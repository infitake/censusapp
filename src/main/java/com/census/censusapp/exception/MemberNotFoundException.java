/**
 * 
 */
package com.census.censusapp.exception;


public class MemberNotFoundException extends RuntimeException {


	private static final long serialVersionUID = 199884072806328377L;

	public MemberNotFoundException(String id) {
		super("Could not find family " + id);
	}
}

