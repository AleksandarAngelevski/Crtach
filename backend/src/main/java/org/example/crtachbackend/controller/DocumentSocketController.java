package org.example.crtachbackend.controller;

import org.example.crtachbackend.service.DocumentService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

//TODO you should create an inmemory or redis cache
//TODO function that would work as a proxy to save changes
//TODO all the time and then create a scheduler fucntion
//TODO that would save the changes to db on  every 5 seconds\

/**
 * Document Web Socket Controller class
 */
@Controller
public class DocumentSocketController {

    private final DocumentService documentService;

    public DocumentSocketController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * Method used to send changes
     * to document to all users
     * concurrently
     *
     * @param docId - the document id param used for
     *              getting the document to update
     *
     * @param userId - the user id param used for getting
     *               the user that makes the changes
     *
     * @param docUpdate - the encoded document update param
     *                  used for updating the document state
     *
     * @return - returns the changes to the document
     */
    @MessageMapping("/doc/{docId}/sync")
    @SendTo("/topic/doc/{docId}")
    public String handleSync(@DestinationVariable Long docId, @Header("userId") Long userId,  String docUpdate){

        documentService.changeDocumentState(docId, userId, docUpdate);

        return docUpdate;
    }

}
