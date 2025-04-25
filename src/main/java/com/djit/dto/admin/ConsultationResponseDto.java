package com.djit.dto.admin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationResponseDto {

	private Long number;
	private LocalDateTime consultationDateTime;
	private String consultationDateTimeStr;
	
	private static final DateTimeFormatter DATE_TIME_FORMATTER =
	          	DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	
	
	public void formatAndSetDateTimeString() {
        if (this.consultationDateTime != null) {
            this.consultationDateTimeStr = this.consultationDateTime.format(DATE_TIME_FORMATTER);
        } else {
            this.consultationDateTimeStr = null;
        }
    }

}
