package com.byebyechallan.controller;

import com.byebyechallan.dto.FileResponseDto;
import com.byebyechallan.service.InMemoryFileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file")
@Tag(name = "File Controller", description = "Download and Upload the file")
public class FileController {

  private final InMemoryFileService fileService;

  public FileController(InMemoryFileService fileService){
    this.fileService=fileService;
  }

  @PostMapping("/upload")
  public FileResponseDto upload(
      @RequestParam("file") MultipartFile file,
      HttpServletRequest request) throws IOException {
    return fileService.upload(file, request);
  }

  @GetMapping("/{fileName}")
  public ResponseEntity<Resource> download(
      @PathVariable String fileName) throws IOException {
    return fileService.download(fileName);
  }
}
