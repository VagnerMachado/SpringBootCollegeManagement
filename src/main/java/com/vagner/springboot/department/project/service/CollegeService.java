package com.vagner.springboot.department.project.service;

import com.vagner.springboot.department.project.entity.College;
import com.vagner.springboot.department.project.error.college.DuplicateDepartmentException;
import com.vagner.springboot.department.project.error.college.DuplicateMajorException;
import com.vagner.springboot.department.project.repository.CollegeRepositoryInterface;
import com.vagner.springboot.department.project.utils.CollegeUtils;
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
        CollegeUtils.validateDepartmentCodes(college);
        //ensure that college major list has non repeated values. (Math == math) = true
        CollegeUtils.validateMajorList(college);
        return collegeRepository.save(college);
    }

    public List<College> fetchCollegeList()
    {
        List<College> list = collegeRepository.findAll();
        System.out.println(list);
        return list;
    }
}
