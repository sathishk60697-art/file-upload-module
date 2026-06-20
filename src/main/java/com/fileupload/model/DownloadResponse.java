package com.fileupload.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownloadResponse {
    private String fileId;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private String presignedUrl;
    private long expiresInSeconds;
}
