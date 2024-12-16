package ru.itmentor.spring.boot_security.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@Entity
@Table(name="roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public Role(String name) {
        this.name = name;
    }
}