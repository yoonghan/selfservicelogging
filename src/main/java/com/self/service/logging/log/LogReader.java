package com.self.service.logging.log;

import java.io.IOException;
import java.util.Properties;

import static com.self.service.logging.impl.PropertyFiles.*;

import com.self.service.util.common.PropertyLoaderUtil;

class LogReader {

	private final String CLASS_NAME = LogReader.class.getName();
	
	private boolean infoEnabled = true;
	private boolean warningEnabled = true;
	private boolean errorEnabled = true;
	private boolean emailSendEnabled = true;
	
	public LogReader(){
		initProperty();
	}
	
	private void initProperty(){
		Properties prop;
		try {
			prop = new PropertyLoaderUtil().loadProperty(null, CLASS_NAME, LOGFILE);
			checkProperties(prop);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private void checkProperties(Properties prop) throws IllegalAccessException{
		String infoEnabled = prop.getProperty(ENABLE_LOG_INFO,ENABLED);
		String warningEnabled = prop.getProperty(ENABLE_LOG_WARN,ENABLED);
		String errorEnabled = prop.getProperty(ENABLE_LOG_ERROR,ENABLED);
		String sendEmailEnabled = prop.getProperty(ENABLE_MESSAGE_SENDING,ENABLED);
		
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
