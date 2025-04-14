package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/minio")
@CrossOrigin(origins = "*") // ✅ Allow from frontend (e.g. React)
public class MinioController {

    @Autowired
    private MinioService minioService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // ✅ This returns UUID_filename
            String storedFileName = minioService.uploadBinary(file);

            // ✅ This should use the UUID_filename to fetch URL
            String fileUrl = minioService.getFileUrl(storedFileName);

            return ResponseEntity.ok(fileUrl); // ✅ Just return the final URL to React
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Upload failed: " + e.getMessage());
        }
    }
}