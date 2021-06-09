package tw.net.pic.java.intern.bao.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_account")
public class User implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;
//    @GeneratedValue(strategy=GenerationType.AUTO, generator="my_entity_seq_gen")
//    @SequenceGenerator(name="my_entity_seq_gen", sequenceName="HIBERNATE_SEQUENCE")
//    @Column(name = "id")

    @Column(name = "name", columnDefinition = "VARCHAR(100)")
    private String username;
    @Column(name = "pwd", columnDefinition = "VARCHAR(255)")
    private String password;
    @Column(name = "role_id")
    private int role_id;
    @Column(name = "create_date")
    private Date create_date;
    @Column(name = "modify_date")
    private Date modify_date;
    @Column(name = "active", columnDefinition = "smallint")
    private Integer active_code = 1;
    @Column(name = "social_id", columnDefinition = "VARCHAR(255)")
    private String social_id;

    @Transient
    private String passwordConfirm;

    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "role_id",insertable = false, updatable = false)
    private Role role;
//    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE } , fetch = FetchType.EAGER)
//    @JoinTable(name="account_role", joinColumns = {@JoinColumn(table="user_account", name="role_id")}, inverseJoinColumns = {@JoinColumn(table = "user_role", name="id")})
//    private Set<Role> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getModify_date() {
        return modify_date;
    }

    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}