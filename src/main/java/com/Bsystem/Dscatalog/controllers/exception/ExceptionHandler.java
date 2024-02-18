package com.Bsystem.Dscatalog.controllers.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.Bsystem.Dscatalog.exceptions.DataBaseException;
import com.Bsystem.Dscatalog.exceptions.ResourceEntityNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(ResourceEntityNotFoundException.class)
	public ResponseEntity<StandarError> entityNotFound(ResourceEntityNotFoundException e, HttpServletRequest request){
		StandarError err = new StandarError();
		err.setTimeStamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Resource nao encontrado");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandarError> dataBase(DataBaseException e, HttpServletRequest request){
		StandarError err = new StandarError();
		err.setTimeStamp(Instant.now());
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setError("DataBase Exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
