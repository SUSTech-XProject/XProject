package com.ooad.xproject.service.impl;

import com.ooad.xproject.entity.Role;
import com.ooad.xproject.entity.Teacher;
import com.ooad.xproject.mapper.RoleMapper;
import com.ooad.xproject.mapper.TeacherMapper;
import com.ooad.xproject.service.TeacherService;
import com.ooad.xproject.vo.AcInfoTchUpdateVO;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final RoleMapper roleMapper;
    private final TeacherMapper teacherMapper;

    public TeacherServiceImpl(RoleMapper roleMapper, TeacherMapper teacherMapper) {
        this.roleMapper = roleMapper;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public Teacher getTeacherByTid(Integer tid) {
        return teacherMapper.selectByPrimaryKey(tid);
    }

    @Override
    public Teacher getTeacherByRoleId(Integer roleId) {
        return teacherMapper.selectByRoleId(roleId);
    }

    @Override
    public boolean updateAcInfo(Role role, AcInfoTchUpdateVO acInfoTchUpdateVO) {
        Teacher teacher = teacherMapper.selectByRoleId(role.getRoleId());

        teacher.setEmail(acInfoTchUpdateVO.getEmail());
        teacher.setTchName(acInfoTchUpdateVO.getTchName());
        teacher.setTchNo(acInfoTchUpdateVO.getTchNo());

        int affectedRowCnt = teacherMapper.updateByPrimaryKeySelective(teacher);

        role.setIconUrl(acInfoTchUpdateVO.getIconUrl());
        affectedRowCnt += roleMapper.updateByPrimaryKey(role);

        return affectedRowCnt == 2;
    }
}
