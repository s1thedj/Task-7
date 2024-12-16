package ru.itmentor.spring.boot_security.demo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserDTO {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String username;

    @NotEmpty(message = "Password can't be empty")
    private String password;

    @NotEmpty(message = "LastName should not be empty")
    @Size(min = 2, max = 30, message = "LastName should be between 2 and 30 characters")
    private String lastName;

    private Set<String> roles;

    @Email
    @NotEmpty(message = "Email should not be empty")
    private String email;



    public UserDTO(String username, String password, Set<String> roles, String email, String lastName) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
        this.lastName = lastName;
    }
}
