package com.djit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	


	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http
		 	// .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/admin/login").permitAll()
	            .requestMatchers("/admin/**").hasRole("ADMIN")
	            .anyRequest().permitAll())
	        .formLogin(form -> form
	            .loginPage("/admin/login")
	            .loginProcessingUrl("/admin/login")
	            .defaultSuccessUrl("/admin/list", true)
				.failureUrl("/admin/login?error")
	            .permitAll())
	        .exceptionHandling(ex -> ex
	            .accessDeniedPage("/admin/login")  
	            .authenticationEntryPoint((request, response, authException) -> {
	                response.sendRedirect("/admin/login");  
	            }))
	        .logout(logout -> logout
				.logoutUrl("/admin/logout")
				.logoutSuccessUrl("/admin/login")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
	            .permitAll());
	    return http.build();
		
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
	}
	
	
}
