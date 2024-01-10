package com.vagner.springboot.department.project.error.college;

public class DuplicateDepartmentException extends Exception {
    public DuplicateDepartmentException(String string)
    {
        super(string);
    }
}
