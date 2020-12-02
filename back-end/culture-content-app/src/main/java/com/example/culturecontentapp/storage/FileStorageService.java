package com.example.culturecontentapp.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService implements StorageService {

  private final Path root = Paths.get("src/main/resources/static/uploads");

  @Override
  public String store(MultipartFile file) {
    try {
      if (file.isEmpty()) {
        throw new StorageException("Failed to store empty file");
      }
      String originalFilename = file.getOriginalFilename();
      if (originalFilename != null) {
        originalFilename = java.util.UUID.randomUUID()
            + originalFilename.substring(originalFilename.lastIndexOf("\\.") + 1);
      }
      Path destinationFile = this.root.resolve(Paths.get(originalFilename)).normalize().toAbsolutePath();
      if (!destinationFile.getParent().equals(this.root.toAbsolutePath())) {
        throw new StorageException("Cannot store file outside current directory");
      }

      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      }

      return originalFilename;
    } catch (IOException e) {
      throw new StorageException("Failed to store file");
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(root.toFile());
  }

  @Override
  public void init() {
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      throw new StorageException("Could not initialize storage");
    }
  }
}
