package com.vagner.springboot.department.project.controller;

import com.vagner.springboot.department.project.entity.College;
import com.vagner.springboot.department.project.error.college.CollegeNotFoundException;
import com.vagner.springboot.department.project.error.college.DuplicateDepartmentException;
import com.vagner.springboot.department.project.error.college.DuplicateMajorException;
import com.vagner.springboot.department.project.service.CollegeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class CollegeController
{
    @Autowired
    private CollegeService collegeService;
    private final Logger LOGGER = LoggerFactory.getLogger(CollegeController.class);

    @PostMapping("/college")
    //@Valid checks Department class for valid tags in object declaration
    public College saveCollege(@Valid @RequestBody College college) throws DuplicateDepartmentException, DuplicateMajorException {
        LOGGER.info("In saveCollege at CollegeController");
        college.setCollegeId(null); // causes the auto increment to be used.
        return collegeService.saveCollege(college);
    }

    @GetMapping("/college")
    public List<College> fetchCollegeList()
    {
        LOGGER.info("In fetchCollegeList at CollegeController");
        return collegeService.fetchCollegeList();
    }

    @DeleteMapping(value = "/college/{id}")
    public ResponseEntity<String> deleteDepartmentByID(@PathVariable("id") Long id) throws CollegeNotFoundException
    {
        LOGGER.info("In deleteDepartmentByID at DepartmentController");
        return collegeService.deleteCollegeById(id);
    }

    @PutMapping(value = "/college/{collegeId}")
    public College updateCollege(@RequestBody College updatedCollege, @PathVariable long collegeId) throws CollegeNotFoundException {
        LOGGER.info("In updateCollege at CollegeController");
        return collegeService.updateCollege(updatedCollege, collegeId);

    }
}
