package com.pramati.autocomplete.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.pramati.autocomplete.constants.Constants;
import com.pramati.autocomplete.exception.ServiceException;
import com.pramati.autocomplete.services.AutocompleteService;

@RunWith(MockitoJUnitRunner.class)
public class TestAutocompleteService {

	@InjectMocks
	AutocompleteService service;
	
	@Test
	public void validateQueryParams() {
		
		String[] start = {"", null};
		String[] atmost = {"-123", "1.2", "1.2.3", "abcd", "10.0"};
		
		//validate start param
		for(String text : start) {
			boolean thrown = false;
			try {
				service.getCities(text, "10");
			}catch(ServiceException e) {
				thrown = true;
				Assert.assertEquals(Constants.ErrorConstants.INVALID_START, e.getErrorCode());
			}
			 Assert.assertTrue(thrown);
		}
		
		//validate atmost param
		for(String max : atmost) {
			boolean thrown = false;
			try {
				service.getCities("AB", max);
			}catch(ServiceException e) {
				thrown = true;
				Assert.assertEquals(Constants.ErrorConstants.INVALID_ATMOST, e.getErrorCode());
			}
			 Assert.assertTrue(thrown);
		}
	}
	
	public void testServiceWithDefaultMaxCandidates() {
		List<String> cities = service.getCities("N", null);
		
		Assert.assertEquals(cities.size(), 10);
		
	}
	
	public void testServiceWithGivenMaxCandidates() {
		List<String> cities = service.getCities("N", "20");
		
		Assert.assertEquals(cities.size(), 20);
		
	}
}
