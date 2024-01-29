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

    /**
     * POST a college into the database
     * @param college - A valid college object via POST body
     * @return - the college added into the database
     * @throws DuplicateDepartmentException - case the college has duplicate department names.
     * @throws DuplicateMajorException - case major list has duplicate major list.
     */
    @PostMapping("/college")
    //@Valid checks Department class for valid tags in object declaration
    public College saveCollege(@Valid @RequestBody College college) throws DuplicateDepartmentException, DuplicateMajorException {
        LOGGER.info("In saveCollege at CollegeController");
        college.setCollegeId(null); // causes the auto increment to be used.
        return collegeService.saveCollege(college);
    }

    /**
     * GET all colleges from the database
     * @return - a list of colleges in the database.
     */
    @GetMapping("/college")
    public List<College> fetchCollegeList()
    {
        LOGGER.info("In fetchCollegeList at CollegeController");
        return collegeService.fetchCollegeList();
    }

    /**
     * DELETE college by ID
     * @param id - the ID of the college to delete
     * @return - message regarding the deletion
     * @throws CollegeNotFoundException - case college with ID is not found.
     */
    @DeleteMapping(value = "/college/{id}")
    public ResponseEntity<String> deleteDepartmentByID(@PathVariable("id") Long id) throws CollegeNotFoundException
    {
        LOGGER.info("In deleteDepartmentByID at DepartmentController");
        return collegeService.deleteCollegeById(id);
    }

    /**
     * PUT college in database
     * @param updatedCollege - A college object to be used to updaate.
     * @param collegeId - the id for the college in the database to update
     * @return - the updated college object
     * @throws CollegeNotFoundException - case the college ID does not exist in the database
     * @throws DuplicateMajorException - case the college passed as param has a duplicate major in the list.
     *
     * NOTE: college departments are updated via @PutMapping("/departments/college/{collegeId}/department/{departmentId}")
     */
    @PutMapping(value = "/college/{collegeId}")
    public College updateCollege(@RequestBody College updatedCollege, @PathVariable long collegeId) throws CollegeNotFoundException, DuplicateMajorException {
        LOGGER.info("In updateCollege at CollegeController");
        return collegeService.updateCollege(updatedCollege, collegeId);

    }
}
