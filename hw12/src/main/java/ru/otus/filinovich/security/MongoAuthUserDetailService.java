package ru.otus.filinovich.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.dao.user.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MongoAuthUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        ru.otus.filinovich.domain.User user = userRepository.findUserByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException(userName);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getUserRoles()
                .forEach(role -> {
                    grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
                });
        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

}
