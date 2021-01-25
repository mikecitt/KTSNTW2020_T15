package com.example.culturecontentapp.exception;

public class SubTypeAlreadyExistsException extends RuntimeException {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public SubTypeAlreadyExistsException(String message){
        super(message);
    }
}
