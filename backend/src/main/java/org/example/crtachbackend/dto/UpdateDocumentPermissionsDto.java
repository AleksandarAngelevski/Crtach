package org.example.crtachbackend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.crtachbackend.enums.Permissions;

import java.util.Set;

/**
 * A update document
 * permissions dto class
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDocumentPermissionsDto {

    @NotEmpty(message = "User Id cannot be null")
    private Long userId;

    @NotNull(message = "Permissions Set cannot be null")
    private Set<Permissions> permissions;
}
