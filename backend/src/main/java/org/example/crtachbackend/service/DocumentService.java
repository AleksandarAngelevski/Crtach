package org.example.crtachbackend.service;

import org.example.crtachbackend.dto.DocumentDto;
import org.example.crtachbackend.dto.UpdateDocumentPermissionsDto;

/**
 * The document service interface
 */
public interface DocumentService {

    /**
     * Method used for getting a document
     * by document id
     *
     * @param id - the id param used for
     *           searching for the document
     *
     * @return - returns a document dto
     */
    DocumentDto getDocumentById(Long id);

    /**
     * Method used for creating a document
     *
     * @param documentDto - the document dto
     *                    param used for
     *                    creating a document
     *
     * @return - returns a document dto
     */
    DocumentDto createDocument(DocumentDto documentDto);

    /**
     * Method used for updating document
     *
     * @param documentId - the document id param
     *                   used for getting the
     *                   document that's supposed
     *                   to be updated
     *
     * @param userId - the user id param used for
     *               finding the user that wants
     *               to update the document
     *
     * @param documentDto - the document dto param
     *                    sued for updating the
     *                    document
     *
     * @return - returns the updated document dto
     */
    DocumentDto updateDocument(Long documentId, Long userId, DocumentDto documentDto);

    /**
     * Method used for deleting a document
     *
     * @param id - the id param used for finding
     *           the document to delete
     */
    void deleteDocumentById(Long id);

    /**
     * Method used for editing the document state
     *
     * @param documentId - the document id param
     *                   used for searching for the
     *                   document to be edited
     *
     * @param userId - the user id param used for
     *               getting the user that's editing
     *               the document
     *
     * @param state - the state param used to update
     *              the document state
     */
    void changeDocumentState(Long documentId, Long userId, String state);

    /**
     * Method used for giving permissions to users
     * for a certain document
     *
     * @param documentId - the document id param
     *                   used for finding the
     *                   document to add
     *                   permissions to
     *
     * @param updateDocumentPermissionsDto - the
     *                                     update document
     *                                     permissions dto
     *                                     used for
     *                                     updating the
     *                                     document
     *                                     permissions
     */
    void givePermissions(Long documentId, UpdateDocumentPermissionsDto updateDocumentPermissionsDto);
}
