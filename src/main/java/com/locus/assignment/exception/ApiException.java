package com.locus.assignment.exception;

public class ApiException extends RuntimeException {

    private int status;
    private String message;
    private Object result;

    public ApiException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public ApiException(int status, String message, Object result) {
        super(message);
        this.status = status;
        this.message = message;
        this.result = result;
    }
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
