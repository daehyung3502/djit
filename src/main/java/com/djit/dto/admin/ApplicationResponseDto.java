package com.djit.dto.admin;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponseDto {

	private Long number;
	private String subjectName;
	private String name;
	private String email;
	private String sex;
	private String birth;
	private String phoneNumber;
	private String address;
	private String educationLevel;
	private String married;
	private String employmentInsurance;
	private String educationGoal;
	private String motivation;
	private String note;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private ConsultationResponseDto consultation;
	

}
