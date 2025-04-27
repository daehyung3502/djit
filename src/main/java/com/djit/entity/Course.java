package com.djit.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "course_name", nullable = false)
    private String courseName;
    
    @Column(name = "course_tagline")
    private String courseTagline;
    
     
    @Column(name = "course_description", columnDefinition = "TEXT")
    private String courseDescription;
    
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @Column(name = "duration")
    private String duration;
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "course_subjects", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "subject")
    private List<String> subjects = new ArrayList<>();
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "course_educational_goals", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "goal", columnDefinition = "TEXT")
    private List<String> educationalGoals = new ArrayList<>();
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "course_roadmap_month1", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "content")
    private List<String> roadmapMonth1 = new ArrayList<>();
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "course_roadmap_month2", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "content")
    private List<String> roadmapMonth2 = new ArrayList<>();
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "course_roadmap_month3", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "content")
    private List<String> roadmapMonth3 = new ArrayList<>();
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "course_roadmap_month4", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "content")
    private List<String> roadmapMonth4 = new ArrayList<>();
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "course_roadmap_month5", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "content")
    private List<String> roadmapMonth5 = new ArrayList<>();
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "course_roadmap_month6", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "content")
    private List<String> roadmapMonth6 = new ArrayList<>();
}
