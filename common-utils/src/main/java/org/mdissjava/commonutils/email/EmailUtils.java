package org.mdissjava.commonutils.email;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

/**
 * Utility method to send emails, simple, in html, with attachment and with
 * templates
 */
public class EmailUtils {

	/** The constant which represents a simple email with only text */
	public static final int TEXT = 1;

	/** The constant which represents an HTML email */
	public static final int HTML = 2;

	/**
	 * Send simple email using only simple text.
	 * 
	 * @param to
	 *            the email of the receiver
	 * @param subject
	 *            the subject of the email
	 * @param msg
	 *            the text which will be sended
	 * @throws EmailException
	 *             the email exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void sendSimpleEmail(String to, String subject, String msg)
			throws EmailException, IOException {
		// Getting the properties utility
		PropertiesFacade propertiesFacade = new PropertiesFacade();
		Properties properties = propertiesFacade.getProperties("globals");

		// Email construction with properties file information
		Email email = new SimpleEmail();
		email.setHostName(properties.getProperty("email.hostname"));
		email.setSmtpPort(Integer.parseInt(properties
				.getProperty("email.smtpport")));
		email.setAuthenticator(new DefaultAuthenticator(properties
				.getProperty("email.user"), properties
				.getProperty("email.password")));
		email.setTLS(true);
		email.setFrom(properties.getProperty("email.from"),
				properties.getProperty("email.from.name"));
		email.setSubject(subject);
		email.setMsg(msg);
		email.addTo(to);
		email.send();
	}

	/**
	 * The principle method to send an email.
	 * 
	 * @param to
	 *            the email of the receiver
	 * @param subject
	 *            the subject of the email
	 * @param msg
	 *            the text, or html depending the email type
	 * @param type
	 *            the type of the email TEXT or HTML using this class constants
	 * @throws EmailException
	 *             the email exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void sendEmail(String to, String subject, String msg, int type)
			throws EmailException, IOException {
		switch (type) {
		case EmailUtils.TEXT:
			EmailUtils.sendSimpleEmail(to, subject, msg);
			break;
		case EmailUtils.HTML:
			EmailUtils.sendHTMLEmail(to, subject, msg);
			break;
		}
	}

	/**
	 * Sends email with attachments.
	 * 
	 * @param to
	 *            the receivers email address
	 * @param subject
	 *            the subject of the email
	 * @param msg
	 *            the text
	 * @param attachments
	 *            the array of files' direction Ex:
	 *            "/resources/images/Mdiss.png"
	 * @throws EmailException
	 *             the email exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void sendEmail(String to, String subject, String msg,
			String... attachments) throws EmailException,
			IllegalArgumentException, IOException {
		PropertiesFacade propertiesFacade = new PropertiesFacade();
		Properties properties = propertiesFacade.getProperties("globals");
		List<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
		for (String s : attachments) {
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(s);
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachmentList.add(attachment);
		}

		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(properties.getProperty("email.hostname"));
		email.setSmtpPort(Integer.parseInt(properties
				.getProperty("email.smtpport")));
		email.setAuthenticator(new DefaultAuthenticator(properties
				.getProperty("email.user"), properties
				.getProperty("email.password")));
		email.setTLS(true);
		email.setFrom(properties.getProperty("email.from"));
		email.addTo(to);
		email.setSubject(subject);
		email.setMsg(msg);

		for (EmailAttachment ea : attachmentList) {
			email.attach(ea);
		}
		email.send();
	}

	/**
	 * Send html email.
	 * 
	 * @param to
	 *            the to
	 * @param subject
	 *            the subject
	 * @param html
	 *            the html
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws EmailException
	 *             the email exception
	 */
	private static void sendHTMLEmail(String to, String subject, String html)
			throws IllegalArgumentException, IOException, EmailException {
		// Create the email message
		PropertiesFacade propertiesFacade = new PropertiesFacade();
		Properties properties = propertiesFacade.getProperties("globals");
		HtmlEmail email = new HtmlEmail();
		email.setHostName(properties.getProperty("email.hostname"));
		email.setSmtpPort(Integer.parseInt(properties
				.getProperty("email.smtpport")));
		email.setAuthenticator(new DefaultAuthenticator(properties
				.getProperty("email.user"), properties
				.getProperty("email.password")));
		email.setSSL(true);
		email.setTLS(true);
		email.setFrom(properties.getProperty("email.from"));
		email.addTo(to);
		email.setSubject(subject);

		email.setHtmlMsg(html);

		// set the alternative message
		email.setTextMsg("Your email client does not support HTML messages");

		// send the email
		email.send();
	}

	/**
	 * Send welcome email using a template.
	 * 
	 * @param to
	 *            the receivers email address
	 * @param name
	 *            the name of the receiver which will appear in the email
	 * @throws EmailException
	 *             the email exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void sendWelcomeEmail(String to, String name)
			throws EmailException, IOException {
		// TODO: Add all the text to properties files - i18n
		STGroup group = new STGroupDir("src/main/resources/templates", '$', '$');
		ST st = group.getInstanceOf("welcome");

		//st.add("title", "Welcome To MDISS PHOTO Java ");
		st.add("name", name);
		EmailUtils.sendEmail(to, "Welcome to MDISS", st.render(),
				EmailUtils.HTML);
	}
	
	public static void sendValidationEmail(String to, String name, String link)
			throws EmailException, IOException {
		// TODO: Add all the text to properties files - i18n
		STGroup group = new STGroupDir("src/main/resources/templates", '$', '$');
		ST st = group.getInstanceOf("welcome");

		st.add("name", name);
		st.add("link", link);
		EmailUtils.sendEmail(to, "Welcome to MDISS", st.render(),
				EmailUtils.HTML);
	}
	
	public static void sendEmail(String to, String template, Map<String, String> data) throws EmailException, IOException {
		STGroup group = new STGroupDir("src/main/resources/templates", '$', '$');
		ST st = group.getInstanceOf(template);
		for(String value:data.keySet()) {
			st.add(value, data.get(value));
		}
		EmailUtils.sendEmail(to, "Welcome to MDISS", st.render(),
				EmailUtils.HTML);
	}
}
