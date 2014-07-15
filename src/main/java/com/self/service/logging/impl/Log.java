package com.self.service.logging.impl;

public interface Log {
	public void error(String error);
	public void error(String error, Exception e);
	public void info(String info);
	public void warn(String warn);
}
