package org.example.crtachbackend.repository;

import org.example.crtachbackend.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A document repository interface used for
 * interacting with the document db
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document,Long> {

    /**
     * Method used for finding document
     * by document name
     *
     * @param name - the name param used for
     *             searching for the document
     *
     * @return - returns the document if it
     *          exists
     */
    Optional<Document> findDocumentByName(String name);
}
