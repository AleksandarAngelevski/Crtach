package org.example.crtachbackend.service.implementation;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.example.crtachbackend.dto.DocumentDto;
import org.example.crtachbackend.dto.UpdateDocumentPermissionsDto;
import org.example.crtachbackend.enums.Permissions;
import org.example.crtachbackend.mapper.DocumentMapper;
import org.example.crtachbackend.model.Document;
import org.example.crtachbackend.model.User;
import org.example.crtachbackend.model.UserPermissions;
import org.example.crtachbackend.repository.DocumentRepository;
import org.example.crtachbackend.repository.UserRepository;
import org.example.crtachbackend.service.DocumentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;

import java.util.HashSet;
import java.util.Set;

/**
 * Document service implementation class
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final UserRepository userRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository, DocumentMapper documentMapper, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
        this.userRepository = userRepository;
    }

    /**
     * Method used for getting a document
     * by id
     *
     * @param id - the id param used for
     *           searching for the document
     *
     * @return - returns the found document dto
     */
    @Override
    public DocumentDto getDocumentById(Long id) {

        return documentMapper.toDocumentDto(documentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Document not found")));
    }

    /**
     * Method used for creating a document
     *
     * @param documentDto - the document dto
     *                    param used for
     *                    creating a document
     *
     * @return - returns the created document dto
     */
    @Transactional
    @Override
    public DocumentDto createDocument(DocumentDto documentDto) {

        if (documentRepository.findDocumentByName(documentDto.getName()).isPresent()) {

            throw new EntityExistsException("Document with that name already exists");
        }


        Document document = documentMapper.toDocument(documentDto);

        document.setCreator(userRepository.findById(documentDto.getCreatorId()).orElseThrow(() -> new EntityNotFoundException("User not found")));

        Set<UserPermissions> permissions = new HashSet<>();

        UserPermissions userPermissions = new UserPermissions();

        userPermissions.setEmail(document.getCreator().getEmail());
        userPermissions.setPermissions(Set.of(Permissions.values()));

        permissions.add(userPermissions);

        document.setUserPermissions(permissions);

        return documentMapper.toDocumentDto(documentRepository.save(document));
    }

    /**
     * Method used for updating a document
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
    @Transactional
    @Override
    public DocumentDto updateDocument(Long documentId, Long userId, DocumentDto documentDto) {

        Document  document = documentRepository.findById(documentId).orElseThrow(() -> new EntityNotFoundException("Document not found"));


        if (!document.getCreator().getId().equals(userId)) {

            throw new AccessDeniedException("Access to update document denied, User must be creator of document");
        }

        document.setName(documentDto.getName());

        return documentMapper.toDocumentDto(documentRepository.save(document));
    }

    /**
     * Method used for deleting a document by id
     *
     * @param id - the id param used for finding
     *           the document to delete
     */
    @Transactional
    @Override
    public void deleteDocumentById(Long id) {

        if (id == null) {

            throw new IllegalArgumentException("Document id cannot be null");
        }

        documentRepository.deleteById(id);
    }

    /**
     * Method used for editing document state
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
    @Transactional
    @Override
    public void changeDocumentState(Long documentId, Long userId, String state) {

        if (documentId == null || userId == null) {

            throw new IllegalArgumentException("Document id and user id cannot be null");
        }

        Document document = documentRepository.findById(documentId).orElseThrow(() -> new EntityNotFoundException("Document not found"));

        document.setDocumentState(state);

        documentRepository.save(document);
    }

    /**
     * Method used for giving permissions to users
     * in a document
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
    @Transactional
    @Override
    public void givePermissions(Long documentId, UpdateDocumentPermissionsDto  updateDocumentPermissionsDto) {

        if (documentId == null) {

            throw new IllegalArgumentException("Document id cannot be null");
        }

        User user = userRepository.findById(updateDocumentPermissionsDto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Document  document = documentRepository.findById(documentId).orElseThrow(() -> new EntityNotFoundException("Document not found"));

        UserPermissions userPermissions = new UserPermissions();

        userPermissions.setEmail(user.getEmail());

        userPermissions.setPermissions(updateDocumentPermissionsDto.getPermissions());

        Set<UserPermissions> existingPermissions = document.getUserPermissions();

        if ( existingPermissions == null) {

            existingPermissions = new HashSet<>();
        } else {

            existingPermissions.removeIf(p -> p.getEmail().equals(user.getEmail()));
        }

        existingPermissions.add(userPermissions);
        document.setUserPermissions(existingPermissions);

        documentRepository.save(document);
    }

}
