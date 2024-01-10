package com.vagner.springboot.department.project.error.college;

public class DuplicateMajorException extends Exception
{
    public DuplicateMajorException(String message)
    {
        super(message);
    }
}
