package eu.openreq.service;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import eu.openreq.remote.dto.EmailData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Properties;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Autowired
    private JavaMailSender mailSender;

    @Autowired
    private IPService ipService;

    @Autowired
    private Environment environment;

    final static String exceptionMessage = "An exception occurred while opening the file.";

    private String generateHTML(String htmlMessage) {
        String hostName = ipService.getHost();
        long port = ipService.getPort();

		return "<!DOCTYPE html>\n" +
    		"<html>\n" + 
    		"<head>\n" + 
    		"<link href=\"https://fonts.googleapis.com/css?family=Roboto\" rel=\"stylesheet\" type=\"text/css\" />\n" +
    		"<link href=\"https://" + hostName + ":" + port + "/css/email.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
    		"</head>\n" +
    		"\n" + 
    		"<body>\n" + 
    		"	<div class=\"container\">\n" + 
    		"		<header>\n" + 
    		"		<img src=\"https://" + hostName + ":" + port + "/images/innosensr_logo.png\" width=\"180\" />\n" +
    		"		<hr />\n" + 
    		"	</header>\n" + 
    		"		<div class=\"content\">\n" + htmlMessage +
    		"			<p class=\"greetings\">Best regards,\n" + 
    		"your OpenReq!Live Team</p>\n" +
    		"		</div>\n" +
    		"		<footer>\n" +
    		"		<p>Powered by Applied Artificial Intelligence Group @ TU Graz</p>\n" +
    		"		<p>\n" + 
    		"			<a href=\"https://" + hostName + ":" + port + "/aboutus\">Imprint</a> <span> | </span> <a href=\"https://" + hostName + ":" + port + "/privacypolicy\">Privacy policy</a> <span> |\n" +
    		"			</span> <a href=\"mailto:innosensr@gmail.com?Subject=InnoSensr%20Feedback\" target=\"_top\">Feedback</a>\n" +
    		"		</p>\n" + 
    		"	</footer>\n" + 
    		"	</div>\n" + 
    		"</body>\n" + 
    		"</html>\n";
    }

	@Async
	public void sendEmailAsync(String toAddress, String subject, String htmlMessage, String textMessage) {
		try {
            String from = environment.getProperty("mail.from");
            sendEmailAsync(from, toAddress, subject, htmlMessage, textMessage);
        } catch (Exception e) {
			logger.error(exceptionMessage, e);
        }
	}

	@Async
	public void sendEmailAsync(String from, String toAddress, String subject, String htmlMessage, String textMessage) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
            String replyTo = environment.getProperty("mail.replyTo");
            String[] bcc = environment.getProperty("mail.bcc").split(",");

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setTo(toAddress);
            helper.setReplyTo(replyTo);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(textMessage);
            helper.setBcc(bcc);
            mimeMessage.setContent(generateHTML(htmlMessage), "text/html; charset=utf-8");
            mailSender.send(mimeMessage);
        } catch (Exception e) {
			logger.error(exceptionMessage, e);
        }
	}

	@Async
	public void sendMassEmailAsync(String from, List<EmailData> emails) {
        Transport transport = null;

		try {
            String replyTo = environment.getProperty("mail.replyTo");
            String[] bcc = environment.getProperty("mail.bcc").split(",");
            String host = environment.getProperty("massmail.host");
            String port = environment.getProperty("massmail.port");
            String userName = environment.getProperty("massmail.username");
            String password = environment.getProperty("massmail.password");

            Properties prop = System.getProperties(); // load all smtp properties
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", host);
            prop.put("mail.smtp.port", String.valueOf(port));

            Session session = Session.getDefaultInstance(prop, null);
            transport = session.getTransport("smtp");

            for (EmailData email : emails) {
                try {
                    MimeMessage mimeMessage = mailSender.createMimeMessage();
                    if (!transport.isConnected()) {
                        if (port != null && port.length() > 0) {
                            transport.connect(host, Integer.parseInt(port), userName, password);
                        } else {
                            transport.connect(host, userName, password);
                        }
                    }

                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
                    helper.setTo(email.getToAddress());
                    helper.setReplyTo(replyTo);
                    helper.setFrom(from);
                    helper.setSubject(email.getSubject());
                    helper.setText(email.getTextMessage());
                    helper.setBcc(bcc);
                    mimeMessage.setContent(generateHTML(email.getHtmlMessage()), "text/html; charset=utf-8");
                    mimeMessage.saveChanges();
                    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                } catch (Exception e) {
					logger.error(exceptionMessage, e);
                }
            }
        } catch (NoSuchProviderException e) {
			logger.error(exceptionMessage, e);
        } finally {
		    if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
					logger.error(exceptionMessage, e);
                }
            }
        }
	}

	public boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}
}
