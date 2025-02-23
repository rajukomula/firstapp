package com.example.controller;

import com.example.model.FileEntity;
import com.example.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String contentType = file.getContentType();

        if (contentType == null || !isValidFileType(contentType)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file type. Only images, videos, and PDFs are allowed.");
        }

        try {
            FileEntity savedFile = fileStorageService.storeFile(file);
            return ResponseEntity.ok("File uploaded successfully with ID: " + savedFile.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed.");
        }
    }

    private boolean isValidFileType(String contentType) {
        return contentType.startsWith("image/") ||   // Images (jpeg, png, etc.)
               contentType.startsWith("video/") ||   // Videos (mp4, avi, etc.)
               contentType.equals("application/pdf"); // PDFs only
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long fileId) {
        Optional<FileEntity> fileEntityOptional = fileStorageService.getFile(fileId);
        if (fileEntityOptional.isPresent()) {
            FileEntity fileEntity = fileEntityOptional.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFileName() + "\"")
                    .body(fileEntity.getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
