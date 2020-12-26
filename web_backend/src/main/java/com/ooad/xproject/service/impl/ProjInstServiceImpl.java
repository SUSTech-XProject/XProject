package com.ooad.xproject.service.impl;

import com.alibaba.fastjson.JSON;
import com.ooad.xproject.bo.MessageFactory;
import com.ooad.xproject.bo.ProjSettingsBO;
import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.dto.GradeDTO;
import com.ooad.xproject.dto.RecordInstDTO;
import com.ooad.xproject.dto.StudentDTO;
import com.ooad.xproject.dto.StudentProjDTO;
import com.ooad.xproject.entity.*;
import com.ooad.xproject.mapper.*;
import com.ooad.xproject.service.MailService;
import com.ooad.xproject.service.ProjInstService;
import com.ooad.xproject.vo.ApplyReplyParamVO;
import com.ooad.xproject.vo.ApplyTeamParamVO;
import com.ooad.xproject.vo.ProjInstCreationVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;
import java.util.stream.Collectors;

import static com.ooad.xproject.constant.ProjInstStatus.Confirm;
import static com.ooad.xproject.constant.ProjInstStatus.Raw;

@Service
public class ProjInstServiceImpl implements ProjInstService {

    private final ProjectMapper projectMapper;
    private final ProjectInstMapper projectInstMapper;
    private final RecordInstMapper recordInstMapper;
    private final SubmissionInstMapper submissionInstMapper;
    private final MessageMapper msgMapper;

    private final ProjInstStudentRTMapper pisRTMapper;
    private final RecordMapper recordMapper;
    private final MailService mailService;
    private final StudentMapper studentMapper;

    public ProjInstServiceImpl(ProjectMapper projectMapper, ProjectInstMapper projectInstMapper, RecordInstMapper recordInstMapper, SubmissionInstMapper submissionInstMapper, MessageMapper msgMapper, ProjInstStudentRTMapper pisRTMapper, RecordMapper recordMapper, MailService mailService, StudentMapper studentMapper) {
        this.projectMapper = projectMapper;
        this.projectInstMapper = projectInstMapper;
        this.recordInstMapper = recordInstMapper;
        this.submissionInstMapper = submissionInstMapper;
        this.msgMapper = msgMapper;
        this.pisRTMapper = pisRTMapper;
        this.recordMapper = recordMapper;
        this.mailService = mailService;
        this.studentMapper = studentMapper;
    }

    @Override
    public ProjectInst getProjectInst(Integer piid) {
        return projectInstMapper.selectByPrimaryKey(piid);
    }

    @Override
    public List<ProjectInst> getProInstList(Integer projId) {
        return projectInstMapper.selectByProjId(projId);
    }

    @Override
    public List<StudentDTO> getStudentDTOByProjInstId(Integer projInstId) {
        return projectInstMapper.selectStudentByProjInstId(projInstId);
    }


    @Override
    public List<RecordInst> getRecordInstList(Integer rid) {
        return recordInstMapper.selectByRcdId(rid);
    }

    // todo: select Record by project and student
    @Override
    public List<RecordInstDTO> getRecordInstList(Integer projId, Integer stdRoleId) {
        return recordInstMapper.selectByProjAndStdRoleId(projId, stdRoleId);
    }


    @Override
    public List<SubmissionInst> getSubmissionInstList(Integer sbmId) {
        return submissionInstMapper.selectBySbmId(sbmId);
    }

    // todo: select Submission by project and student
    @Override
    public List<SubmissionInst> getSubmissionInstList(Integer pid, Integer sid) {
        return null;
    }

    @Transactional
    @Override
    public SvResult<Boolean> deleteProjInst(int projInstId) {
        int affectedRowCnt = projectInstMapper.deleteByPrimaryKey(projInstId);
        if (affectedRowCnt == 1) {
            return new SvResult<>(0, true);
        } else {
            return new SvResult<>(0, false);
        }
    }

    @Transactional
    @Override
    public SvResult<Boolean> confirmProjInst(int projInstId, boolean isForce, boolean isTeacher) {

        if (!isForce) {
            // check proj inst validate
            SvResult<Boolean> svResult = checkProjInst(projInstId, isTeacher);
            if (!svResult.getData()) {
                System.out.println("Team " + projInstId + " check failed.");
                return svResult;
            }
        }

        ProjectInst record = projectInstMapper.selectByPrimaryKey(projInstId);

        if (record == null) {
            return new SvResult<>("No such team", false);
        }
        if (record.getStatus().equals(Confirm.name())) {
            return new SvResult<>("Already confirmed", false);
        }

        record.setProjInstId(projInstId);
        record.setStatus(Confirm.toString());
        int affectedRowCnt = projectInstMapper.updateByPrimaryKeySelective(record);

        if (affectedRowCnt == 1) {
            return new SvResult<>("Confirm successfully", true);
        } else {
            return new SvResult<>("Confirm failed", false);
        }
    }

    private SvResult<Boolean> checkProjInst(int projInstId, boolean isTeacher) {
        ProjectInst projInst = projectInstMapper.selectByPrimaryKey(projInstId);
        Project project = projectMapper.selectByPrimaryKey(projInst.getProjId());

        ProjSettingsBO settings = JSON.parseObject(project.getProjSettings(), ProjSettingsBO.class);

        List<StudentProjDTO> stdProjDTOList = projectInstMapper.selectStdProjDTOByProjInstId(projInstId);
        // check group mark
        if ((settings == null || settings.getAllowCrossMark() == null || !settings.getAllowCrossMark())
                && !stdProjDTOList.isEmpty()) {
            String groupMark = stdProjDTOList.get(0).getGroupMark();
            for (int i = 1; i < stdProjDTOList.size(); i++) {
                if (!groupMark.equals(stdProjDTOList.get(i).getGroupMark())) {
                    return new SvResult<>("Across group is not allowed", false);
                }
            }
        }

        // check recruit system
        if (settings == null || (settings.getUseRecruitSystem() != null && !settings.getUseRecruitSystem() && !isTeacher)) {
            return new SvResult<>("Recruit system is not open now", false);
        }


        // check size
        if (settings.getMinSize() != null && settings.getMaxSize() != null) {
            if (stdProjDTOList.size() > settings.getMaxSize()) {
                String msg = String.format("Team over-sized. Current: %d. Max size: %d",
                        stdProjDTOList.size(), settings.getMaxSize());
                return new SvResult<>(msg, false);
            } else if (stdProjDTOList.size() < settings.getMinSize()) {
                String msg = String.format("No enough members. Current: %d. Min size: %d",
                        stdProjDTOList.size(), settings.getMinSize());
                return new SvResult<>(msg, false);
            }
        }

        // check due time
        if (settings.getDueTime() != null && settings.getDueTime().after(new Date(System.currentTimeMillis()))
                && !isTeacher) {
            return new SvResult<>("Pass due time", false);
        }

        return new SvResult<>("ProjInst is valid", true);
    }

    private SvResult<Boolean> cancelProjInst(int projInstId) {
        ProjectInst record = projectInstMapper.selectByPrimaryKey(projInstId);

        if (record == null) {
            return new SvResult<>("No such team", false);
        }
        if (record.getStatus().equals(Raw.name())) {
            return new SvResult<>("Already raw", false);
        }

        record.setProjInstId(projInstId);
        record.setStatus(Raw.toString());
        int affectedRowCnt = projectInstMapper.updateByPrimaryKeySelective(record);

        if (affectedRowCnt == 1) {
            return new SvResult<>("Cancel successfully", true);
        } else {
            return new SvResult<>("Cancel failed", false);
        }
    }

    @Override
    public ProjectInst getPIByProjIdAndStdRoleId(int projId, int stdRoleId) {
        return projectInstMapper.selectPIByProjIdAndStdRoleId(projId, stdRoleId);
    }

    @Transactional
    @Override
    public SvResult<Boolean> createProjInst(ProjInstCreationVO projInstCreationVO) {
        try {
            ProjectInst projInst = new ProjectInst();
            projInst.setProjId(projInstCreationVO.getProjId());
            projInst.setTeamName(projInstCreationVO.getTeamName());
            projInst.setTopicStr(projInstCreationVO.getTopic());
            projInst.setTargetMemNum(projInstCreationVO.getIdealSize());
            projInst.setDescription(projInstCreationVO.getDescription());

            int affectedRowCnt = projectInstMapper.insertSelective(projInst);

            if (affectedRowCnt == 0) {
                throw new Exception("Error when insert projInst");
            }

            return new SvResult<>("Create project inst", true);
        } catch (Exception e) {
            // roll back
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return new SvResult<>(e.toString(), false);
        }
    }

    /**
     * Reuse VO to create project inst.
     */
    @Transactional
    @Override
    public SvResult<Boolean> createProjInstAndLink(int stdRoleId, ProjInstCreationVO projInstCreationVO) {
        // check first
        if (this.getPIByProjIdAndStdRoleId(projInstCreationVO.getProjId(), stdRoleId) != null) {
            return new SvResult<>("Already in a team", false);
        }

        try {
            ProjectInst projInst = new ProjectInst();
            projInst.setProjId(projInstCreationVO.getProjId());
            projInst.setTeamName(projInstCreationVO.getTeamName());
            projInst.setTopicStr(projInstCreationVO.getTopic());
            projInst.setTargetMemNum(projInstCreationVO.getIdealSize());
            projInst.setDescription(projInstCreationVO.getDescription());

            int affectedRowCnt = projectInstMapper.insertSelective(projInst);

            if (affectedRowCnt == 0) {
                throw new Exception("Error when insert projInst");
            }

            int projInstId = projInst.getProjInstId();

            // update relate (first member)

            ProjInstStudentRT pisRT = new ProjInstStudentRT();
            pisRT.setStdRoleId(stdRoleId);
            pisRT.setProjInstId(projInstId);
            affectedRowCnt = pisRTMapper.insertSelective(pisRT);

            if (affectedRowCnt == 0) {
                throw new Exception("Error when update ProjInstStudentRT");
            }

            return new SvResult<>("Create project inst, and take student as first member", true);
        } catch (Exception e) {
            // roll back
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return new SvResult<>(e.toString(), false);
        }
    }

    @Override
    public boolean quitTeam(int stdRoleId, int projInstId) {
        int affectedRoleCnt = projectInstMapper.deleteProjInstStdRT(stdRoleId, projInstId);
        // TODO notice other student
        return affectedRoleCnt == 1;
    }

    @Override
    public boolean updateProjInst(ProjectInst projectInst) {
        int affectedRoleCnt = projectInstMapper.updateByPrimaryKeySelective(projectInst);
        return affectedRoleCnt == 1;
    }

    @Override
    public SvResult<Boolean> applyTeam(Integer roleId, ApplyTeamParamVO atpVO) {
        int projInstId = atpVO.getProjInstId();
        List<StudentDTO> teamMemList = projectInstMapper.selectStudentByProjInstId(projInstId);

        ProjectInst projInst = projectInstMapper.selectByPrimaryKey(projInstId);
        int projId = projInst.getProjId();
        ProjectInst projInstApplicant = projectInstMapper.selectPIByProjIdAndStdRoleId(projId, roleId);
        if (projInstApplicant != null) {
            return new SvResult<>("Apply failed. You already have a team", false);
        }

        if (teamMemList.isEmpty()) {
            // add this role directly
            System.out.printf("%d, %d", projInstId, roleId);
            int affectedRowCnt = projectInstMapper.insertProjInstStdRT(projInstId, roleId, null);

            if (affectedRowCnt != 0) {
                // send email
                Student applicant = studentMapper.selectByRoleId(roleId);
                mailService.sendSimpleMail(applicant.getEmail(), "[XProject] You has joined the team",
                        "You has joined the team\r\n" +
                                "This automatic notification message was sent by Xproject");

                return new SvResult<>("Apply successfully. You are the first member!", true);
            } else {
                return new SvResult<>("Apply failed.", false);
            }

        } else {
            Message msg = MessageFactory.createApplyTeamMsg(projInst, roleId, atpVO.getMessage());
            msgMapper.insertSelective(msg);

            Student applicant = studentMapper.selectByRoleId(roleId);

            // send email to all members
            System.out.println("Send email to all members");
            List<StudentDTO> stdList = this.getStudentDTOByProjInstId(projInstId);
            List<String> mailList = stdList.stream().map(StudentDTO::getEmail).collect(Collectors.toList());
            mailService.sendMailToStudent(mailList, "[XProject] A student applies to your team",
                    "A student applies to your team\r\n" +
                            "Name: " + applicant.getStdName() + "\r\n" +
                            "Email: " + applicant.getEmail() + "\r\n" +
                            "This automatic notification message was sent by Xproject");

            return new SvResult<>("Apply successfully. Please wait for result!", true);
        }
    }

    @Transactional
    @Override
    public SvResult<Boolean> applyTeamReply(Integer roleId, ApplyReplyParamVO applyReplyParamVO) {
        try {
            Message msg = msgMapper.selectByPrimaryKey(applyReplyParamVO.getMsgId());
            if (msg.getDecided()) {
                return new SvResult<>("This message has been processed", false);
            }

            // change the basic message info
            msg.setDecided(true);
            msg.setContent(applyReplyParamVO.getMessage());
            msg.setResult(applyReplyParamVO.isAccepted() ? "Accept" : "Reject");
            msg.setHandlerRoleId(roleId);

            // check applicant
            // can't use projId in msg here
            ProjectInst projInst = projectInstMapper.selectByPrimaryKey(msg.getProjInstId());
            ProjectInst projInstApplicant = projectInstMapper.selectPIByProjIdAndStdRoleId(projInst.getProjId(), msg.getCreatorRoleId());
            if (projInstApplicant != null || projInst.getStatus().equals(Confirm.name())) {
                // already has a team
                msg.setResult("Fail to join");
            }

            // update message
            int affectedRowCnt = msgMapper.updateByPrimaryKey(msg);
            if (affectedRowCnt == 1) {
                if (!applyReplyParamVO.isAccepted()) {
                    // reject

                    // send message
                    Message rejectMsg = MessageFactory.createApplyReplyMsg(projInst.getProjId(), msg.getCreatorRoleId(), roleId, false);
                    msgMapper.insertSelective(rejectMsg);

                    // send email
                    Student applicant = studentMapper.selectByRoleId(msg.getCreatorRoleId());
                    mailService.sendSimpleMail(applicant.getEmail(), "[XProject] You are rejected to join the team",
                            "You are reject to join the team\r\n" +
                                    "This automatic notification message was sent by Xproject");
                    return new SvResult<>("Application rejected", true);
                }
            } else {
                return new SvResult<>("Error occur when update message", false);
            }

            if (projInstApplicant != null) {
                // already has a team
                // send message
                Message rejectMsg = MessageFactory.createApplyReplyMsg(projInst.getProjId(), msg.getCreatorRoleId(), roleId, false);
                msgMapper.insertSelective(rejectMsg);

                return new SvResult<>("Application accepted. Applicant has been in anther team.", true);
            }

            if (projInst.getStatus().equals(Confirm.name())) {
                // send message
                Message rejectMsg = MessageFactory.createApplyReplyMsg(projInst.getProjId(), msg.getCreatorRoleId(), roleId, false);
                msgMapper.insertSelective(rejectMsg);

                return new SvResult<>("Application reject. Team is confirmed", true);
            }

            affectedRowCnt = projectInstMapper.insertProjInstStdRT(msg.getProjInstId(), msg.getCreatorRoleId(), "Join");

            if (affectedRowCnt != 0) {
                // send message
                Message acceptMsg = MessageFactory.createApplyReplyMsg(projInst.getProjId(), msg.getCreatorRoleId(), roleId, true);
                msgMapper.insertSelective(acceptMsg);

                // send email
                Student applicant = studentMapper.selectByRoleId(msg.getCreatorRoleId());
                mailService.sendSimpleMail(applicant.getEmail(), "[XProject] You are accepted to join the team",
                        "You are accepted to join the team\r\n" +
                                "This automatic notification message was sent by Xproject");
                return new SvResult<>("Application accepted", true);
            } else {
                return new SvResult<>("Error occur when update team info", false);
            }

        } catch (Exception e) {
            // roll back
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return new SvResult<>("Error occur", false);
        }
    }

    @Transactional
    @Override
    public SvResult<Boolean> inviteTeamReply(Integer roleId, ApplyReplyParamVO applyReplyParamVO) {
        try {
            Message msg = msgMapper.selectByPrimaryKey(applyReplyParamVO.getMsgId());
            if (msg.getDecided()) {
                return new SvResult<>("This message has been processed", false);
            }

            // change the basic message info
            msg.setDecided(true);
            msg.setContent(applyReplyParamVO.getMessage());
            msg.setResult(applyReplyParamVO.isAccepted() ? "Accept" : "Reject");
            msg.setHandlerRoleId(roleId);

            // check team
            // can use projId in msg here
            ProjectInst projInst = projectInstMapper.selectByPrimaryKey(msg.getProjInstId());
            if (projInst.getStatus().equals(Confirm.name())) {
                // is confirmed
                msg.setResult("Fail to join");
            }

            // update message
            int affectedRowCnt = msgMapper.updateByPrimaryKey(msg);
            if (affectedRowCnt == 1) {
                if (!applyReplyParamVO.isAccepted()) {
                    // reject

                    // send message
                    Message rejectMsg = MessageFactory.createInviteReplyMsg(projInst.getProjId(),
                            projInst.getProjInstId(), roleId, false);
                    msgMapper.insertSelective(rejectMsg);

                    // send email
                    Student applicant = studentMapper.selectByRoleId(msg.getCreatorRoleId());
                    mailService.sendSimpleMail(applicant.getEmail(), "[XProject] A student has rejected to join the team",
                            "A student has rejected to join the team\r\n" +
                                    "This automatic notification message was sent by Xproject");
                    return new SvResult<>("Invitation rejected", true);
                }
            } else {
                return new SvResult<>("Error occur when update message", false);
            }

            if (projInst.getStatus().equals(Confirm.name())) {
                // send message
                Message rejectMsg = MessageFactory.createInviteReplyMsg(projInst.getProjId(),
                        projInst.getProjInstId(), roleId, false);
                msgMapper.insertSelective(rejectMsg);

                return new SvResult<>("Invitation reject. Team is confirmed", true);
            }

            affectedRowCnt = projectInstMapper.insertProjInstStdRT(msg.getProjInstId(), msg.getRoleId(), "Join");

            if (affectedRowCnt != 0) {
                // send message
                Message acceptMsg = MessageFactory.createInviteReplyMsg(projInst.getProjId(),
                        projInst.getProjInstId(), roleId, true);
                msgMapper.insertSelective(acceptMsg);

                // send email to all members
                System.out.println("Send email to all members");
                List<StudentDTO> stdList = this.getStudentDTOByProjInstId(projInst.getProjInstId());
                List<String> mailList = stdList.stream().map(StudentDTO::getEmail).collect(Collectors.toList());
                mailService.sendMailToStudent(mailList, "[XProject] A student has been invited to join to your team",
                        "A student has been invited to join to your team" +
                                "This automatic notification message was sent by Xproject");

                return new SvResult<>("Invitation accepted", true);
            } else {
                return new SvResult<>("Error occur when update team info", false);
            }

        } catch (Exception e) {
            // roll back
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return new SvResult<>("Error occur", false);
        }
    }

    @Override
    public List<GradeDTO> getTeamRecordInstList(int projInstId, int rcdId) {
        return recordInstMapper.selectByProjInstIdAndRcdId(projInstId, rcdId);
    }

    @Override
    public String confirmBatchTch(int[] projInstIdList, boolean isForce) {
        List<Integer> successList = new ArrayList<>();
        Set<String> tipSet = new HashSet<>();
        for (int projInstId : projInstIdList) {
            SvResult<Boolean> svResult = confirmProjInst(projInstId, isForce, true);
            if (svResult.getData()) {
                // true
                successList.add(projInstId);
                // send email
                List<StudentDTO> stdList = this.getStudentDTOByProjInstId(projInstId);
                List<String> mailList = stdList.stream().map(StudentDTO::getEmail).collect(Collectors.toList());
                mailService.sendMailToStudent(mailList, "[XProject] Your team has been confirmed",
                        "Your team has been confirmed by teacher\r\n" +
                                "This automatic notification message was sent by Xproject");
            } else {
                // false
                System.out.printf("postTeamConfirm -> Fail to confirm projInst %d%nReason: %s", projInstId, svResult.getMsg());
                tipSet.add(svResult.getMsg());
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String s : tipSet) {
            sb.append("<br/>").append(s);
        }

        int successCnt = successList.size();
        String message = String.format("Confirm %d teams. Total %d.%s", successCnt,
                projInstIdList.length,
                (successCnt == projInstIdList.length) ? "" : ("Reason:" + sb.toString()));

        return message;
    }

    @Override
    public String cancelBatchTch(int[] projInstIdList) {
        List<Integer> successList = new ArrayList<>();
        for (int projInstId : projInstIdList) {
            SvResult<Boolean> svResult = cancelProjInst(projInstId);
            if (svResult.getData()) {
                // true
                successList.add(projInstId);
                // send email
                List<StudentDTO> stdList = this.getStudentDTOByProjInstId(projInstId);
                List<String> mailList = stdList.stream().map(StudentDTO::getEmail).collect(Collectors.toList());
                mailService.sendMailToStudent(mailList, "[XProject] Your team status has been canceled by teacher",
                        "Your team has been confirmed by teacher\r\n" +
                                "This automatic notification message was sent by Xproject");
            } else {
                // false
                System.out.printf("postTeamConfirm -> Fail to cancel projInst %d%n", projInstId);
            }
        }
        int successCnt = successList.size();
        String message = String.format("Cancel %d teams. Total %d.", successCnt,
                projInstIdList.length);
        return message;
    }

    @Override
    public List<GradeDTO> getTeamRecordInstListStd(int stdRoleId, int rcdId) {
        Record record = recordMapper.selectByPrimaryKey(rcdId);
        ProjectInst projInst = projectInstMapper.selectPIByProjIdAndStdRoleId(record.getProjId(), stdRoleId);
        if (projInst == null) {
            // no team yet
            return recordInstMapper.selectByStdRoleIdAndRcdId(stdRoleId, rcdId);
        } else {
            List<GradeDTO> gradeList = recordInstMapper.selectByProjInstIdAndRcdId(projInst.getProjInstId(), rcdId);
            for (int idx = 0; idx < gradeList.size(); ++idx) {
                if (gradeList.get(idx).getRoleId() == stdRoleId) {
                    GradeDTO temp = gradeList.remove(idx);
                    gradeList.add(0, temp);
                }
            }
            return gradeList;
        }
    }
}
