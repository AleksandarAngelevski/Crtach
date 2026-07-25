package org.example.crtachbackend.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.crtachbackend.enums.Permissions;

import java.util.Set;

/**
 * User Permissions entity class
 * used for the permissions
 * of users in a document
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPermissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "user_permission_list",
            joinColumns = @JoinColumn(name = "document_id")
    )
    @Column(name = "permission")
    @Enumerated(EnumType.STRING)
    private Set<Permissions> permissions;
}
