package com.getyourway.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.getyourway.Constants;
import com.getyourway.user.validator.UsernameConstraint;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Column(nullable=false, unique=true)
    private @Id
    @GeneratedValue Long id;

    @Column(nullable=false, unique=true)
    @Size(min = 5, max = 20)
    private String username;

    @Column(nullable=false)
    @Size(min = 8)
    private @JsonIgnore String password;

    @Column(nullable=false, columnDefinition = "VARCHAR(20) default 'ROLE_USER'")
    private String roles;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.setPassword(password); // Encode password
        this.roles = Constants.USER;

    }

    // Getters and setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public String getRoles() {
        return roles;
    }
    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User user = (User) o;

        //Two bcrypt passwords will never be equals
        return Objects.equals(this.id, user.id) && Objects.equals(this.username, user.username)
                && Objects.equals(this.roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.username, this.password, this.roles);
    }

    @Override
    public String toString() {
        return "User: " + username + ", id: " + id;
    }

}
