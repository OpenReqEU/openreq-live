package eu.openreq.service;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import eu.openreq.statistics.OptimalReleasePlanCalculator;
import lombok.AllArgsConstructor;
import lombok.Data;
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

    // TODO: create a Thymeleaf template instead!!
    private String generateHTML(String htmlMessage) {
        String hostName = ipService.getHost();
        long port = ipService.getPort();

		return "<!DOCTYPE html>\n" +
    		"<html>\n" + 
    		"<head>\n" + 
    		"<link href=\"https://fonts.googleapis.com/css?family=Roboto\" rel=\"stylesheet\" type=\"text/css\" />\n" +
    		"<style>\n" + 
    		"body {\n" + 
    		"	font-family: 'Roboto', sans-serif;\n" + 
    		"	font-size: 14px;\n" + 
    		"	background-color: #eee;\n" + 
    		"}\n" + 
    		"\n" + 
    		"hr {\n" + 
    		"	border: 0;\n" + 
    		"	border-top: 1px solid #eee;\n" + 
    		"}\n" + 
    		"\n" + 
    		".container {\n" + 
    		"	width: 40%;\n" + 
    		"	background-color: #fff;\n" + 
    		"	color: #666;\n" + 
    		"	margin-left: auto;\n" + 
    		"	margin-right: auto;\n" + 
    		"}\n" + 
    		"\n" + 
    		"@media ( max-width : 991px) {\n" + 
    		"	.container {\n" + 
    		"		width: 100%;\n" + 
    		"	}\n" + 
    		"}\n" + 
    		"\n" + 
    		"header {\n" + 
    		"	background-color: #fff !important;\n" + 
    		"	text-align: center;\n" + 
    		"	padding: 15px;\n" + 
    		"}\n" + 
    		"\n" + 
    		"header img {\n" + 
    		"	width: 180px;\n" + 
    		"	height: auto;\n" + 
    		"}\n" + 
    		"\n" + 
    		"header hr {\n" + 
    		"	margin-bottom: 0 !important;\n" + 
    		"}\n" + 
    		"\n" + 
    		"footer {\n" + 
    		"	background-color: #f5f5f5;\n" + 
    		"	color: #9da8b7;\n" + 
    		"	text-align: center;\n" + 
    		"	padding: 15px;\n" + 
    		"	margin-bottom: 20px;\n" + 
    		"}\n" + 
    		"\n" + 
    		"footer a {\n" + 
    		"	color: #337ab7 !important;\n" + 
    		"}\n" + 
    		"\n" + 
    		"footer a:active, footer a:visited, footer a:focus {\n" + 
    		"	color: #337ab7 !important;\n" + 
    		"}\n" + 
    		"\n" + 
    		"footer a:hover {\n" + 
    		"	color: #2f97f3 !important;\n" + 
    		"}\n" + 
    		"\n" + 
    		".content {\n" + 
    		"	padding: 15px;\n" + 
    		"}\n" + 
    		"\n" + 
    		".btn {\n" + 
    		"	padding: 6px 12px;\n" + 
    		"	text-align: center;\n" + 
    		"	cursor: pointer;\n" + 
    		"	display: inline-block;\n" + 
    		"	background-color: #039be5;\n" + 
    		"	color: #fff;\n" + 
    		"	border-radius: 2px;\n" + 
    		"	text-decoration: none !important;\n" + 
    		"	margin-top: 10px;\n" + 
    		"}\n" + 
    		"\n" + 
    		".btn:hover{\n" + 
    		"	background-color: #406392; \n" + 
    		"}\n" + 
    		"\n" + 
    		"p.greetings {\n" + 
    		"	margin-top: 20px; \n" + 
    		"	white-space: pre-wrap;\n" + 
    		"}\n" + 
    		"\n" + 
    		"</style>\n" + 
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
			logger.error("An exception occurred while opening the file.", e);
        } finally {}
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
			logger.error("An exception occurred while opening the file.", e);
        } finally {}
	}

	@Data
    @AllArgsConstructor
	public static class EmailData {
        private String toAddress;
        private String subject;
        private String htmlMessage;
        private String textMessage;
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
					logger.error("An exception occurred while opening the file.", e);
                } finally {}
            }
        } catch (NoSuchProviderException e) {
			logger.error("An exception occurred while opening the file.", e);
        } finally {
		    if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
					logger.error("An exception occurred while opening the file.", e);
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
