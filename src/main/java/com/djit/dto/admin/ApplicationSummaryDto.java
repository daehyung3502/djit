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
public class ApplicationSummaryDto {

	private Long number;
	private LocalDateTime createdAt;
	private String name;
	private String sex;
	private String birth;
	private String phoneNumber;
	private String subjectName;

}
