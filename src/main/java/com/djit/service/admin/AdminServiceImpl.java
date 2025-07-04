package com.djit.service.admin;

import com.djit.common.CalendarDay;
import com.djit.dto.admin.ApplicationResponseDto;
import com.djit.dto.admin.ApplicationSummaryDto;
import com.djit.dto.admin.CalendarDto;
import com.djit.dto.admin.ConsultationDto;
import com.djit.dto.admin.ConsultationResponseDto;
import com.djit.dto.admin.CourseDetailDto;
import com.djit.dto.admin.CourseDto;
import com.djit.dto.admin.PortfolioDto;
import com.djit.dto.client.ApplicationModifyDto;
import com.djit.entity.Application;
import com.djit.entity.Consultation;
import com.djit.entity.Course;
import com.djit.entity.Portfolio;
import com.djit.repository.application.ApplicationRepository;
import com.djit.repository.application.ConsultationRepository;
import com.djit.repository.application.CourseRepository;
import com.djit.repository.application.PortfolioRepository;
import com.djit.service.FileUploadService;

import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	private final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);
	private final ApplicationRepository applicationRepository;
	private final ConsultationRepository counsultationRepository;
	private final CourseRepository courseRepository;
	private final PortfolioRepository portfolioRepository;
	private final ModelMapper modelMapper;
	private final FileUploadService fileUploadService; 

	@Override
	@Transactional(readOnly = true)
	public Page<ApplicationSummaryDto> paging(Pageable pageable) {
		int page = pageable.getPageNumber() - 1;
		int pageLimit = 10;
		Page<Application> applications = applicationRepository
				.findAll(PageRequest.of(page, pageLimit, Sort.by(Direction.DESC, "createdAt")));
		Page<ApplicationSummaryDto> applicationSummaryDto = applications
				.map(application -> modelMapper.map(application, ApplicationSummaryDto.class));
		return applicationSummaryDto;
	}

	@Override
	@Transactional(readOnly = true)
	public ApplicationResponseDto getApplication(Long number) {
		Application application = applicationRepository.findById(number)
				.orElseThrow(() -> new RuntimeException("ID " + number + "에 해당하는 Application을 찾을 수 없습니다."));
		ApplicationResponseDto applicationResponseDto = modelMapper.map(application, ApplicationResponseDto.class);
		if (applicationResponseDto.getConsultation() != null) {
			applicationResponseDto.getConsultation().formatAndSetDateTimeString();
		}
		return applicationResponseDto;
	}

	@Override
	@Transactional
	public void modifyApplication(ApplicationModifyDto applicationModifyDto, Long id) {
		Application application = applicationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("ID " + id + "에 해당하는 Application을 찾을 수 없습니다."));
		modelMapper.map(applicationModifyDto, application);
		application.setUpdatedAt(LocalDateTime.now());
	}

	@Override
	@Transactional
	public void deleteApplication(Long number) {
		LOGGER.info(number + " : 상담 삭제 서비스 호출");
		Application application = applicationRepository.findById(number)
				.orElseThrow(() -> new RuntimeException("ID " + number + "에 해당하는 Application을 찾을 수 없습니다."));
		applicationRepository.delete(application);
	}

	@Override
	@Transactional
	public ConsultationResponseDto consultationApplication(ConsultationDto consultationDto) {
		LOGGER.info("상담 예약 서비스 호출");
		Application application = applicationRepository.findById(consultationDto.getNumber())
				.orElseThrow(() -> new RuntimeException());
		this.LOGGER.info(application.getName() + " : application name 값 확인");
		Consultation consultation = application.getConsultation();
		if (consultation == null) {
			consultation = Consultation.createConsultation(application, consultationDto.getConsultationDateTime());
			consultation = counsultationRepository.save(consultation);
		} else {
			consultation.updateConsultation(consultationDto.getConsultationDateTime());
		}
		ConsultationResponseDto responseDto = modelMapper.map(consultation, ConsultationResponseDto.class);
		responseDto.formatAndSetDateTimeString();
		return responseDto;

	}

	
	
	
	@Override
	@Transactional
	public void deleteConsultation(Long id) {
		LOGGER.info(id + " : 상담 예약 삭제 서비스 호출");
		Consultation consultation = counsultationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("ID " + id + "에 해당하는 Consultation을 찾을 수 없습니다."));
		Application application = consultation.getApplication();	
		counsultationRepository.delete(consultation);
		application.deleteConsultation();
	}


	@Override
	@Transactional(readOnly = true)
	public List<CourseDto> getCourses() {
		LOGGER.info("코스 조회 서비스 호출");
		List<CourseDto> courses = courseRepository.findAllWithSubjects().stream()
				.map(course -> modelMapper.map(course, CourseDto.class)).collect(Collectors.toList());
		return courses;
	}

	@Override
	public CalendarDto generateCalendarData(Integer year, Integer month) {
	    LocalDate today = LocalDate.now();
	    if (year == null)
	        year = today.getYear();
	    if (month == null)
	        month = today.getMonthValue();

	    YearMonth yearMonth = YearMonth.of(year, month);
	    YearMonth prevMonth = yearMonth.minusMonths(1);
	    YearMonth nextMonth = yearMonth.plusMonths(1);

		LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
    	LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);
		 List<ConsultationDto> consultations = counsultationRepository.findByConsultationDateTimeBetweenOrderByConsultationDateTimeAsc(
            startOfMonth, endOfMonth).stream()
			.filter(consultation -> consultation.getConsultationDateTime() != null)
            .map(ConsultationDto::fromEntity)
            .collect(Collectors.toList());


	    List<List<CalendarDay>> calendar = generateCalendar(yearMonth, today);
	    return CalendarDto.builder()
	        .currentYear(today.getYear())
	        .selectedYear(year)
	        .selectedMonth(month)
	        .prevYear(prevMonth.getYear())
	        .prevMonth(prevMonth.getMonthValue())
	        .nextYear(nextMonth.getYear())
	        .nextMonth(nextMonth.getMonthValue())
	        .calendar(calendar)
			.consultations(consultations)
	        .build();
	}

	@Override
	@Transactional(readOnly = true)
	public CourseDetailDto getCourseDetailById(Long id) {
		LOGGER.info("코스 상세 조회 서비스 호출: " + id);
		Course course = courseRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("ID " + id + "에 해당하는 Course를 찾을 수 없습니다."));
		
		CourseDetailDto courseDetailDto = modelMapper.map(course, CourseDetailDto.class);
		return courseDetailDto;
	}

	@Override
	@Transactional
	public void updateCourseDetail(CourseDetailDto courseDetailDto) {
		LOGGER.info("코스 상세 수정 서비스 호출: " + courseDetailDto.getId());
		Course course = courseRepository.findById(courseDetailDto.getId())
			.orElseThrow(() -> new RuntimeException("ID " + courseDetailDto.getId() + "에 해당하는 Course를 찾을 수 없습니다."));
		
		modelMapper.map(courseDetailDto, course);
		courseRepository.save(course);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PortfolioDto> getAllPortfolios() {
		List<Portfolio> portfolios = portfolioRepository.findAllByOrderByIdAsc();
		return portfolios.stream()
			.map(portfolio -> modelMapper.map(portfolio, PortfolioDto.class))
			.toList();
	}

	@Override
	@Transactional
	public void updatePortfolio(PortfolioDto portfolioDto) {
		LOGGER.info("포트폴리오 업데이트: {}", portfolioDto.getId());
		Portfolio portfolio = portfolioRepository.findById(portfolioDto.getId())
			.orElseThrow(() -> new RuntimeException("포트폴리오를 찾을 수 없습니다: " + portfolioDto.getId()));

		String oldImageUrl = portfolio.getImageUrl();

		if (portfolioDto.getImageFile() != null && !portfolioDto.getImageFile().isEmpty()) {
			try {
				String newImageUrl = fileUploadService.uploadImage(portfolioDto.getImageFile(), "portfolio");
				portfolio.setImageUrl(newImageUrl);
				if (oldImageUrl != null) {
					fileUploadService.deleteFile(oldImageUrl);
					LOGGER.info("기존 이미지 삭제: {}", oldImageUrl);
				}
				
				LOGGER.info("새 이미지 업로드 완료: {}", newImageUrl);
			} catch (Exception e) {
				LOGGER.error("이미지 처리 실패: {}", e.getMessage());
				throw new RuntimeException("이미지 처리 중 오류가 발생했습니다: " + e.getMessage());
			}
		}
			
		
		portfolio.setTitle(portfolioDto.getTitle());
		portfolio.setGithubUrl(portfolioDto.getGithubUrl());
		
		portfolio.setUpdatedAt(LocalDateTime.now());
		
		portfolioRepository.save(portfolio);
	}






	private List<List<CalendarDay>> generateCalendar(YearMonth yearMonth, LocalDate today) {
		List<List<CalendarDay>> calendar = new ArrayList<>();

		
		LocalDate firstDay = yearMonth.atDay(1);

	
		DayOfWeek dayOfWeek = firstDay.getDayOfWeek();
		int value = dayOfWeek.getValue();

	
		int dayOfWeekValue = value % 7;

		
		List<CalendarDay> week = new ArrayList<>();
		YearMonth prevMonth = yearMonth.minusMonths(1);
		int prevMonthLength = prevMonth.lengthOfMonth();

	
		for (int i = 0; i < dayOfWeekValue; i++) {
			int day = prevMonthLength - dayOfWeekValue + i + 1;
			week.add(new CalendarDay(day, false, false, i));
		}

	
		int daysInMonth = yearMonth.lengthOfMonth();
		for (int i = 1; i <= daysInMonth; i++) {
			LocalDate date = yearMonth.atDay(i);
			boolean isToday = date.equals(today);
			int dayOfWeekVal = date.getDayOfWeek().getValue() % 7;

			week.add(new CalendarDay(i, true, isToday, dayOfWeekVal));

			
			if (dayOfWeekVal == 6) {
				calendar.add(week);
				week = new ArrayList<>();
			}
		}

		
		if (!week.isEmpty()) {
			int count = 7 - week.size();
			for (int i = 1; i <= count; i++) {
				int dayOfWeekVal = (week.size() + i - 1) % 7;
				week.add(new CalendarDay(i, false, false, dayOfWeekVal));
			}
			calendar.add(week);
		}

		return calendar;
	}
}