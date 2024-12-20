package com.products.dto.response;

public class APICustomize<T> {
	
	private String statusCode;
    private  String message;
    private T result ;
    
    
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	
	public APICustomize(String statusCode, String message, T result) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.result = result;
	}
    
	public APICustomize() {

	}
    
}
