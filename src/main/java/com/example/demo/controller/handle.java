package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class handle {
	@GetMapping("/403")
	public String handle403() {
		return "error";
	}
	
}
