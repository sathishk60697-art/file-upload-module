package com.fileupload.service;

import com.fileupload.model.FileMetadata;
import com.fileupload.model.FileMetadataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileUploadService {

    private static final Logger log = LoggerFactory.getLogger(FileUploadService.class);

    private final FileMetadataRepository fileMetadataRepository;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    // Fixed Constructor Assignment
    public FileUploadService(FileMetadataRepository fileMetadataRepository) {
        this.fileMetadataRepository = fileMetadataRepository;
    }

    public FileMetadata uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File empty-ah irukka koodathu!");
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String mockS3Url = "https://" + bucketName + ".s3.amazonaws.com/" + fileName;

        log.info("Saving file metadata to database for: {}", fileName);

        FileMetadata metadata = new FileMetadata(
                file.getOriginalFilename(),
                mockS3Url,
                file.getSize(),
                file.getContentType(),
                LocalDateTime.now()
        );

        return fileMetadataRepository.save(metadata);
    }

    public List<FileMetadata> getAllFiles() {
        return fileMetadataRepository.findAll();
    }
}