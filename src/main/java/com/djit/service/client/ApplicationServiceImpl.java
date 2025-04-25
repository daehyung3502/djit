package com.djit.service.client;

import com.djit.dto.client.ApplicationDto;
import com.djit.entity.Application;
import com.djit.repository.application.ApplicationRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
	
	
	private final ModelMapper modelMapper;
	private final ApplicationRepository applicationRepository;

	@Override
	public void saveApplication(ApplicationDto applicationDto) {
		Application application = modelMapper.map(applicationDto, Application.class);
		application.setCreatedAt(LocalDateTime.now());
		application.setUpdatedAt(LocalDateTime.now());
		applicationRepository.save(application);

		
	}

}
