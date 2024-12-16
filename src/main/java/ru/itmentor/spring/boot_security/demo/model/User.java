package ru.itmentor.spring.boot_security.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



import java.util.Collection;

import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String username;

    @Column(name = "lastname", nullable = false)
    @NotEmpty(message = "LastName should not be empty")
    @Size(min = 2, max = 30, message = "LastName should be between 2 and 30 characters")
    private String lastName;

    @Column(name = "email", nullable = false)
    @Email
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Password can't be empty")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
}