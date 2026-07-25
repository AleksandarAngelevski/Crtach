package org.example.crtachbackend.mapper;

import org.example.crtachbackend.dto.DocumentDto;
import org.example.crtachbackend.model.Document;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the document entity
 */
@Mapper(componentModel = "spring")
public interface DocumentMapper {

    /**
     * Method that maps document
     * to document dto
     *
     * @param document - the document
     *                 entity param
     *                 that's supposed
     *                 to be mapped
     *
     * @return - returns a document dto
     */
    @Mapping(source = "creator.id", target = "creatorId")
    DocumentDto toDocumentDto(Document document);

    /**
     * Method that maps document dto
     * to document entity
     *
     * @param documentDto -  the document dto
     *                    param that's supposed
     *                    to be mapped
     *
     * @return - returns a document entity
     */
    Document toDocument(DocumentDto documentDto);

}
