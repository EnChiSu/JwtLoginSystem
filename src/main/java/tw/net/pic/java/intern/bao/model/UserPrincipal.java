package tw.net.pic.java.intern.bao.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private User principal;
    private org.springframework.security.core.userdetails.UserDetails wrappedUser;

    public UserPrincipal(User principal) {
        this.principal = principal;
        org.springframework.security.core.userdetails.User.UserBuilder builder
                = org.springframework.security.core.userdetails.User.builder();

        this.wrappedUser = builder.username(this.principal.getUsername())
        .password(this.principal.getPassword())
        .roles(String.valueOf(this.principal.getRole_id())) //這裡會加 ROLE_ 前綴
        .build();
    }

    @Override
    public String getPassword() {
        return this.wrappedUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.wrappedUser.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.wrappedUser.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.wrappedUser.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.wrappedUser.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.wrappedUser.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.wrappedUser.isEnabled();
    }

    public User getPrincipal() {
        return this.principal;
    }
}
