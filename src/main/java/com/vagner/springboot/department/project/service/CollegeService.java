package com.vagner.springboot.department.project.service;

import com.vagner.springboot.department.project.entity.College;
import com.vagner.springboot.department.project.error.college.DuplicateDepartmentException;
import com.vagner.springboot.department.project.error.college.DuplicateMajorException;
import com.vagner.springboot.department.project.repository.CollegeRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CollegeService
{
    @Autowired
    private CollegeRepositoryInterface collegeRepository;

    public College saveCollege(College college) throws DuplicateDepartmentException, DuplicateMajorException
    {
        //ensure that all the departments have unique department code. Other validation can be added if needed.
        validateDepartmentCodes(college);
        //ensure that college major list has non repeated values. (Math == math) = true
        validateMajorList(college);
        return collegeRepository.save(college);
    }

    public List<College> fetchCollegeList()
    {
        return collegeRepository.findAll();
    }

    // AUXILIARY METHODS
    private void validateDepartmentCodes(College college) throws DuplicateDepartmentException {
        Set<String> deptCodeSet = new HashSet<>();
        college.getDepartments().forEach(department -> deptCodeSet.add(department.getDepartmentCode()));
        if(deptCodeSet.size() != college.getDepartments().size())
            throw new DuplicateDepartmentException("Duplicate Department Code in Body. They must be unique");
    }

    private void validateMajorList(College college) throws DuplicateMajorException {
        Set<String> majorSet = new HashSet<>();
        college.getMajors().forEach(major -> majorSet.add(major.toLowerCase()));
        if(majorSet.size() != college.getMajors().size())
            throw new DuplicateMajorException("Duplicate Major in list. They must be unique");
    }
}
