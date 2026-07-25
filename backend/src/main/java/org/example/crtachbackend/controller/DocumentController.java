package org.example.crtachbackend.controller;

import jakarta.validation.Valid;
import org.example.crtachbackend.dto.DocumentDto;
import org.example.crtachbackend.dto.UpdateDocumentPermissionsDto;
import org.example.crtachbackend.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class used for the document entity
 */
@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * Method that gets a document by id
     *
     * @param id - the id param used for
     *           searching for the document
     *
     * @return - returns the document if it
     *          exists and status 200 ok
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> getDocument(@PathVariable Long id){

        return ResponseEntity.ok(documentService.getDocumentById(id));
    }

    /**
     * Method used for creating a document
     *
     * @param documentDto - the document dto
     *                    param used for
     *                    creating a document
     *
     * @return - returns the created document
     *          and status 201 created
     */
    @PostMapping
    public ResponseEntity<DocumentDto> createDocument(@Valid @RequestBody DocumentDto documentDto){

        return new ResponseEntity<>(documentService.createDocument(documentDto),  HttpStatus.CREATED);
    }

    /**
     * Method used for updating a document
     *
     * @param documentId - the document id
     *                   param used for
     *                   searching for the
     *                   document
     *
     * @param userId - the user id param used
     *               for finding the user
     *               that wants to update the
     *               document
     *
     * @param documentDto - the document dto
     *                    param used for
     *                    updating the document
     *
     * @return - returns the updated document
     *          and status 201 created
     */
    @PutMapping("/{documentId}")
    public ResponseEntity<DocumentDto> updateDocument(@PathVariable Long documentId, @RequestParam Long userId, @Valid @RequestBody DocumentDto documentDto){

        return new ResponseEntity<>(documentService.updateDocument(documentId, userId, documentDto), HttpStatus.CREATED);
    }

    /**
     * Method used for deleting a document
     *
     * @param id - the id param used for
     *           searching for the document
     *           to be deleted
     *
     * @return - returns no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id){

        documentService.deleteDocumentById(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Method used for granting permissions
     * to users for a document
     *
     * @param documentId - the document id param
     *                   used of finding
     *                   the document
     *
     * @param updateDocumentPermissionsDto - the
     *                                      update permissions dto
     *                                     used for updating the
     *                                     permissions on a document
     *
     * @return - returns no content
     */
    @PatchMapping("/{documentId}/permissions")
    public ResponseEntity<Void> updatePermissions(@PathVariable Long documentId, @Valid @RequestBody UpdateDocumentPermissionsDto  updateDocumentPermissionsDto){

        documentService.givePermissions(documentId, updateDocumentPermissionsDto);

        return ResponseEntity.noContent().build();
    }

}
