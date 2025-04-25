package com.djit.controller.admin;
import com.djit.dto.admin.ApplicationResponseDto;
import com.djit.dto.admin.ApplicationSummaryDto;
import com.djit.dto.admin.CalendarDto;
import com.djit.dto.admin.ConsultationDto;
import com.djit.dto.admin.ConsultationResponseDto;
import com.djit.dto.client.ApplicationModifyDto;
import com.djit.service.admin.AdminService;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

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
		return "/admin/list";
	}

	@GetMapping("/applicationDetail/{number}")
	public String applicationDetail(@PageableDefault(page = 1) Pageable pageable, @PathVariable Long number,
			Model model) {
		LOGGER.info(number + " : number 값 확인");
		ApplicationResponseDto applicationResponseDto = adminService.getApplication(number);
		model.addAttribute("applicationResponseDto", applicationResponseDto);
		model.addAttribute("pageable", pageable);
		return "/admin/applicationDetail";
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
	public ResponseEntity<Void> deleteConsultation(@PathVariable Long id) {
		LOGGER.info("예약 삭제");
		LOGGER.info("삭제할 정보" + id);
		adminService.deleteConsultation(id);
		
		return ResponseEntity.ok().build();
	}

	@GetMapping("/applicationModify/{id}")
	public String applicationModify(@PageableDefault(page = 1) Pageable pageable, @PathVariable Long id, Model model){
		LOGGER.info("Admin applicationModify page");
		LOGGER.info(id + " : number 값 확인");
		LOGGER.info(pageable.getPageNumber() + " : pageable 값 확인");
		ApplicationResponseDto applicationResponseDto = adminService.getApplication(id);
		model.addAttribute("applicationResponseDto", applicationResponseDto);
		model.addAttribute("pageable", pageable);
		return "/admin/applicationModify";

	}

	@PostMapping("/applicationModify/{id}")
	public ResponseEntity<Map<String, Long>> applicationModify(@PathVariable Long id, @RequestBody ApplicationModifyDto applicationModifyDto) {
		LOGGER.info("Admin applicationModify update page");
		LOGGER.info(id + " : number 값 확인");
		adminService.modifyApplication(applicationModifyDto, id);
		Map<String, Long> responseBody = Map.of("number", id);
		return ResponseEntity.ok(responseBody);
	}
	@DeleteMapping("/applicationModify/{id}")
	public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
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
		return "/admin/calendar";
	}

	@GetMapping("/courseModify")
	public String courseModify() {
		LOGGER.info("Admin courseModify page");
		return "/admin/courseModify";
	}

	@GetMapping("/login")
	public String login() {
		LOGGER.info("Admin login page");
		return "/admin/login";
	}

}