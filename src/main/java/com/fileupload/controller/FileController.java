package com.fileupload.controller;

import com.fileupload.model.FileMetadata;
import com.fileupload.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin("*")
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    private final FileUploadService fileUploadService;

    // Fixed Constructor Assignment
    public FileController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("Request received to upload file: {}", file.getOriginalFilename());
        try {
            FileMetadata savedFile = fileUploadService.uploadFile(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFile);
        } catch (IllegalArgumentException e) {
            log.warn("Validation error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Upload process failed: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<FileMetadata>> getAllFiles() {
        return ResponseEntity.ok(fileUploadService.getAllFiles());
    }
}