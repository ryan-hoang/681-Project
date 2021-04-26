package gmu.Project.WebSecurity;

import gmu.Project.model.Authority;
import gmu.Project.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public class CustomSecureUser extends User implements UserDetails {

    public CustomSecureUser(User user) {
        this.setAuthorities(user.getAuthorities());
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
    }

    @Override
    public Set<Authority> getAuthorities() {
        return super.getAuthorities();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
