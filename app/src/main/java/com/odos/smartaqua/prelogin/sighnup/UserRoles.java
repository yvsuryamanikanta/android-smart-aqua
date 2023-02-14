package com.odos.smartaqua.prelogin.sighnup;

public class UserRoles {

    int roleID;
    String roleCode;
    String roleName;
    boolean isactive;

    public UserRoles(int roleID, String roleCode, String roleName, boolean isactive) {
        this.roleID = roleID;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.isactive = isactive;
    }

    public int getRoleID() {
        return roleID;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public boolean isIsactive() {
        return isactive;
    }
}
