package com.ooad.xproject.vo;

import lombok.Data;

@Data
public class ChangePwdVO {
    private String oldPassword, newPassword, confirmNewPassword;
}

