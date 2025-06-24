package com.djit.controller.main;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.djit.dto.admin.CourseDetailDto;
import com.djit.dto.admin.CourseDto;
import com.djit.dto.admin.PortfolioDto;
import com.djit.service.admin.AdminService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DjitController {
    
    private final AdminService adminService;
    
    @GetMapping({"/"})
    public String main(Model model) {
        log.info("메인 페이지 접속");
        List<CourseDto> courses = adminService.getCourses();
        List<PortfolioDto> portfolios = adminService.getAllPortfolios();
        model.addAttribute("courses", courses);
        model.addAttribute("portfolios", portfolios);
        log.info("Thymeleaf 템플릿 'pages/main' 렌더링 시작");
        return "pages/main";
    }
    
    @GetMapping("/course")
    public String course(Model model, @RequestParam(value = "courseId", required = false) Long courseId) {
        log.info("과정 페이지 접속");
        log.info("courseId: {}", courseId);
        
        List<CourseDto> courses = adminService.getCourses();
        List<CourseDetailDto> courseDetails = courses.stream()
            .map(course -> adminService.getCourseDetailById(course.getId()))
            .toList();
        model.addAttribute("courseDetails", courseDetails);
        
        if (courseId != null) {
            model.addAttribute("selectedCourseId", courseId);
        }
        return "pages/course";
    }
    
    @GetMapping("/intro")
    public String intro() {
        log.info("교육원 소개 페이지 접속");
        return "pages/intro";
    }
    
    @GetMapping("/reg-info")
    public String regInfo() {
        log.info("입학안내 페이지 접속");
        return "pages/regInfo";
    }
    
    @GetMapping("/reg-app")
    public String regApp() {
        log.info("온라인접수 페이지 접속");
        return "pages/regApp";
    }
    
    @GetMapping("/emp")
    public String emp() {
        log.info("취업정보소개 페이지 접속");
        return "pages/emp";
    }
}