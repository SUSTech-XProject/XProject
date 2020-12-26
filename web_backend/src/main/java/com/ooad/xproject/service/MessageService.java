package com.ooad.xproject.service;

import com.ooad.xproject.dto.MessageDTO;
import com.ooad.xproject.entity.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageService {
    List<MessageDTO> getMessageDTOList(Integer roleId, Integer projId, Integer projInstId);

    boolean updateMessage(Message message);

    boolean createMessage(Message message);

    boolean deleteMessage(int msgId);

    Message getMessageByMsgId(int msgId);
}
