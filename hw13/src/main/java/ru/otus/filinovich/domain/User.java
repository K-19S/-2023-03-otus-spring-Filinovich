package ru.otus.filinovich.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<UserRole> userRoles;

    public User(String username, String password, Set<UserRole> userRoles) {
        this.username = username;
        this.password = password;
        this.userRoles = userRoles;
    }
}
