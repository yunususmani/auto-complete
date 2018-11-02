package com.pramati.autocomplete.validator;

public class ArgumentValidator {

	public static boolean validInteger(String maxCandidate) 
	{
		try {
			Integer.valueOf(maxCandidate);
		}catch (Exception e) {
			return false;
		}
		
		return true;
	}
}
