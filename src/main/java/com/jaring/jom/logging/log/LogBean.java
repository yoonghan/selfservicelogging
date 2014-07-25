package com.jaring.jom.logging.log;

import java.util.Properties;

import static com.jaring.jom.logging.impl.PropertyFiles.*;

import com.jaring.jom.util.impl.PropertyMapperImpl;

class LogBean implements PropertyMapperImpl {
	
	private final int INFO_ENABLE = 0b00001;
	private final int WARNING_ENABLE = 0b00010;
	private final int ERROR_ENABLE = 0b00100;
	private final int EMAIL_SEND_ENABLE = 0b01000;
	
	private final int MAX_SWITCH = 5;
	
	private int enabledLog = 0b01111;
	
	@Override
	public void map(Properties property) throws IllegalAccessException{
		String logEnabling = property.getProperty(ENABLE_LOG);

		try{
			
			//check length and add 0 to fill in the subsequent values.
			for(int i=0; i < MAX_SWITCH+1; i++){
				logEnabling = "0"+logEnabling;
			}
			
			enabledLog = Integer.parseInt(logEnabling, 2);
		}catch(NumberFormatException nfe){
			throw new IllegalAccessException("Unknown "+ENABLE_LOG+" configured: Using default log.");
		}
	}

	public boolean isInfoEnabled() {
		return checkFlag(INFO_ENABLE);
	}

	public void setInfoEnabled() {
		setFlag(INFO_ENABLE);
	}

	public boolean isWarningEnabled() {
		return checkFlag(WARNING_ENABLE);
	}

	public void setWarningEnabled(boolean warningEnabled) {
		setFlag(WARNING_ENABLE);
	}

	public boolean isErrorEnabled() {
		return checkFlag(ERROR_ENABLE);
	}

	public void setErrorEnabled(boolean errorEnabled) {
		setFlag(ERROR_ENABLE);
	}

	public boolean isEmailSendEnabled() {
		return checkFlag(EMAIL_SEND_ENABLE);
	}

	public void setEmailSendEnabled(boolean emailSendEnabled) {
		setFlag(EMAIL_SEND_ENABLE);
	}
	
	private boolean checkFlag(int flag){
		return (flag & enabledLog) == flag;
	}
	
	private void setFlag(int flag){
		this.enabledLog = this.enabledLog | flag;
	}

}
