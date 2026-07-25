package org.example.crtachbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Document Dto class
 * user for creating and
 * updating documents
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {

    @NotBlank(message = "Document name must not be blank")
    @Size(min = 5, max = 30)
    private String name;

    @NotNull(message = "Creator id cannot be null")
    private Long creatorId;
}
