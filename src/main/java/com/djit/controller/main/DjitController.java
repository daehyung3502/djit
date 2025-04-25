package com.djit.controller.main;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;


@Controller

public class DjitController {
	
	
	  @GetMapping("/pages/*")
	    public String main(HttpServletRequest request) {
	        return null;
	    }
	  
	 
	  
	    
	   
	    
	   

}
