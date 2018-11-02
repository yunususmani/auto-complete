package com.pramati.autocomplete.exception;

import java.util.ResourceBundle;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pramati.autocomplete.constants.Constants;

@Provider
@Component
public class ServiceExceptionHandler implements ExceptionMapper<Exception>
{
    private static final Logger logger = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    private static final ResourceBundle resource = ResourceBundle.getBundle(Constants.ERROR_PROPS);
    
    @Override
    public Response toResponse(Exception exception)
    {    	   	
    	if (exception instanceof ServiceException)
        {
            ServiceException serviceException = (ServiceException) exception;
            logInfo(serviceException.getErrorCode(), exception);
            return handleException(serviceException, serviceException.getErrorCode());
        }
        else if (exception instanceof BadRequestException || exception instanceof WebApplicationException)
        {
        	logInfo("Generic Exception", exception);
            return ((WebApplicationException) exception).getResponse();
        }
        
        logError("Generic Exception", exception);
        return getResponseEntity(null, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }


    private Response handleException(ServiceException exception,
                                     String errorCode) {
        
        Integer statusCode;

        try {
            String httpCodeStr = resource.getString(errorCode + Constants.DOT + Constants.HTTP_CODE);
            statusCode = Integer.parseInt(httpCodeStr.trim());
        } catch (Exception e) {
        	e.printStackTrace();
            statusCode = Constants.INTERNAL_SERVER_ERROR;
        }
        
        String errorMsg = resource.getString(errorCode);
        
        return getResponseEntity(errorMsg, statusCode);
    }
	
    protected Response getResponseEntity(Object response, Integer statusCode) {
       
        return Response.status(statusCode).entity(response).build();
    }

    private void logInfo(String code, Throwable throwable) {
        logger.info(code + " : " + throwable.getMessage(), throwable);
        
    }
    
    private void logError(String code, Throwable throwable) {
        logger.error(code + " : " + throwable.getMessage(), throwable);
        
    }
   
}
