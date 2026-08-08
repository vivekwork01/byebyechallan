package com.byebyechallan.service;

import static com.byebyechallan.utils.Constant.DOCUMENTS_DIR;
import static com.byebyechallan.utils.Constant.FILE_API_PATH;

import com.byebyechallan.dto.FileResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class InMemoryFileService {

  @Value("${app.base-url:http://localhost:8081}")
  private String baseUrl;

  public FileResponseDto upload(long userId, MultipartFile file, HttpServletRequest request)
      throws IOException {
    Path userDir = getUserDir(userId);
    Files.createDirectories(userDir);

    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
    Path filePath = userDir.resolve(fileName);
    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

    return FileResponseDto.builder()
        .fileName(fileName)
        .filePath(filePath.toString())
        .fileUrl(buildFileUrl(userId, fileName, request))
        .build();
  }

  public ResponseEntity<Resource> download(long userId, String fileName)
      throws MalformedURLException {
    validateFileName(fileName);
    Path path = buildFilePath(userId, fileName);

    if (!Files.exists(path)) {
      return ResponseEntity.notFound().build();
    }
    Resource resource = new UrlResource(path.toUri());
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(resource);
  }

  public Path buildFilePath(long userId, String fileName) {
    validateFileName(fileName);
    return getUserDir(userId).resolve(fileName);
  }

  public String buildFileUrl(long userId, String fileName) {
    validateFileName(fileName);
    return baseUrl + FILE_API_PATH + "/" + userId + "/" + fileName;
  }

  public String buildFileUrl(long userId, String fileName, HttpServletRequest request) {
    validateFileName(fileName);
    String urlBase = request.getScheme() + "://" + request.getServerName() + ":"
        + request.getServerPort();
    return urlBase + FILE_API_PATH + "/" + userId + "/" + fileName;
  }

  private Path getUserDir(long userId) {
    return Paths.get(DOCUMENTS_DIR, String.valueOf(userId));
  }

  private void validateFileName(String fileName) {
    if (fileName == null || fileName.isBlank() || fileName.contains("..")
        || fileName.contains("/") || fileName.contains("\\")) {
      throw new IllegalArgumentException("Invalid file name");
    }
  }
}
