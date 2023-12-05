package com.example.demo.controller;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Mail;
import com.example.demo.service.MailService;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;

@Controller
public class register {
	 @Autowired
	    private MailService emailService;
	 @Autowired
	 private UserService userSer;
	 @Autowired
	 private TokenService tokenService;

	@GetMapping("/register") 
	public String registerPage()
	{
		return "register";
	}
	
	@PostMapping("/sendMail")
	    public String send(HttpSession session2,HttpServletRequest request,@RequestParam("email")String  Email, @RequestParam("name") String name, @RequestParam(value = "token", required = false) String token )
	    {	
		
	    	String[] username = Email.split("@");
	    	Mail m = new Mail();
	    	m.setRecipient(Email);
	    	String body = "Your usename and password to login is " + username[0];
	    	String link = "\nLink to login and change password is: http://localhost:8080/link?token=";
	        String tokenCreate = tokenService.generateToken();
	        
			m.setMsgBody(body + link + tokenCreate)  ;
		    String status = emailService.sendSimpleMail(m);

	       HttpSession session =  request.getSession();
	       session2.setAttribute("fullname",name);
	       System.out.println(name);
	       Object attribute = session.getAttribute("name");
	       if (attribute instanceof List) {
	           List<String> values = (List<String>) attribute;
	           {
	               	if(values.contains(username[0]))
	               	{
	               		System.out.println("not in ");
	   	        	 return   "redirect:/register";

	               	}
	           }
	       } else if (attribute instanceof String) {
	           String value = (String) attribute;
	           if(value.equals(username[0]))
	           {
	        	   
	        	   return   "redirect:/register";
	           }
	           System.out.println("Giá trị trong 'name': " + value);
	       } else {
        	   session.setAttribute("name", username[0]);
	           System.out.println(" set giá trị trong 'name': " + username[0]);


	       }
	     
	       

	       
	       return "redirect:/register";
	    }
	@GetMapping("/")

	public String home()
	{
		return "login";
	}
	
	
	
	@PostMapping("/create-new-admin")
	public String log(@RequestParam("name")String  name,@RequestParam("pass")String  pass)
	{
		com.example.demo.model.User u = new com.example.demo.model.User();
		u.setUsername(name);
		u.setPassword(pass);
		userSer.addUser(u);
		return "redirect:/register";
	}
	
	
	}
