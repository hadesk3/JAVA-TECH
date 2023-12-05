package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;

@Controller
public class adminPage {
	@Autowired 
	UserService userService;
	
	@Autowired
	ProductService productService;
	@GetMapping("/admin-page")
	public String page(Model model)
	{
		List<User> l = userService.findAll();
		model.addAttribute("accounts", l);
		
		List<Product> l2 = productService.getAllProducts();
		model.addAttribute("products", l2);
		return "admin-page";
	}
	
	@GetMapping("/detail-user/{id}")
	public String editUser(@PathVariable("id") Long id, Model model) {
	    User user = userService.findById(id).orElse(null);
	    
	    if (user == null) {
	        return "error";
	    }
	    
	    model.addAttribute("user", user);
	    
	    return "edit-user-form";
	}
	
	@PostMapping("/handleUser")
	public String postPageHandleUser(@RequestParam("name") String name)
	{
		userService.activeUser(name);
		return "redirect:/admin-page";
	}
}
