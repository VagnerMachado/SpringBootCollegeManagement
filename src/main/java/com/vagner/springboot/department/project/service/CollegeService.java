package com.vagner.springboot.department.project.service;

import com.vagner.springboot.department.project.entity.College;
import com.vagner.springboot.department.project.error.college.DuplicateDepartmentException;
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

    public College saveCollege(College college) throws DuplicateDepartmentException {
        //ensure that all the departments have unique department code. Other validation can be added if needed.
       Set<String> set = new HashSet<>();
        college.getDepartments().forEach(department -> set.add(department.getDepartmentCode()));
        if(set.size() != college.getDepartments().size())
            throw new DuplicateDepartmentException("Duplicate Department Code in Body. They must be unique");
        return collegeRepository.save(college);
    }

    public List<College> fetchCollegeList()
    {
        return collegeRepository.findAll();
    }

}
