package com.odos.smartaqua.prelogin.profile;

import java.io.Serializable;

public class UserData implements Serializable {
    int userid;
    int roleid;
    String uniqueid;
    String username;
    String usernumber;
    String useremail;
    String userlocation;
    String userimage;
    String createdby;
    String notificationid;
    String rolecode;

    public UserData(int userid, int roleid, String uniqueid, String username, String usernumber, String useremail,
                    String userlocation, String userimage, String createdby, String notificationid, String rolecode) {
        this.userid = userid;
        this.roleid = roleid;
        this.uniqueid = uniqueid;
        this.username = username;
        this.usernumber = usernumber;
        this.useremail = useremail;
        this.userlocation = userlocation;
        this.userimage = userimage;
        this.createdby = createdby;
        this.notificationid = notificationid;
        this.rolecode = rolecode;
    }
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserlocation() {
        return userlocation;
    }

    public void setUserlocation(String userlocation) {
        this.userlocation = userlocation;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getNotificationid() {
        return notificationid;
    }

    public void setNotificationid(String notificationid) {
        this.notificationid = notificationid;
    }

    public String getRolecode() {
        return rolecode;
    }

    public void setRolecode(String rolecode) {
        this.rolecode = rolecode;
    }

}
