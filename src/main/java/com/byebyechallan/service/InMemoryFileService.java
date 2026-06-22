package com.byebyechallan.service;

import static com.byebyechallan.utils.Constant.DOCUMENTS_DIR;

import com.byebyechallan.dto.FileResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class InMemoryFileService {

  public FileResponseDto upload(MultipartFile file, HttpServletRequest request) throws IOException {
    Files.createDirectories(Paths.get(DOCUMENTS_DIR));

    String fileName =
        UUID.randomUUID() + "_" + file.getOriginalFilename();
    Path filePath = Paths.get(DOCUMENTS_DIR, fileName);
    Files.copy(
        file.getInputStream(),
        filePath,
        StandardCopyOption.REPLACE_EXISTING
    );
    String fileUrl =
        request.getScheme() + "://" +
            request.getServerName() + ":" +
            request.getServerPort() +
            "/api/files/" + fileName;

    return FileResponseDto.builder()
        .fileName(fileName)
        .fileUrl(fileUrl)
        .filePath(filePath.toString())
        .build();
  }

  public ResponseEntity<Resource> download(String fileName) throws MalformedURLException {
    Path path = Paths.get(DOCUMENTS_DIR, fileName);

    if (!Files.exists(path)) {
      return ResponseEntity.notFound().build();
    }
    Resource resource = new UrlResource(path.toUri());
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(resource);
  }
}
