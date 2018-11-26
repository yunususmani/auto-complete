package com.pramati.autocomplete.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mockito.internal.verification.AtMost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pramati.autocomplete.cache.DataLoader;
import com.pramati.autocomplete.cache.Node;
import com.pramati.autocomplete.constants.Constants;
import com.pramati.autocomplete.exception.ServiceException;
import com.pramati.autocomplete.validator.ArgumentValidator;

@Component
public class AutocompleteService implements IAutocompleteService{

	private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
	
	private static Node trie = DataLoader.getTrie();
	
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
		
		List<String> results = new ArrayList<>();
		
		// Iterate to the end of the prefix
	    Node curr = trie;
	    for (char c : text.toUpperCase().toCharArray()) {
	        if (curr.children.containsKey(c)) {
	            curr = curr.children.get(c);
	        } else {
	            return results;
	        }
	    }
		
	    findAllMatchWords(curr, results, Integer.valueOf(maxCandidate));
		logger.info("total ["+results.size()+"] results found for input request");
		
		return results;
	}

	private void findAllMatchWords(Node curr, List<String> results, int maxResult) 
	{
		if(results.size() >= maxResult)
			return;
		
		if(curr.isWord)
			results.add(curr.prefix);
		
		for (Character c : curr.children.keySet()) {
			findAllMatchWords(curr.children.get(c), results, maxResult);
	    }
		
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
