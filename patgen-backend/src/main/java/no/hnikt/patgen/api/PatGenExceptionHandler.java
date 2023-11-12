package no.hnikt.patgen.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import no.hnikt.patgen.exception.NotFoundException;

/**
 * @author Brandon Lee
 *
 */
@RestControllerAdvice
public class PatGenExceptionHandler {

	@ResponseBody
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleConstraintException(ConstraintViolationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
	
	@ResponseBody
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<String> handleConstraintException(NotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
	
	@ResponseBody
	@ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnknownException(HttpServletRequest request, Exception exception) {
		 return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
