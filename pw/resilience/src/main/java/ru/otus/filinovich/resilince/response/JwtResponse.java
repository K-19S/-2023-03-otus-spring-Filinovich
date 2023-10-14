package ru.otus.filinovich.resilince.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.filinovich.resilince.model.Book;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {
  private String accessToken;

  private String tokenType = "Bearer";

  private String id;

  private String username;

  private String email;

  private List<String> roles;

  private Set<Book> books;

  public JwtResponse(String accessToken, String id, UserInfo userInfo, List<String> roles, Set<Book> books) {
    this.accessToken = accessToken;
    this.id = id;
    this.username = userInfo.getUsername();
    this.email = userInfo.getEmail();
    this.roles = roles;
    this.books = books;
  }
}
