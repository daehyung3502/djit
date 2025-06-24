package com.djit.dto.admin;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDto {
    private Long id;
    private String title;
    private String githubUrl;
    private String imageUrl;
    private MultipartFile imageFile;
}