package tw.net.pic.java.intern.bao.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "user_role")
public class Role implements Serializable {

    final static public String ROLE_STORE = "ROLE_1";
    final static public String ROLE_CUSTOMER = "ROLE_2";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "name", columnDefinition = "VARCHAR(100)")
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<User> users;
//    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE } , fetch = FetchType.EAGER, mappedBy = "roles")
//    private Set<User> users;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}