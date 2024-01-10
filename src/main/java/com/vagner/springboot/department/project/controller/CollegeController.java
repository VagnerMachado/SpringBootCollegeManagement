package com.vagner.springboot.department.project.controller;

import com.vagner.springboot.department.project.entity.College;
import com.vagner.springboot.department.project.error.college.DuplicateDepartmentException;
import com.vagner.springboot.department.project.error.college.DuplicateMajorException;
import com.vagner.springboot.department.project.service.CollegeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
//
//    @GetMapping("/departments/{id}")
//    public Department getDepartmentByID(@PathVariable("id") Long id) throws DepartmentNotFoundException
//    {
//        LOGGER.info("In getDepartmentByID at DepartmentController");
//        return departmentService.getDepartmentByID(id);
//    }
//
//    @DeleteMapping(value = "/departments/{id}")
//    public ResponseEntity<String> deleteDepartmentByID(@PathVariable("id") Long id) throws DeleteDepartmentException
//    {
//        LOGGER.info("In deleteDepartmentByID at DepartmentController");
//        return departmentService.deleteDepartmentByID(id);
//    }
//
//    @PutMapping("/departments/{id}") //
//    public Department updateDepartmentByID(@RequestBody @Valid Department dept, @PathVariable("id") Long id) throws UpdateDepartmentException, MethodArgumentNotValidException {
//        LOGGER.info("In updateDepartmentByID at DepartmentController");
//        return departmentService.updateDepartmentByID(dept, id);
//    }
//
//    @GetMapping("/departments/name/{name}")
//    public List<Department> getDepartmentByName(@PathVariable String name) throws NoDepartmentWithProvidedNameException
//    {
//        LOGGER.info("In getDepartmentByID at DepartmentController");
//        return departmentService.getDepartmentByName(name);
//    }
//
//    @GetMapping("/departments/name/like/{name}")
//    public List<Department> getDepartmentNameLike(@PathVariable String name)throws NoDepartmentWithProvidedNameException
//    {
//        LOGGER.info("In getDepartmentNameLike at DepartmentController");
//        return departmentService.customQueryForNameLike(name);
//    }
}
