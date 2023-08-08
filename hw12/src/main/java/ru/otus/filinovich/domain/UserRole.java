package ru.otus.filinovich.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class UserRole implements GrantedAuthority {

    @Getter
    @Setter
    private String role;

    @Override
    public String getAuthority() {
        return getRole();
    }
}
