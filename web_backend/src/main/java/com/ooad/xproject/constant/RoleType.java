package com.ooad.xproject.constant;

public enum RoleType {
    Teacher("T"),
    Student("S"),
    Admin("A");

    public String code;

    RoleType(String code) {
        this.code = code;
    }

    /**
     * Return ture if the enum match the type code
     */
    public boolean match(String code) {
        return this.code.equals(code);
    }
}
