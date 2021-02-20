package sendEmail;
// File Name SendEmail.java

import com.email.durgesh.Email;

/**
 * The class SendEmail is using a thread to send an email to a recipient
 * 
 * @author Eylon
 *
 */
public class SendEmail implements Runnable {
	private String recipient;
	private String subject;
	private String content;

	/**
	 * SendEmail constructor with all fields as parameters
	 * 
	 * @param recipient The recipient email
	 * @param subject   The subject of the email
	 * @param content   The content of the email
	 */
	public SendEmail(String recipient, String subject, String content) {
		this.recipient = recipient;
		this.subject = subject;
		this.content = content;
	}

	/**
	 * The thread that sends the email
	 */
	@Override
	public void run() {
		try {

			Email email = new Email("myfuelinc@gmail.com", "myfuelpassword");
			email.setFrom("myfuelinc@gmail.com", "MyFuel");
			email.setSubject(subject);
			email.setContent(content, "text/plain;charset=UTF-8");
			email.addRecipient(recipient);
			email.send();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
