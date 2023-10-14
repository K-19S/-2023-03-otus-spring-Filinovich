package ru.otus.filinovich.authenticationservice.payload.response;

import ru.otus.filinovich.authenticationservice.models.Book;

import java.util.List;
import java.util.Set;

public class JwtResponse {
  private String token;

  private String type = "Bearer";

  private String id;

  private String username;

  private String email;

  private List<String> roles;

  private Set<Book> books;

  public JwtResponse(String accessToken, String id, UserInfo userInfo, List<String> roles, Set<Book> books) {
    this.token = accessToken;
    this.id = id;
    this.username = userInfo.getUsername();
    this.email = userInfo.getEmail();
    this.roles = roles;
    this.books = books;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }
}
