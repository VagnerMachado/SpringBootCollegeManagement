package com.vagner.springboot.department.project.error;

import com.vagner.springboot.department.project.error.college.DuplicateDepartmentException;
import com.vagner.springboot.department.project.error.college.DuplicateMajorException;
import com.vagner.springboot.department.project.error.department.DeleteDepartmentException;
import com.vagner.springboot.department.project.error.department.DepartmentNotFoundException;
import com.vagner.springboot.department.project.error.department.NoDepartmentWithProvidedNameException;
import com.vagner.springboot.department.project.error.department.UpdateDepartmentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

// This setup for exception cleans up the returned values when exception is thrown

@RestControllerAdvice
@ResponseStatus
@Slf4j
public class ControllerAdviceList extends ResponseEntityExceptionHandler
{
	@ExceptionHandler(DepartmentNotFoundException.class) //response entity is given
	public ResponseEntity<ErrorMessage> departmentNotFoundException(DepartmentNotFoundException exception, WebRequest request)
	{
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, getPath(request), exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}
	
	@ExceptionHandler(DeleteDepartmentException.class) //response entity is given
	public ResponseEntity<ErrorMessage> deleteDepartmentException(DeleteDepartmentException exception, WebRequest request)
	{
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, getPath(request), exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}
	
	@ExceptionHandler(NoDepartmentWithProvidedNameException.class) //response entity is given
	public ResponseEntity<ErrorMessage> noDepartmentWithProvidedNameException(NoDepartmentWithProvidedNameException exception, WebRequest request)
	{
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, getPath(request), exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}

	@ExceptionHandler(UpdateDepartmentException.class) //response entity is given
	public ResponseEntity<ErrorMessage> updateDepartmentException(UpdateDepartmentException exception, WebRequest request)
	{
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, getPath(request), exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	@Override //Override the Bean Validation in Spring.
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
	{

		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, getPath(request), "Bean passed is failing validation. " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex)
	{
		List<String> list = new ArrayList<>();
		ex.getConstraintViolations().forEach(x -> list.add(x.getMessage()));
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, "Bean passed is failing validation: " + list + " - " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	@ExceptionHandler(DuplicateDepartmentException.class)
	protected ResponseEntity<Object> handleDuplicateDepartmentException(DuplicateDepartmentException ex, WebRequest request)
	{
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, getPath(request), "Duplicate Department Value: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	@ExceptionHandler(DuplicateMajorException.class)
	protected ResponseEntity<Object> handleDuplicateDepartmentException(DuplicateMajorException ex, WebRequest request)
	{
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, getPath(request), "Duplicate Majors in Array: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	private String getPath(WebRequest request)
	{
		return ((ServletWebRequest)request).getRequest().getMethod() + " : " + ((ServletWebRequest)request).getRequest().getRequestURI();
	}
}
