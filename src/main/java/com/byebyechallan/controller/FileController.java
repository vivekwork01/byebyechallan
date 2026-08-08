package com.byebyechallan.controller;

import com.byebyechallan.dto.FileResponseDto;
import com.byebyechallan.service.InMemoryFileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/file")
@Tag(name = "File Controller", description = "Download and Upload the file")
public class FileController {

  private final InMemoryFileService fileService;

  public FileController(InMemoryFileService fileService) {
    this.fileService = fileService;
  }

  @PostMapping("/{userId}/upload")
  public FileResponseDto upload(
      @PathVariable long userId,
      @RequestParam("file") MultipartFile file,
      HttpServletRequest request) throws IOException {
    return fileService.upload(userId, file, request);
  }

  @GetMapping("/{userId}/{fileName}")
  public ResponseEntity<Resource> download(
      @PathVariable long userId,
      @PathVariable String fileName) throws IOException {
    return fileService.download(userId, fileName);
  }
}
