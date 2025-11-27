package com.vn.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AdviceController {
	@ExceptionHandler(Exception.class)
	public String globalExceptionHandler(Exception ex, WebRequest request) {

		return "/error/error";
	}
}
