package com.djit.service.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.djit.dto.admin.ApplicationResponseDto;
import com.djit.dto.admin.ApplicationSummaryDto;
import com.djit.dto.admin.CalendarDto;
import com.djit.dto.admin.ConsultationDto;
import com.djit.dto.admin.ConsultationResponseDto;
import com.djit.dto.admin.CourseDto;
import com.djit.dto.client.ApplicationModifyDto;


public interface AdminService {
	Page<ApplicationSummaryDto> paging(Pageable pageable);

	ApplicationResponseDto getApplication(Long number);
	
	ConsultationResponseDto consultationApplication(ConsultationDto consultationDto);
	
	CalendarDto generateCalendarData(Integer year, Integer month);

	void modifyApplication(ApplicationModifyDto applicationModifyDto, Long number);
	
	void deleteConsultation(Long id);

	void deleteApplication(Long number);

	List<CourseDto> getCourses();
	
	
}