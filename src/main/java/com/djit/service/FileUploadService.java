package com.djit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadService.class);
    
    @Value("${file.upload.path:C:/uploads/}")
    private String uploadBasePath;
    
    public String uploadImage(MultipartFile file, String subFolder) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }
        
        
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("파일명이 없습니다.");
        }
        
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!extension.matches("\\.(jpg|jpeg|png|gif|webp)$")) {
            throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다. (jpg, jpeg, png, gif, webp)");
        }
        
       
        String fullUploadPath = uploadBasePath + subFolder + "/";
        File uploadDir = new File(fullUploadPath);
        
        
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            if (created) {
                LOGGER.info("업로드 디렉토리 생성: {}", fullUploadPath);
            } else {
                LOGGER.error("업로드 디렉토리 생성 실패: {}", fullUploadPath);
                throw new IOException("업로드 디렉토리 생성에 실패했습니다.");
            }
        }
        
        
        String filename = UUID.randomUUID().toString() + extension;
        Path filePath = Paths.get(fullUploadPath + filename);
        
        
        Files.copy(file.getInputStream(), filePath);
        LOGGER.info("파일 업로드 완료: {}", filePath);
        
        
        return "/uploads/" + subFolder + "/" + filename;
    }
    
    public boolean deleteFile(String fileUrl) {
        try {
            if (fileUrl == null || !fileUrl.startsWith("/uploads/")) {
                return false;
            }
            
            
            String relativePath = fileUrl.replace("/uploads/", "");
            String fullPath = uploadBasePath + relativePath;
            
            File file = new File(fullPath);
            if (file.exists() && file.delete()) {
                LOGGER.info("파일 삭제 완료: {}", fullPath);
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("파일 삭제 실패: {}", e.getMessage());
        }
        return false;
    }
}