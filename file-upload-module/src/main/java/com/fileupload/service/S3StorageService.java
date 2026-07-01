package com.fileupload.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Service
public class S3StorageService {

    // 1. Fixed 'log cannot be resolved'
    private static final Logger log = LoggerFactory.getLogger(S3StorageService.class);

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    // 2. Fixed 'blank final field may not have been initialized'
    public S3StorageService(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    // Example methods - unga requirement padi use pannikonga
    public void testLog() {
        log.info("S3StorageService successfully initialized with bucket: {}", bucketName);
    }
}