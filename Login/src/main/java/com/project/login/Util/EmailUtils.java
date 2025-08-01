package com.project.login.Util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmailUtils {
	
	@Autowired
	private JavaMailSender mailSender;
	
	

	public boolean sendMail(String to, String subject, String body) {
		
		boolean isMailSent = false;
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			mailSender.send(mimeMessage); 
			isMailSent =true;
		}catch (Exception e) {
			log.error("Exception occured",e);
		}
		return isMailSent;
		
	}
}
