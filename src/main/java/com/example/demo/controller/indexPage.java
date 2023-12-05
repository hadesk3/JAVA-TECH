package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Controller
public class indexPage {
	@Autowired 
	UserService userService;
	@GetMapping("/index")
	public String getUser(Principal principal, Model model)
	{
	    String username = principal.getName();
	    User u = userService.findbyUsername(username);
	    model.addAttribute("username", u.getFullName());
	    return "index";
	}
}
