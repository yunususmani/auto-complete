package com.pramati.autocomplete.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.pramati.autocomplete.services.AutocompleteService;

@Path("/")
public class Autocomplete {


    @Autowired
    private AutocompleteService service;

    @GET
    @Path("/suggest_cities")
    @Produces("text/plain")
    public Response suggest(@QueryParam("start") String start, @QueryParam("atmost") String atmost) 
    {
    	List<String> result = service.getCities(start, atmost);
    	
    	StringBuffer buffer = new StringBuffer();
    	result.forEach((str) -> buffer.append(str).append("\n"));
    	
    	if(buffer.length() > 1) {
    		buffer.replace(buffer.lastIndexOf("\n"), buffer.length(), "");
    	}
    	return Response.status(200).entity(buffer.toString()).build();

    }
}
