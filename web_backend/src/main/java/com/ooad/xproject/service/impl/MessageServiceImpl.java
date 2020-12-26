package com.ooad.xproject.service.impl;

import com.ooad.xproject.dto.MessageDTO;
import com.ooad.xproject.entity.Message;
import com.ooad.xproject.mapper.MessageMapper;
import com.ooad.xproject.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    @Override
    public List<MessageDTO> getMessageDTOList(Integer roleId, Integer projId, Integer projInstId) {
        if (projId != null) {
            return messageMapper.selectDTOByProject(roleId, projId);
        } else {
            return messageMapper.selectDTOByProjInst(projInstId);
        }
    }

    @Override
    public boolean updateMessage(Message message) {
        int affectedRowCnt = messageMapper.updateByPrimaryKeySelective(message);
        return affectedRowCnt == 1;
    }

    @Override
    public boolean createMessage(Message message) {
        int affectedRowCnt = messageMapper.insertSelective(message);
        return affectedRowCnt == 1;
    }

    @Override
    public boolean deleteMessage(int msgId) {
        int affectedRowCnt = messageMapper.deleteByPrimaryKey(msgId);
        return affectedRowCnt == 1;
    }

    @Override
    public Message getMessageByMsgId(int msgId) {
        return messageMapper.selectByPrimaryKey(msgId);
    }
}
