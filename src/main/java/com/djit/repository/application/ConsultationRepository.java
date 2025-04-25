package com.djit.repository.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.djit.entity.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    List<Consultation> findByConsultationDateTimeBetweenOrderByConsultationDateTimeAsc(LocalDateTime start, LocalDateTime end);

}
