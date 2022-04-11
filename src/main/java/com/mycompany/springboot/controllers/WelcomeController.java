package com.mycompany.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.springboot.service.WelcomeService;

@RestController
public class WelcomeController {
	
	@Autowired
	private WelcomeService service = new WelcomeService();
	
	@RequestMapping("/welcome")
	public String welcome() {
		return service.retrieveWelcomeMessage();
	}

}
