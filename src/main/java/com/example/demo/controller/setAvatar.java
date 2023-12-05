package com.example.demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.User;
import com.example.demo.reposity.UserRepo;
import com.example.demo.service.UserService;

@Controller
public class setAvatar {
	
	   PasswordEncoder passwordEncoder;

	@Autowired
	ServletContext servletContext;
	@Autowired
	UserService userService;
	UserRepo userRepo;
	@GetMapping("/setAvatar")
	public String s(Principal principal,Model model)
	{
		String username = principal.getName();
	    User u = userService.findbyUsername(username);
	    User user = new User();
	    user.setFullName(u.getFullName());
	    user.setAvatar(u.getAvatar());
	    model.addAttribute("account", user);
	    
	    
		return "avatar";
	}

@PostMapping("/setAvatar")
public String signup(@RequestParam("avatar") MultipartFile avatar,@RequestParam("name") String name,Principal principal) {
    if (!avatar.isEmpty()) {
        try {
        	
        	String username = principal.getName();
    	    User u = userService.findbyUsername(username);
        	String nameImage = u.getId()+".jpg";
            byte[] imageData = avatar.getBytes();
            String filePath = "./src/main/resources/static/uploads/" +nameImage;
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(imageData);
            fos.close();
            u.setAvatar("./uploads/"+nameImage);
            u.setFullName(name);
            userService.addUser(u);
            
        	  
            System.out.println("===========");

        } catch (IOException e) {
            System.out.println(e);
        }
    } else {
        // ...
    }

    return "redirect:/setAvatar";
}

}
