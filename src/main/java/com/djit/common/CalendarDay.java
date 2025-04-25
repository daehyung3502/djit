package com.djit.common;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarDay {
    private int day;
    private boolean currentMonth;
    private boolean today;
    private int dayOfWeek;  
}