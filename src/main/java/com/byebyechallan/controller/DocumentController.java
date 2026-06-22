package com.byebyechallan.controller;

import com.byebyechallan.dto.DocumentRequestDto;
import com.byebyechallan.dto.UserDocumentDto;
import com.byebyechallan.entity.CoreDocumentEntity;
import com.byebyechallan.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/document")
@Tag(name = "Document Controller", description = "Api for managing the Documents")
public class DocumentController {

  private final DocumentService documentService;

  public DocumentController(DocumentService documentService) {
    this.documentService = documentService;
  }

  @GetMapping("/list")
  @Operation(summary = "Get the Document List", description = "Get the document list for a selected country, state, registration vehicle")
  public List<CoreDocumentEntity> getDocumentList(
      @RequestParam(name = "country", required = true) String country,
      @RequestParam(name = "state", required = true) String state,
      @RequestParam(name = "registration_type", required = true) String registrationType,
      @RequestParam("vehicle_type") String vehicleType,
      @RequestParam(name = "doc_type", required = false) String docType) {
    return documentService.getDocumentList(country, state, registrationType, vehicleType, docType);
  }

  @PostMapping("/{userId}/profile/{profileId}/registration/{vehicleRegistrationNo}")
  @Operation(summary="Save Document for a Profile", description = "Save the document for Profile of a User")
  public UserDocumentDto saveDocument(@PathVariable("userId") long userId,
      @PathVariable("profileId") long profileId,
      @PathVariable("vehicleRegistrationNo") String vehicleRegistrationNo,
      @RequestBody DocumentRequestDto documentRequestDto) {
    return documentService.saveDocument(userId, profileId, vehicleRegistrationNo,
        documentRequestDto);
  }

  @GetMapping("/{userId}/profile/{profileId}/registration/{vehicleRegistrationNo}")
  @Operation(summary = "Get all the Documents for a Profile", description = "Retrieve all documents associated with a specific profile")
  public List<UserDocumentDto> getAllDocuments(@PathVariable("userId") long userId,
      @PathVariable("profileId") long profileId,
      @PathVariable("vehicleRegistrationNo") String vehicleRegistrationNo) {
    return documentService.getAllDocuments(userId, profileId, vehicleRegistrationNo);
  }
}
