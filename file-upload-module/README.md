# File Upload Module

A production-ready Spring Boot microservice for managing file uploads with AWS S3.

## Tech Stack
- **Java 17** + **Spring Boot 3.2**
- **AWS S3** (SDK v2) for storage
- **PostgreSQL** for metadata
- **Spring Security** for access control

## APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/files/upload` | Upload a file |
| GET | `/api/files/download/{fileId}` | Get pre-signed download URL |
| GET | `/api/files/{fileId}` | Get file metadata |
| GET | `/api/files?uploadedBy=user` | List files |
| DELETE | `/api/files/{fileId}` | Delete a file |

## Setup

### 1. Environment Variables
```bash
export AWS_S3_BUCKET=your-bucket-name
export AWS_REGION=us-east-1
export AWS_ACCESS_KEY_ID=your-access-key
export AWS_SECRET_ACCESS_KEY=your-secret-key
export DB_USERNAME=postgres
export DB_PASSWORD=yourpassword
```

### 2. Run
```bash
mvn spring-boot:run
```

### 3. Upload Example
```bash
curl -X POST http://localhost:8080/api/files/upload \
  -u admin:password \
  -F "file=@/path/to/yourfile.pdf" \
  -F "uploadedBy=john" \
  -F "description=My document"
```

### 4. Download Example
```bash
curl http://localhost:8080/api/files/download/{fileId} \
  -u admin:password
# Returns a pre-signed S3 URL valid for 1 hour
```

## Security
- HTTP Basic Auth (extend with JWT for production)
- Server-side S3 encryption (AES-256)
- Pre-signed URLs expire after 1 hour (configurable)
- File type validation (allowlist-based)
- Max file size: 100MB (configurable)

## Project Structure
```
src/main/java/com/fileupload/
├── FileUploadApplication.java
├── config/
│   ├── AwsS3Config.java
│   └── SecurityConfig.java
├── controller/
│   └── FileController.java
├── exception/
│   ├── FileNotFoundException.java
│   ├── FileUploadException.java
│   ├── GlobalExceptionHandler.java
│   └── InvalidFileException.java
├── model/
│   ├── DownloadResponse.java
│   ├── FileMetadata.java
│   ├── FileMetadataRepository.java
│   ├── UploadRequest.java
│   └── UploadResponse.java
└── service/
    ├── FileUploadService.java
    └── S3StorageService.java
```
