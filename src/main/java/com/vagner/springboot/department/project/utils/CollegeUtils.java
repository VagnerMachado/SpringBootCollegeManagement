package com.vagner.springboot.department.project.utils;

import com.vagner.springboot.department.project.entity.College;
import com.vagner.springboot.department.project.error.college.DuplicateDepartmentException;
import com.vagner.springboot.department.project.error.college.DuplicateMajorException;

import java.util.HashSet;
import java.util.Set;

public class CollegeUtils
{
    // AUXILIARY METHODS
    public static void validateDepartmentCodes(College college) throws DuplicateDepartmentException {
        Set<String> deptCodeSet = new HashSet<>();
        college.getDepartments().forEach(department -> deptCodeSet.add(department.getDepartmentCode()));
        if(deptCodeSet.size() != college.getDepartments().size())
            throw new DuplicateDepartmentException("Duplicate Department Code in Body. They must be unique");
    }

    public static void validateMajorList(College college) throws DuplicateMajorException {
        Set<String> majorSet = new HashSet<>();
        college.getMajors().forEach(major -> majorSet.add(major.toLowerCase()));
        if(majorSet.size() != college.getMajors().size())
            throw new DuplicateMajorException("Duplicate Major in list. They must be unique");
    }
}
