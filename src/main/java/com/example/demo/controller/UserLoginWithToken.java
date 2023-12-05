package com.example.demo.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;

@Controller
public class UserLoginWithToken {
	@Autowired
	private UserService userService;

	 @Autowired
	 private TokenService tokenService;

	@GetMapping("/link")
	public String link(@RequestParam(value = "token", required = false) String token, Model model)
	{
		 if (token == null || !tokenService.isTokenValid(token)) {
		        model.addAttribute("errorMessage", "Vui lòng đăng nhập bằng cách nhấp vào liên kết trong email của bạn.");
		        return "error.html";
		    }
		return 	"link";
	}

    @PostMapping("/userLoginFirstTime")
    public String userLogin(HttpServletRequest request,@RequestParam("name")String  name)
    {
    	HttpSession session = request.getSession();
    	Object attribute = session.getAttribute("name");
    	
    	if (attribute instanceof List) 
    	{
    	    List<String> values = (List<String>) attribute;
    	    if(values.contains(name))
    	    {
    	    	return "redirect:/change-pass";
    	    }
    	} else if (attribute instanceof String) {
    	    String value = (String) attribute;
    	    if(value.equals(name))
    	    {
    	    	return "redirect:/change-pass";
    	    }
    	} else {
    			return "login";
    	}
    	System.out.println("error");
		return "error";	    
    }
    @GetMapping("/change-pass")
    public String userChangePass()
    {
    	return "user-change-pass";
    }
    @PostMapping("/changed-pass")
    public String userChangedPass( HttpServletRequest request,@RequestParam("pass") String pass)
    {
    	com.example.demo.model.User u = new com.example.demo.model.User();
    	HttpSession session = request.getSession();
    	String name = (String) session.getAttribute("name");
    	String fullName = (String) session.getAttribute("fullname");

		u.setUsername(name);
		u.setPassword(pass);
		u.setFullName(fullName);
		u.setAvatar("./uploads/image");
		userService.addUserAgent(u);
    	return "redirect:/index";
    }
}
