package com.pramati.autocomplete.exception;

import java.util.ResourceBundle;

import com.pramati.autocomplete.constants.Constants;

public class ServiceException extends RuntimeException{
	
	private static final ResourceBundle resource =   ResourceBundle.getBundle(Constants.ERROR_PROPS); 

	private String errorCode;
	
	private String message;

	public ServiceException(String errorCode) {
		super(errorCode);
		this.errorCode=errorCode;
	}
	
	public ServiceException(String errorCode,String message) {
		super(message);
		this.errorCode=errorCode;
		this.message=message;
	}
	
	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String errorCode,String message, Throwable cause) {
		super(cause);
		this.errorCode=errorCode;
		this.message=message;
	}
	
	@Override
	public String getMessage() {
		return getLocalizedMessage();
	}
	
	@Override
	public String getLocalizedMessage() {
		return getBundle(this.errorCode);
	}
	
	private String getBundle(String errorCode){
		return resource.getString(errorCode);
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
}
