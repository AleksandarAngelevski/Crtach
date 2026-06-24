package org.example.crtachbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.crtachbackend.enums.UserRole;

/**
 * A user dto class
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "First name must not be blank")
    @Size(min = 3, max = 20)
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    @Size(min = 3, max = 20)
    private String lastName;

    @NotBlank(message = "Username must not be blank")
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "Email must not be blank")
    @Size(min = 3, max = 20)
    private String email;

    private UserRole role;
}
