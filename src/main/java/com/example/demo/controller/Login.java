package com.example.demo.controller;

import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Login {

    private String generatedLink;
    private LocalDateTime expirationTime;

    @GetMapping("/generateLink")
    public String generateLink() {
        // Check if the previously generated link has expired
        if (expirationTime != null && LocalDateTime.now().isAfter(expirationTime)) {
            generatedLink = null; // Invalidate the previous link
        }

        // Generate a new link if no link exists or the previous link has expired
        if (generatedLink == null) {
            generatedLink = generateNewLink();
            expirationTime = LocalDateTime.now().plusMinutes(1); // Set the expiration time to 1 minute from now
        }

        return generatedLink;
    }
    
    @GetMapping("/newlink")
    private String generateNewLink() {
        // Generate a new link
        String link = "http://localhost:8080/link1minnutes";

        return "redirect:/";
    }
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/login?logout"; 
    }
    
    
}
