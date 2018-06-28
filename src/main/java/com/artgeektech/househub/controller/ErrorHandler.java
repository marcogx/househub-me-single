package com.artgeektech.househub.controller;

import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Created by guang on 6:36 PM 4/25/18.
 */
@ControllerAdvice
public class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(value = {Exception.class, RuntimeException.class,
        DataException.class, SQLIntegrityConstraintViolationException.class})
    public String error500(HttpServletRequest request, Exception e){
        logger.error(e.getMessage(),e);
        logger.error(request.getRequestURL() + " encounter 500");
        return "error/500";
    }
}
