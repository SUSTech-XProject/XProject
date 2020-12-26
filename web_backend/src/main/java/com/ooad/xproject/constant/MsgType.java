package com.ooad.xproject.constant;

public enum MsgType {
    Apply,
    JoinSuccess,
    JoinFail,
    Quit,
    Invite,
    InviteSuccess,
    InviteFail,
    Null;

    public static MsgType getRoleType(String code) {
        for (MsgType i : MsgType.values()) {
            if (i.match(code)) {
                return i;
            }
        }
        return Null;
    }

    /**
     * Return ture if the enum match the type code
     */
    public boolean match(String typeStr) {
        return this.toString().equals(typeStr);
    }
}
