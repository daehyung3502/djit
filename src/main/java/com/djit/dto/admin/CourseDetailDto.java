package com.djit.dto.admin;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDetailDto {
    private Long id;
    private String courseName;
    private String courseTagline;
    private String courseDescription;
    private LocalDate startDate;
    private String duration;
    private List<String> subjects;
    private List<String> educationalGoals;
    
    
    private List<String> roadmapMonth1;
    private List<String> roadmapMonth2;
    private List<String> roadmapMonth3;
    private List<String> roadmapMonth4;
    private List<String> roadmapMonth5;
    private List<String> roadmapMonth6;
}