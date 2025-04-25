package com.djit.service.admin;

import com.djit.common.CalendarDay;
import com.djit.dto.admin.ApplicationResponseDto;
import com.djit.dto.admin.ApplicationSummaryDto;
import com.djit.dto.admin.CalendarDto;
import com.djit.dto.admin.ConsultationDto;
import com.djit.dto.admin.ConsultationResponseDto;
import com.djit.dto.client.ApplicationModifyDto;
import com.djit.entity.Application;
import com.djit.entity.Consultation;
import com.djit.repository.application.ApplicationRepository;
import com.djit.repository.application.ConsultationRepository;
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
	private final ModelMapper modelMapper;

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

			
			if (dayOfWeekVal == 6 || i == daysInMonth) {
				calendar.add(week);
				week = new ArrayList<>();
			}
		}

		
		if (!week.isEmpty()) {
			int count = 7 - week.size();
			for (int i = 1; i <= count; i++) {
				int dayOfWeekVal = (dayOfWeekValue + daysInMonth + i - 1) % 7;
				week.add(new CalendarDay(i, false, false, dayOfWeekVal));
			}
			calendar.add(week);
		}

		return calendar;
	}
}