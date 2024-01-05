package com.vagner.springboot.department.project.error.department;

@SuppressWarnings("serial")
public class NoDepartmentWithProvidedNameException extends Exception {
	public NoDepartmentWithProvidedNameException(String message) {
		super(message);
	}
}
