package com.ooad.xproject.constant;

public enum RoleType {
    Teacher,
    Student,
    Admin,
    Null;

    public static RoleType getRoleType(String code) {
        for (RoleType i : RoleType.values()) {
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
