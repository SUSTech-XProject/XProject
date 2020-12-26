package com.ooad.xproject.mapper;

import com.ooad.xproject.dto.MessageDTO;
import com.ooad.xproject.entity.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMapper {
    int deleteByPrimaryKey(Integer msgId);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer msgId);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    // extra

    List<MessageDTO> selectDTOByProjInst(int projInstId);

    List<MessageDTO> selectDTOByProject(int roleId, int projId);
}