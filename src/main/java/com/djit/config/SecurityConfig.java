package com.djit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import static org.springframework.security.config.Customizer.withDefaults;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/admin/login").permitAll()
	            .requestMatchers("/admin/**").hasRole("ADMIN")
	            .anyRequest().permitAll())
	        .formLogin(form -> form
	            .loginPage("/admin/login")
	            .loginProcessingUrl("/admin/login")
	            .defaultSuccessUrl("/admin/list", true)
	            .permitAll())
	        .exceptionHandling(ex -> ex
	            .accessDeniedPage("/admin/login")  
	            .authenticationEntryPoint((request, response, authException) -> {
	                response.sendRedirect("/admin/login");  
	            }))
	        .logout(logout -> logout
	            .logoutUrl("/admin/logout")
	            .permitAll());
	    return http.build();
		
	}
	

	@Bean
	public UserDetailsService userDetailsService() {
		String encodedPassword = passwordEncoder().encode("12345");
		UserDetails user = User.withUsername("admin").password(encodedPassword) 
				.roles("ADMIN").build();
		
		return new InMemoryUserDetailsManager(user);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
	}
	
	
}
