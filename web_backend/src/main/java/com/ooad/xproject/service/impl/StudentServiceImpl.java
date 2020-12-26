package com.ooad.xproject.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ooad.xproject.bo.CommentBO;
import com.ooad.xproject.bo.StudentImportBO;
import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.dto.StudentDTO;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.entity.Student;
import com.ooad.xproject.entity.Teacher;
import com.ooad.xproject.mapper.RoleMapper;
import com.ooad.xproject.mapper.StudentMapper;
import com.ooad.xproject.service.StudentService;
import com.ooad.xproject.vo.AcInfoStdUpdateVO;
import com.ooad.xproject.vo.SelectorStdVO;
import com.ooad.xproject.vo.StdUpdateVO;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final RoleMapper roleMapper;
    private final StudentMapper studentMapper;

    public StudentServiceImpl(RoleMapper roleMapper, StudentMapper studentMapper) {
        this.roleMapper = roleMapper;
        this.studentMapper = studentMapper;
    }

    @Override
    public Student getStudent(Integer sid) {
        return studentMapper.selectByPrimaryKey(sid);
    }

    @Override
    public List<Student> getStudentList(Integer group) {
        return studentMapper.selectBySgpId(group);
    }

    @Override
    @Deprecated
    public List<Student> getTeammatesByTeamId(Integer tid) {
        return studentMapper.selectByTeamId(tid);
    }

    @Override
    @Deprecated
    public List<Student> getTeammatesByStdId(Integer sid) {
        int tid = studentMapper.selectTeamIdByStdId(sid);
        return studentMapper.selectByTeamId(tid);
    }

    @Override
    public Student getStudentByRoleId(Integer roleId) {
        return studentMapper.selectByRoleId(roleId);
    }

    @Override
    public boolean updateAcInfo(Role role, AcInfoStdUpdateVO acInfoStdUpdateVO) {
        Student student = studentMapper.selectByRoleId(role.getRoleId());

        if (acInfoStdUpdateVO.getFlags() != null) {
            String newFlagsJson = JSON.toJSONString(acInfoStdUpdateVO.getFlags());
            student.setFlags(newFlagsJson);
        }

        if (acInfoStdUpdateVO.getSkills() != null) {
            String newSkillsJson = JSON.toJSONString(acInfoStdUpdateVO.getSkills());
            student.setSkills(newSkillsJson);
        }

        student.setBio(acInfoStdUpdateVO.getBio());
        student.setEmail(acInfoStdUpdateVO.getEmail());

        int affectedRowCnt = studentMapper.updateByPrimaryKeySelective(student);

        role.setIconUrl(acInfoStdUpdateVO.getIconUrl());
        affectedRowCnt += roleMapper.updateByPrimaryKey(role);

        return affectedRowCnt == 2;
    }

    @Override
    public SvResult<PageInfo<StudentDTO>> getStudentListBySelector(Role role, Teacher teacher, SelectorStdVO selectorStdVO) {
        PageHelper.startPage(selectorStdVO.getPage(), selectorStdVO.getPageSize(), selectorStdVO.getOrderStr());

        List<StudentDTO> stdDTOList;
        try {
            stdDTOList = studentMapper.selectDTOBySelector(teacher.getSchId(), selectorStdVO.getStdClass());
        } catch (Exception e) {
            e.printStackTrace();
            return new SvResult<>(-1, e.getMessage());
        }
        PageInfo<StudentDTO> pageInfo = new PageInfo<>(stdDTOList);

        return new SvResult<>("Success", pageInfo);
    }

    @Override
    public List<StudentDTO> getAllStudentList(Teacher teacher) {
        List<StudentDTO> stdDTOList = studentMapper.selectDTOBySelector(teacher.getSchId(), null);
        return stdDTOList;
    }

    @Transactional
    @Override
    public SvResult<Role> creatRoleAndStudent(int schId, StudentImportBO studentImportBO) {
        if (roleMapper.selectByUsername(studentImportBO.getUsername()) != null) {
            return new SvResult<>("User already exist", null);
        }

        try{
            String username = studentImportBO.getUsername();
            String password = studentImportBO.getPassword();
            String type = "Student";
            String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            int times = 2;      // hash times
            String encodedPassword = new SimpleHash("md5", password, salt, times).toString();

//            create and insert role
            Role newRole = new Role();
            newRole.setUsername(username);
            newRole.setPassword(encodedPassword);
            newRole.setRoleType(type);
            newRole.setSalt(salt);
            int affectedRowCnt = roleMapper.insertSelective(newRole);
            if (affectedRowCnt == 0) {
                throw new Exception("Error when insert Role");
            }
            int roleId = newRole.getRoleId();
            System.out.println(roleId);

//            create and insert student
            Student std = new Student();
            std.setStdName(studentImportBO.getStdName());
            std.setSchId(schId);
            std.setStdNo(studentImportBO.getStdNo());
            std.setStdClass(studentImportBO.getStdClass());
            std.setEmail(studentImportBO.getEmail());
            std.setRoleId(roleId);
            affectedRowCnt = studentMapper.insertSelective(std);
            if (affectedRowCnt == 0) {
                throw new Exception("Error when insert Student");
            }
            return new SvResult<>("Create a new Student account", newRole);
        }catch (Exception e){
            // roll back
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return new SvResult<>(e.toString(), null);
        }
    }

    @Override
    public Student getStudentByStdNo(String stdNo) {
        return studentMapper.selectByStdNo(stdNo);
    }

    @Override
    public boolean updateStdInfo(StdUpdateVO stdUpdateVO) {
        Student std = studentMapper.selectByRoleId(stdUpdateVO.getRoleId());
        if (std == null) {
            return false;
        }
        stdUpdateVO.copyToStudent(std);
        int affectedRowCnt = studentMapper.updateByPrimaryKeySelective(std);
        return affectedRowCnt == 1;
    }

    @Override
    public boolean appendStdPayload(int roleId, String payload) {
        Student student = studentMapper.selectByRoleId(roleId);

        if (student == null) {
            return false;
        }
        String pl = student.getPayload();
        List<String> commentList = new ArrayList<>();
        CommentBO commentBO = JSON.parseObject(pl, CommentBO.class);
        if(commentBO == null){
            commentBO = new CommentBO();
        }
        if(commentBO.getComments() == null){
            commentBO.setComments(commentList);
        }
        commentList = commentBO.getComments();
        commentList.add(payload);
        commentBO.setComments(commentList);

        pl = JSON.toJSONString(commentBO);
        student.setPayload(pl);
        return studentMapper.updateByPrimaryKey(student) != 0;
    }
}
