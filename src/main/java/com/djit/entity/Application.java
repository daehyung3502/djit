package com.djit.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;
	
	@Column(name = "subject_name", nullable = false)
    private String subjectName;
	
	@Column(nullable = false, length = 10)
    private String name;
	
	@Column(nullable = false)
    private String email;
	
	@Column(nullable = false, length = 2)
    private String sex;
	
	@Column(nullable = false, length = 20)
    private String birth;
	
	@Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;
	
	@Column(nullable = false)
    private String address;
	
	@Column(name = "education_level", nullable = false, length = 20)
    private String educationLevel;
	
	@Column(nullable = false, length = 2)
    private String married;
	
	@Column(name = "employment_insurance", nullable = false, length = 2)
    private String employmentInsurance;
	
	@Column(name = "education_goal", nullable = false, length = 8)
    private String educationGoal;
	
	@Lob
	@Column
    private String motivation;
	
	@Lob
	@Column
	private String note;
	
	@Column(name = "created_at")
    private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
    private LocalDateTime updatedAt;
	
	@OneToOne(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Consultation consultation;
	
	public void deleteConsultation() {
		this.consultation = null;
	}


	
	
	
	

}
