package com.example.demo.reposity;

import com.example.demo.model.Mail;

public interface MailRepo {
	 String sendSimpleMail(Mail details);
	 String sendMailWithAttachment(Mail details);
}
