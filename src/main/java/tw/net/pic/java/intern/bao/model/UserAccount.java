package tw.net.pic.java.intern.bao.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user_account")
public class UserAccount {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @JsonProperty("id")
    private String id;

    @JsonProperty("social_id")
    @Column(name = "social_id", nullable = false)
    private String socialId;

    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;

    @JsonProperty("pwd")
    @Column(name = "pwd", nullable = false)
    private String pwd;

    @JsonProperty("create_date")
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @JsonProperty("modify_date")
    @Column(name = "modify_date", nullable = false)
    private Date modifyDate;

    @JsonProperty("active")
    @Column(name = "active", nullable = false)
    private short active;

    @JsonProperty("role_id")
    @Column(name = "role_id", nullable = false)
    private int roleId;

    public UserAccount(){

    }

    public UserAccount(String id,String socialId, String name, String pwd, Date createDate,Date modifyDate,short active,int roleId){
        this.id = id;
        this.socialId = socialId;
        this.name = name;
        this.pwd = pwd;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.active = active;
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public short getActive() {
        return active;
    }

    public void setActive(short active) {
        this.active = active;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString(){
        return "user_account [id=" + id + ", socialId=" + socialId + ", name=" + name +", pwd=" + pwd +
                ", createDate=" + createDate + ", modifyDate= " + modifyDate + ", active=" + active +", roleId=" + roleId +" ]";
    }
}
