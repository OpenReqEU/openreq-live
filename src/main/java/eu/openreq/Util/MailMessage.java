package eu.openreq.Util;

import java.util.Locale;

public class MailMessage {

	public static String getMailMessageText(/*Decision_PollDbo createdDecision, */Locale locale){
		/*
		ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", locale);		

		String message = bundle.getString("email.creation1") + "\"<b>" + createdDecision.getName() + "</b>\" " + bundle.getString("email.creation2");
		
		if(createdDecision.isClosedGroup()){
			message += bundle.getString("email.creation3");
			message += "(<b>" + createdDecision.getPersonalLinks().size() + "</b> " + bundle.getString("email.creation4");
		} else {
			message += bundle.getString("email.creation5");
			if(!createdDecision.isAllowPollSharing())
				message += bundle.getString("email.creation6");
			message += "<br><a href=\"" + createdDecision.getDecisionLink() + "\">" + createdDecision.getDecisionLink() + "</a>";
		}
		
		message += bundle.getString("email.creation7");
		if(!createdDecision.isAllowResultSharing())
			message += bundle.getString("email.creation6");
		message += "<br><a href=\"" + createdDecision.getDecisionLink().replace("openDecision","openSummary") + "\">" + createdDecision.getDecisionLink().replace("openDecision","openSummary") + "</a>";
		
		message += bundle.getString("email.creation8");
		message += "<br><a href=\"" + createdDecision.getAdminLink() + "\">" + createdDecision.getAdminLink() + "</a>";
		
		message += bundle.getString("email.creation9");
		return message;
		*/
		return null;
	}
	
	public static String getMailMessageTextForPersonalInvitation(/*Decision_PollDbo createdDecision, */String mailAddress, Locale locale){
		/*
		ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", locale);		
		
		String message = bundle.getString("email.invitation1") + "\"" + createdDecision.getName() + "\" " + bundle.getString("email.invitation2");
		
		message += bundle.getString("email.invitation3");
		
		message += "<br><a href=\"" + createdDecision.getDecisionLink()+ "&persID=" + createdDecision.getPersonalLinkOfMailAddress(mailAddress).getLink() + "\">" + createdDecision.getDecisionLink() + "&persID=" + createdDecision.getPersonalLinkOfMailAddress(mailAddress).getLink() + "</a>";
		
		message += bundle.getString("email.invitation4");
		return message;
		*/
		return null;
	}

}
