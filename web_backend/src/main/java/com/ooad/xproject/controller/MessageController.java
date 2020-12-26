package com.ooad.xproject.controller;

import com.ooad.xproject.bo.MessageFactory;
import com.ooad.xproject.constant.RespStatus;
import com.ooad.xproject.dto.MessageDTO;
import com.ooad.xproject.entity.Message;
import com.ooad.xproject.entity.ProjectInst;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.service.MessageService;
import com.ooad.xproject.service.ProjInstService;
import com.ooad.xproject.service.RoleService;
import com.ooad.xproject.utils.RoleUtils;
import com.ooad.xproject.vo.InviteParamVO;
import com.ooad.xproject.vo.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {

    private final RoleService roleService;
    private final ProjInstService projInstService;
    private final MessageService messageService;
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    public MessageController(RoleService roleService, ProjInstService projInstService, MessageService messageService) {
        this.roleService = roleService;
        this.projInstService = projInstService;
        this.messageService = messageService;
    }

    @ResponseBody
    @GetMapping("api/all/project/message")
    public Result<?> getProjMsgList(@RequestParam("projId") int projId) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        List<MessageDTO> msgList = messageService.getMessageDTOList(role.getRoleId(), projId, null);
        return new Result<>(msgList);
    }

    @ResponseBody
    @GetMapping("api/all/team/message")
    public Result<?> getTeamMsgList(@RequestParam("projInstId") int projInstId) {
        List<MessageDTO> msgList = messageService.getMessageDTOList(null, null, projInstId);
        return new Result<>(msgList);
    }

    @ResponseBody
    @PostMapping("api/student/team/invite")
    public Result<Integer> postInviteStudents(@RequestBody InviteParamVO inviteParamVO) {
        int[] stdRoleIdList = inviteParamVO.getStdRoleIdList();
        int projId = inviteParamVO.getProjId();

        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        ProjectInst projInst = projInstService.getPIByProjIdAndStdRoleId(projId, role.getRoleId());

        if (projInst == null) {
            return new Result<>(RespStatus.FAIL, "No team");
        }

        int successCnt = 0;
        int creatorRoleId = role.getRoleId();

        for (int stdRoleId: stdRoleIdList) {
            ProjectInst projInstTmp = projInstService.getPIByProjIdAndStdRoleId(projId, stdRoleId);
            if (projInstTmp != null) {
                logger.info(String.format("postInviteStudents: invite fail. " +
                        "stdRoleId=%d", stdRoleId));
                continue;   // already has a team
            }
            Message msg = MessageFactory.createInviteMessage(creatorRoleId, projInst, stdRoleId);
            messageService.createMessage(msg);
            successCnt += 1;
        }

        return new Result<>(successCnt);
    }
}
