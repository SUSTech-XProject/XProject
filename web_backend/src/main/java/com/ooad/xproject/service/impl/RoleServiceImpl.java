package com.ooad.xproject.service.impl;

import com.ooad.xproject.constant.RoleType;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.entity.School;
import com.ooad.xproject.entity.Student;
import com.ooad.xproject.entity.Teacher;
import com.ooad.xproject.mapper.*;
import com.ooad.xproject.service.RoleService;
import com.ooad.xproject.vo.ChangePwdVO;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    // role type

    // Contain number and letter, at least 8 chars.
    // For username, not start with number
    public static final String USERNAME_REGEX = "^(?![0-9])(?![0-9]+$)[0-9A-Za-z]{8,24}$";
    public static final String PASSWORD_REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,32}$";

    private final RoleMapper roleMapper;
    private final AdminMapper adminMapper;
    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;
    private final SchoolMapper schoolMapper;

    public RoleServiceImpl(RoleMapper roleMapper, AdminMapper adminMapper,
                           TeacherMapper teacherMapper, StudentMapper studentMapper, SchoolMapper schoolMapper) {
        this.roleMapper = roleMapper;
        this.adminMapper = adminMapper;
        this.teacherMapper = teacherMapper;
        this.studentMapper = studentMapper;
        this.schoolMapper = schoolMapper;
    }

    @Override
    public Role getByUsername(String username) {
        return roleMapper.selectByUsername(username);
    }


//    /**
//     * Check the role with the username and the pwd.
//     * Return Role obj if success.
//     */
//    @Override
//    public SvResult<Role> checkRolePwd(String username, String password) {
//        Role role = roleMapper.selectByUsername(username);
//        if (role == null) {
//            return new SvResult<>(-1, "No such username");
//        }
//        if (!password.equals(role.getPassword())) {
//            return new SvResult<>(-1, "Wrong password");
//        }
//        return new SvResult<>(0, "Authentication passed", role);
//    }

    @Override
    public Role createUser(String type, String username, String password, Integer schId) {
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;      // hash times
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();

        Role newRole = new Role();
        newRole.setUsername(username);
        newRole.setPassword(encodedPassword);
        newRole.setRoleType(type);
        newRole.setSalt(salt);
        roleMapper.insertSelective(newRole);
        int roleId = newRole.getRoleId();
        System.out.println(roleId);

        if (RoleType.Teacher.match(type)) {
            // create teacher
            Teacher tch = new Teacher();
            tch.setRoleId(roleId);
            tch.setSchId(schId);
            teacherMapper.insertSelective(tch);
        } else if (RoleType.Student.match(type)){
            // create student
            Student std = new Student();
            std.setRoleId(roleId);
            std.setSchId(schId);
            studentMapper.insertSelective(std);
        } else {
            // do nothing
        }

        return newRole;
    }

    @Override
    public boolean validUsername(String username) {
        return username.matches(USERNAME_REGEX);
    }

    @Override
    public boolean validPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    @Override
    public List<Role> selectAll(String roleType) {
        return roleMapper.selectAll(roleType);
    }

    @Override
    public Role getByRoleId(int roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public boolean changePwd(Role role, ChangePwdVO changePwdVO) {
        int times = 2;      // hash times
        String encodedPassword = new SimpleHash("md5", changePwdVO.getNewPassword(), role.getSalt(), times).toString();

        role.setPassword(encodedPassword);
        int affectedRowCnt = roleMapper.updateByPrimaryKey(role);
        return affectedRowCnt != 0;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.selectAll(null);
    }

    @Override
    public boolean changeStatus(int roleId) {
        Role role = roleMapper.selectByPrimaryKey(roleId);
        role.setStatus(("Enabled".equals(role.getStatus())) ? "Disabled" : "Enabled");
        int affectedRowCnt = roleMapper.updateByPrimaryKey(role);
        return affectedRowCnt == 1;
    }

    @Override
    public boolean updateSchool(School school) {
        int affectedRowCnt = schoolMapper.updateByPrimaryKeySelective(school);
        return affectedRowCnt == 1;
    }
}
