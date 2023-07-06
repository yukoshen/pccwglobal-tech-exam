package com.kjslocal.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.kjslocal.exceptions.UserException;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender sender;

	private final String MAIL_ERR = "Error during mail send";

	public void sendWelcomeEmail(String email) throws UserException {

		try {

			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email);
			message.setText("Successfully registered");
			message.setSubject("Welcome Email");
			sender.send(message);

		} catch (MailException ex) {
			throw new UserException(MAIL_ERR);
		}

	}

}
