package com.djit.controller.client;


import java.util.HashMap;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.djit.dto.client.ApplicationDto;
import com.djit.service.client.ApplicationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/application")
public class ApplicationController {

	private final SimpMessagingTemplate messagingTemplate;
	private final Logger LOGGER = LoggerFactory.getLogger(ApplicationController.class);
	private final ApplicationService applicationService;
	
	
	@PostMapping()
	public ResponseEntity<Map<String, Object>> createApplication(@ModelAttribute ApplicationDto applicationDto) {
		LOGGER.info("ApplicationController initialized.");
	    applicationService.saveApplication(applicationDto);
	    
		Map<String, Object> notification = new HashMap<>();
		notification.put("type", "NEW_APPLICATION");
		notification.put("message", "새로운 지원서가 접수되었습니다.");
		notification.put("applicantName", applicationDto.getName());
		notification.put("timestamp", System.currentTimeMillis());
		
		messagingTemplate.convertAndSend("/topic/notifications", notification);

	    Map<String, Object> response = new HashMap<>();
	    response.put("message", "접수되었습니다.");
	    response.put("redirectUrl", "/pages/main");
	    return ResponseEntity.ok(response);
	}
}
