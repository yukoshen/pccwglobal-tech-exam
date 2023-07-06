package com.kjslocal.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.kjslocal.api.UserController;
import com.kjslocal.dto.ErrorDto;

import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes = UserController.class)
@Slf4j
public class UserExceptionAdvice {

	private final String LOG_TEMPLATE = "{}::{}() - {}";

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDto> handleUserException(UserException ex) {

		final ErrorDto error = ErrorDto.builder().errorMessage(ex.getErrMessage()).build();

		log.error(LOG_TEMPLATE, getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
				ex.getMessage());

		return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		ErrorDto error = new ErrorDto();

		if (ex.getBindingResult().hasErrors()) {
			for (final FieldError field : ex.getBindingResult().getFieldErrors()) {
				error = ErrorDto.builder().errorMessage(field.getDefaultMessage()).build();
			}
		}

		log.error(LOG_TEMPLATE, getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
				ex.getMessage());

		return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorDto> handleMethodArgumentTypeMismatchExceptionn(
			MethodArgumentTypeMismatchException ex) {
		ErrorDto error = new ErrorDto();

		error = ErrorDto.builder().errorMessage(ex.getMessage()).build();

		log.error(LOG_TEMPLATE, getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
				ex.getMessage());

		return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

}
