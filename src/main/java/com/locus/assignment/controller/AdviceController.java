package com.locus.assignment.controller;

import com.locus.assignment.dto.response.ApiResponse;
import com.locus.assignment.exception.ApiException;
import com.locus.assignment.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AdviceController {

    private static final Logger logger = LoggerFactory.getLogger(AdviceController.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApiException.class)
    public @ResponseBody
    ApiResponse handleCustomExceptions(ApiException ex, WebRequest request) {
        logger.error(ex.getMessage());
        return new ApiResponse(ex.getStatus(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody
    ApiResponse handleExceptions(Exception ex, WebRequest request) {
        logger.error(String.format("An unexpected error has occurred '%s'", ex.getMessage()), ex.getCause().getStackTrace());
        return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.INTERNAL_SERVER_ERROR_MESSAGE);
    }

}
