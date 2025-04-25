package com.djit.dto.admin;

import java.util.List;
import com.djit.common.CalendarDay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CalendarDto {
    private final int currentYear;
    private final int selectedYear;
    private final int selectedMonth;
    private final int prevYear;
    private final int prevMonth;
    private final int nextYear;
    private final int nextMonth;
    private final List<List<CalendarDay>> calendar;
    private final List<ConsultationDto> consultations;
}