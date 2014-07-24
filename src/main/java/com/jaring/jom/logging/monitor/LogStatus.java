package com.jaring.jom.logging.monitor;

import java.io.IOException;

import com.jaring.jom.util.email.EmailUtil;

class LogStatus {

	private int warningCount=0;
	private int errorCount=0;
	private String firstError="";
	private boolean toSendError=false;
	
	private final static class Singleton{
		public final static LogStatus instance = new LogStatus();
	}
	
	private LogStatus(){
		//init nothing.
	}
	
	public static LogStatus getInstance(){
		return Singleton.instance;
	}

	public int getWarningCount() {
		return warningCount;
	}
	
	public void setWarningCount(int warningCount) {
		this.warningCount = warningCount;
	}
	
	public void incrWarningCount() {
		this.warningCount++;
	}
	
	public int getErrorCount() {
		return errorCount;
	}
	
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}
	
	public void incrErrorCount() {
		this.errorCount++;
	}
	
	public String getFirstError() {
		return firstError;
	}

	public void setFirstError(String firstError) {
		this.firstError = firstError;
	}
	
	public boolean isToSendError() {
		return toSendError;
	}
	
	public void setToSendError(boolean toSendError) {
		this.toSendError = toSendError;
	}
	
	public void flushMessages(){
		int warningCount = getWarningCount();
		int errorCount = getErrorCount();
		boolean toSendError = isToSendError();
		String firstMessage = getFirstError();
		
		resetMessages();
		
		constructEmail(warningCount, errorCount,
				toSendError, firstMessage);
		
	}
	
	public void resetMessages() {
		setWarningCount(0);
		setErrorCount(0);
		setFirstError("");
		setToSendError(false);
	}

	private void constructEmail(int warningCount, int errorCount, 
			boolean toSendError, String firstMessage) {
		StringBuilder emailMessage = new StringBuilder(500);
		
		emailMessage
					.append("Warning Count:").append(warningCount).append("\n")
					.append("Error Count:").append(errorCount).append("\n")
					.append("\n")
					.append("---------------------------------------------")
					.append("\n")
					.append(firstMessage);
		
		try {
			EmailUtil emailUtil = new EmailUtil();
			emailUtil.sendEmail(emailMessage.toString());
		} catch (ClassNotFoundException | IllegalAccessException | IOException e) {
			System.err.println(e.toString());
			e.printStackTrace();
		}
		
		emailMessage = null;
	}

}
