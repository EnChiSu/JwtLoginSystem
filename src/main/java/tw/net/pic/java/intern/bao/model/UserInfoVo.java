package tw.net.pic.java.intern.bao.model;

import java.util.Date;

public class UserInfoVo {
    private String username;
    private String password;
    private Integer role_id;
    private Date pw_modify_date;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public Date getPw_modify_date() {
        return pw_modify_date;
    }

    public void setPw_modify_date(Date pw_modify_date) {
        this.pw_modify_date = pw_modify_date;
    }
}
