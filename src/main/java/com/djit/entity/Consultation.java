package com.djit.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consultation {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;
	
	@Column(nullable = false)
	private LocalDateTime consultationDateTime;
	
    private LocalDateTime createdAt;
	
    private LocalDateTime updatedAt;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "application_number")
	private Application application;
	
	public static Consultation createConsultation(Application application, LocalDateTime consultationDateTime) {
		Consultation consultation = new Consultation();
		consultation.setApplication(application);
		consultation.setConsultationDateTime(consultationDateTime);
		consultation.setCreatedAt(LocalDateTime.now());
		consultation.setUpdatedAt(LocalDateTime.now());
		return consultation;
	}
	
	public void updateConsultation(LocalDateTime consultationDateTime) {
		this.setConsultationDateTime(consultationDateTime);
	}

}
