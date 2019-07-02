package org.abubusoft.foc.business.services.impl;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//[END simple_includes]

import org.abubusoft.foc.business.services.HtmlSanitizier;
import org.abubusoft.foc.business.services.SendMailService;
import org.abubusoft.foc.repositories.model.CloudFile;
import org.abubusoft.foc.repositories.model.Consumer;
import org.abubusoft.foc.repositories.model.Uploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendMailServiceImpl implements SendMailService {

	HtmlSanitizier sanitizer;

	@Value("${app.email.send}")
	protected boolean sendEmail;

	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	@Autowired
	public void setSanitizer(HtmlSanitizier sanitizer) {
		this.sanitizer = sanitizer;
	}

	@Override
	public void send(Uploader uploader, Consumer consumer, CloudFile file) {
		if (sendEmail) {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			try {
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("uxcesco@gmail.com", "Files-on-cloud Admin"));
				msg.addRecipient(Message.RecipientType.TO,
						new InternetAddress(consumer.getEmail(), consumer.getDisplayName()));
				msg.setSubject("There's a new file for you on cloud file!");
				
				String site="https://programmazione-web-238419.appspot.com/";
				//@formatter:off
				String message=
				"Hello,\n"+
				"Follow this link to download the file that "+uploader.getDisplayName()+" shares with you:\n"+
				"\n"+
				site+"api/v1/public/files/"+file.getUuid()+"\n"+
				"\n"+
				"Visit us on\n"+
				site+"\n"+
				"\n"+
				"Thanks,\n"+
				"Your Files-On-Cloud team\n";
				//@formatter:on
				msg.setText(sanitizer
						.sanitize(message));
				Transport.send(msg);
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void send() {
		// TODO Auto-generated method stub

	}
}
