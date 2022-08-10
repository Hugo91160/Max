package model;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Settings {
	private String emailAddress;
	private float amplitudeThreshold;
	private boolean notifyByEmail;
	private String emailKey;

	public Settings(String emailAddress, float amplitudeThreshold, boolean notifyByEmail, String emailKey) {
		super();
		this.emailAddress = emailAddress;
		this.amplitudeThreshold = amplitudeThreshold;
		this.notifyByEmail = notifyByEmail;
		this.emailKey = emailKey;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public float getAmplitudeThreshold() {
		return amplitudeThreshold;
	}

	public void setAmplitudeThreshold(float amplitudeThreshold) {
		this.amplitudeThreshold = amplitudeThreshold;
	}

	public boolean isNotifyByEmail() {
		return notifyByEmail;
	}

	public void setNotifyByEmail(boolean notifyByEmail) {
		this.notifyByEmail = notifyByEmail;
	}

	public String getEmailKey() {
		return emailKey;
	}

	public void setEmailKey(String emailKey) {
		this.emailKey = emailKey;
	}

	/*
	 * Checking settings validity
	 * 
	 */

	public void validate() throws AddressException {
		if (this.isNotifyByEmail()) {
			InternetAddress emailAd = new InternetAddress(this.emailAddress);
			emailAd.validate();
		}
	}

}
