package com.example.culturecontentapp.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  void store(MultipartFile file);

  void deleteAll();

  void init();
}
