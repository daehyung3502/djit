package com.djit.dto.admin;

import java.time.LocalDateTime;

import com.djit.entity.Application;
import com.djit.entity.Consultation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationDto {

	private Long number;
	private String name;
	private LocalDateTime consultationDateTime;
    private Long applicationNumber;
	
	public static ConsultationDto fromEntity(Consultation entity) {
        String name = "신청자 정보 없음";
        Long number = null; 
        Application application = entity.getApplication(); 
        if (application != null) {  
             name = application.getName();
             number = application.getNumber();    
        }
        return ConsultationDto.builder()
            .number(entity.getNumber())
            .consultationDateTime(entity.getConsultationDateTime())
            .name(name)
            .applicationNumber(number)
            .build();
    }
}


