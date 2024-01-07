package com.vagner.springboot.department.project.service;

import com.vagner.springboot.department.project.entity.College;
import com.vagner.springboot.department.project.entity.Department;
import com.vagner.springboot.department.project.error.department.DepartmentNotFoundException;
import com.vagner.springboot.department.project.repository.CollegeRepositoryInterface;
import com.vagner.springboot.department.project.repository.DepartmentRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollegeService
{
    @Autowired
    private CollegeRepositoryInterface collegeRepository;

    public College saveCollege(College college) { return collegeRepository.save(college);
    }

    public List<College> fetchCollegeList()
    {
        return collegeRepository.findAll();
    }

}
