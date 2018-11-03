package com.pramati.autocomplete.services;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pramati.autocomplete.cache.DataLoader;
import com.pramati.autocomplete.constants.Constants;
import com.pramati.autocomplete.exception.ServiceException;
import com.pramati.autocomplete.validator.ArgumentValidator;

@Component
public class AutocompleteService implements IAutocompleteService{

	private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
	
	private static List<String> cities = DataLoader.getCities();
	
	@Override
	public List<String> getCities(String text, String maxCandidate) {
		
		text = StringUtils.trimToEmpty(text);
		maxCandidate = StringUtils.trimToEmpty(maxCandidate);
		
		if(StringUtils.isBlank(maxCandidate)) {
			logger.info("atmost param was blank in request, seting it to default value 10");
			maxCandidate = "10";
		}
		
		//validate request params
		validateRequestParams(text, maxCandidate);
		
		final String exp = text.toUpperCase();
		final Integer maxSize = Integer.valueOf(maxCandidate);
		
		List<String> results = cities.stream().filter(city -> city.startsWith(exp)).limit(maxSize)
		                 .collect(Collectors.toList());
		
		logger.info("total ["+results.size()+"] results found for input request");
		
		return results;
	}

	private void validateRequestParams(String text, String maxCandidate) {
		if(StringUtils.isBlank(text)) {
			throw new ServiceException(Constants.ErrorConstants.INVALID_START);
		}
		
		if(!ArgumentValidator.validInteger(maxCandidate) || Integer.valueOf(maxCandidate) < 0) {
			throw new ServiceException(Constants.ErrorConstants.INVALID_ATMOST);
		}
		
	}
    
}
