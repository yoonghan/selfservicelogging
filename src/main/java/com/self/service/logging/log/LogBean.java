package com.self.service.logging.log;

import java.util.Properties;

import static com.self.service.logging.impl.PropertyFiles.*;

import com.self.service.util.impl.PropertyMapperImpl;

class LogBean implements PropertyMapperImpl {
	
	private boolean infoEnabled = true;
	private boolean warningEnabled = true;
	private boolean errorEnabled = true;
	private boolean emailSendEnabled = true;
	
	@Override
	public void map(Properties property) throws IllegalAccessException{
		String infoEnabled = property.getProperty(ENABLE_LOG_INFO,ENABLED);
		String warningEnabled = property.getProperty(ENABLE_LOG_WARN,ENABLED);
		String errorEnabled = property.getProperty(ENABLE_LOG_ERROR,ENABLED);
		String sendEmailEnabled = property.getProperty(ENABLE_MESSAGE_SENDING,ENABLED);
		
		setInfoEnabled(ENABLED.equals(infoEnabled));
		setWarningEnabled(ENABLED.equals(warningEnabled));
		setErrorEnabled(ENABLED.equals(errorEnabled));
		setEmailSendEnabled(ENABLED.equals(sendEmailEnabled));
	}

	public boolean isInfoEnabled() {
		return infoEnabled;
	}

	public void setInfoEnabled(boolean infoEnabled) {
		this.infoEnabled = infoEnabled;
	}

	public boolean isWarningEnabled() {
		return warningEnabled;
	}

	public void setWarningEnabled(boolean warningEnabled) {
		this.warningEnabled = warningEnabled;
	}

	public boolean isErrorEnabled() {
		return errorEnabled;
	}

	public void setErrorEnabled(boolean errorEnabled) {
		this.errorEnabled = errorEnabled;
	}

	public boolean isEmailSendEnabled() {
		return emailSendEnabled;
	}

	public void setEmailSendEnabled(boolean emailSendEnabled) {
		this.emailSendEnabled = emailSendEnabled;
	}

}
