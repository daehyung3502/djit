package com.djit.controller.admin;
import com.djit.dto.admin.ApplicationResponseDto;
import com.djit.dto.admin.ApplicationSummaryDto;
import com.djit.dto.admin.CalendarDto;
import com.djit.dto.admin.ConsultationDto;
import com.djit.dto.admin.ConsultationResponseDto;
import com.djit.dto.admin.CourseDetailDto;
import com.djit.dto.admin.CourseDto;
import com.djit.dto.admin.PortfolioDto;
import com.djit.dto.client.ApplicationModifyDto;
import com.djit.service.FileUploadService;
import com.djit.service.admin.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	private final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	private final AdminService adminService;
	private final FileUploadService fileUploadService;


	@GetMapping("/list")
	public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
		LOGGER.info("Admin list page");
		Page<ApplicationSummaryDto> ApplicationSummaryDto = adminService.paging(pageable);
		int blockLimit = 5;
		int startPage = ((int) Math.ceil((double) pageable.getPageNumber() / (double) blockLimit) - 1) * blockLimit + 1;
		int endPage = Math.min(startPage + blockLimit - 1, ApplicationSummaryDto.getTotalPages());
		model.addAttribute("list", ApplicationSummaryDto);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "admin/list";
	}

	@GetMapping("/applicationDetail/{number}")
	public String applicationDetail(@PageableDefault(page = 1) Pageable pageable, @PathVariable("number") Long number,
			Model model) {
		LOGGER.info(number + " : number 값 확인");
		ApplicationResponseDto applicationResponseDto = adminService.getApplication(number);
		model.addAttribute("applicationResponseDto", applicationResponseDto);
		model.addAttribute("pageable", pageable);
		return "admin/applicationDetail";
	}

	@PostMapping("/consultation")
	public ResponseEntity<Map<String, String>> consultationApplication(@RequestBody ConsultationDto consultationDto) {
		LOGGER.info("상담 예약 신청");
		ConsultationResponseDto savedDto = adminService.consultationApplication(consultationDto);
		String consultationDateTimeStr = savedDto.getConsultationDateTimeStr();
		Map<String, String> responseBody = Map.of("message", "상담 예약 성공", "consultationDateTimeStr",
				consultationDateTimeStr);
		return ResponseEntity.ok(responseBody);
	}
	
	@DeleteMapping("/consultation/{id}")
	public ResponseEntity<Void> deleteConsultation(@PathVariable("id") Long id) {
		LOGGER.info("예약 삭제");
		LOGGER.info("삭제할 정보" + id);
		adminService.deleteConsultation(id);
		
		return ResponseEntity.ok().build();
	}

	@GetMapping("/applicationModify/{id}")
	public String applicationModify(@PageableDefault(page = 1) Pageable pageable, @PathVariable("id") Long id, Model model){
		LOGGER.info("Admin applicationModify page");
		LOGGER.info(id + " : number 값 확인");
		LOGGER.info(pageable.getPageNumber() + " : pageable 값 확인");
		ApplicationResponseDto applicationResponseDto = adminService.getApplication(id);
		model.addAttribute("applicationResponseDto", applicationResponseDto);
		model.addAttribute("pageable", pageable);
		return "admin/applicationModify";

	}

	@PostMapping("/applicationModify/{id}")
	public ResponseEntity<Map<String, Long>> applicationModify(@PathVariable("id") Long id, @RequestBody ApplicationModifyDto applicationModifyDto) {
		LOGGER.info("Admin applicationModify update page");
		LOGGER.info(id + " : number 값 확인");
		adminService.modifyApplication(applicationModifyDto, id);
		Map<String, Long> responseBody = Map.of("number", id);
		return ResponseEntity.ok(responseBody);
	}
	@DeleteMapping("/applicationModify/{id}")
	public ResponseEntity<Void> deleteApplication(@PathVariable("id") Long id) {
		LOGGER.info("Admin applicationModify delete page");
		LOGGER.info(id + " : number 값 확인");
		adminService.deleteApplication(id);
		return ResponseEntity.ok().build();
	}
		
	

	@GetMapping("/calendar")
	public String showCalendar(HttpServletRequest request, @RequestParam(name = "year", required = false) Integer year,
			@RequestParam(name = "month", required = false) Integer month, Model model) {

		CalendarDto calendarDto = adminService.generateCalendarData(year, month);
		model.addAttribute("calendarData", calendarDto);
		return "admin/calendar";
	}

	@GetMapping("/courseModify")
	public String courseModify(Model model) {
		LOGGER.info("Admin courseModify page");
		List<CourseDto> courses = adminService.getCourses();
		LOGGER.info(courses.toString() + " : courses 값 확인");
		model.addAttribute("courses", courses);
		return "admin/courseModify";
	}

	@GetMapping("/courseModifyDetail/{id}")
	public String courseModifyDetail(@PathVariable("id") Long id, Model model) {
		LOGGER.info("Admin courseModifyDetail page");
		CourseDetailDto courseDetail = adminService.getCourseDetailById(id);
		model.addAttribute("courseDetail", courseDetail);
    
    return "admin/courseModifyDetail";
}

	@PostMapping("/courseModifyDetail/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> updateCourseDetail(@PathVariable("id") Long id, @RequestBody CourseDetailDto courseDetailDto) {
		LOGGER.info("코스 상세 수정: " + id);
		courseDetailDto.setId(id);
		LOGGER.info("코스 상세 정보: " + courseDetailDto.toString());
		adminService.updateCourseDetail(courseDetailDto);
		
		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("message", "코스가 성공적으로 업데이트되었습니다.");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/login")
	public String login() {
		LOGGER.info("Admin login page");
		return "admin/login";
	}

	@GetMapping("/portfolio")
	public String portfolio(Model model) {
		LOGGER.info("Admin portfolio page");
		List<PortfolioDto> portfolios = adminService.getAllPortfolios();
    	model.addAttribute("portfolios", portfolios);
		return "admin/portfolio";
	}

	@PostMapping("/portfolio/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> updatePortfolio(@PathVariable("id") Long id, @ModelAttribute PortfolioDto portfolioDto) {
		LOGGER.info("포트폴리오 업데이트: {}", id);
		portfolioDto.setId(id);
		Map<String, Object> response = new HashMap<>();
		try {
			adminService.updatePortfolio(portfolioDto);
			
			response.put("success", true);
			response.put("message", "포트폴리오가 성공적으로 업데이트되었습니다.");
			return ResponseEntity.ok(response);
			
		} catch (Exception e) {
			LOGGER.error("포트폴리오 업데이트 실패: {}", e.getMessage());
			response.put("success", false);
			response.put("message", e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}
	

}