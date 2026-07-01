package com.fileupload;

import com.fileupload.model.FileMetadata;
import com.fileupload.model.FileMetadataRepository;
import com.fileupload.service.FileUploadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileUploadServiceTest {

    @Mock 
    private FileMetadataRepository fileMetadataRepository;

    @InjectMocks 
    private FileUploadService fileUploadService;

    @Test
    void uploadFile_success() throws IOException {
        // Mocking a sample MultipartFile
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.pdf", "application/pdf", "PDF content".getBytes());

        // Stubbing repository save method to return the saved entity
        when(fileMetadataRepository.save(any(FileMetadata.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act: Calling the service layer with exact 1 argument matching main service class
        FileMetadata response = fileUploadService.uploadFile(file);

        // Assert
        assertNotNull(response);
        assertEquals("test.pdf", response.getFileName());
        assertEquals("application/pdf", response.getContentType());
        assertNotNull(response.getS3Url());
        
        // Verify database interactions
        verify(fileMetadataRepository, times(1)).save(any(FileMetadata.class));
    }

    @Test
    void uploadFile_emptyFile_throwsException() {
        // Creating an empty file
        MockMultipartFile file = new MockMultipartFile(
                "file", "empty.pdf", "application/pdf", new byte[0]);

        // Assert that calling with empty file throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            fileUploadService.uploadFile(file);
        });

        // Verify that database repository was never called for empty files
        verifyNoInteractions(fileMetadataRepository);
    }
}