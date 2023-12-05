package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.model.Mail;
import com.example.demo.reposity.MailRepo;

@Service
public class MailService implements MailRepo {
	@Autowired
	private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;

	@Override
	public String sendSimpleMail(Mail details) {
		 try {
			 
	            // Creating a simple mail message
	            SimpleMailMessage mailMessage
	                = new SimpleMailMessage();
	 
	            // Setting up necessary details
	            mailMessage.setFrom("521h0447@student.tdtu.edu.vn");
	            mailMessage.setTo(details.getRecipient());
	            mailMessage.setText(details.getMsgBody());
	            mailMessage.setSubject(details.getSubject());
	            // Sending the mail
	            javaMailSender.send(mailMessage);
	            return "Mail Sent Successfully...";
	        }
	 
	        // Catch block to handle the exceptions
	        catch (Exception e) {
	        	System.out.println(e);
	            return "Error while Sending Mail";
	        }// TODO Auto-generated method stub
		
	}

	@Override
	public String sendMailWithAttachment(Mail details) {
		// TODO Auto-generated method stub
		return null;
	}

}
