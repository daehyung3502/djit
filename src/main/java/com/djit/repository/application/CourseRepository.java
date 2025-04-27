package com.djit.repository.application;

import org.springframework.data.jpa.repository.JpaRepository;

import com.djit.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
