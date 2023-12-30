package com.vagner.springboot.department.project.error;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// lombok for the getter and setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage 
{
	private HttpStatus status;
	private String message;
}
