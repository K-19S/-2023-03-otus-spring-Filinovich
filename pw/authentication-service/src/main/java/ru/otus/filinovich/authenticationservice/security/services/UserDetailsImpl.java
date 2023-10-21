package ru.otus.filinovich.authenticationservice.security.services;

import ru.otus.filinovich.authenticationservice.models.Book;
import ru.otus.filinovich.authenticationservice.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.otus.filinovich.authenticationservice.payload.response.UserInfo;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private String id;

  private String username;

  private String email;

  @JsonIgnore
  private String password;


  private Collection<? extends GrantedAuthority> authorities;

  private Set<Book> books;

  public UserDetailsImpl(String id, UserInfo userInfo, String password,
                         Collection<? extends GrantedAuthority> authorities, Set<Book> books) {
    this.id = id;
    this.username = userInfo.getUsername();
    this.email = userInfo.getEmail();
    this.password = password;
    this.authorities = authorities;
    this.books = books;
  }

  public static UserDetailsImpl build(User user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());
  UserInfo userInfo = new UserInfo(user.getUsername(), user.getEmail());
    return new UserDetailsImpl(
        user.getId(), 
        userInfo,
        user.getPassword(), 
        authorities, user.getBooks());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public String getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public Set<Book> getBooks() {
    return books;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
