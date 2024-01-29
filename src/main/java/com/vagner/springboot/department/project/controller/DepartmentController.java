package com.vagner.springboot.department.project.controller;

import java.util.List;
import javax.validation.Valid;
import com.vagner.springboot.department.project.entity.College;
import com.vagner.springboot.department.project.entity.Department;
import com.vagner.springboot.department.project.error.college.CollegeNotFoundException;
import com.vagner.springboot.department.project.error.college.DuplicateDepartmentException;
import com.vagner.springboot.department.project.error.department.DeleteDepartmentException;
import com.vagner.springboot.department.project.error.department.DepartmentNotFoundException;
import com.vagner.springboot.department.project.error.department.NoDepartmentWithProvidedNameException;
import com.vagner.springboot.department.project.error.department.UpdateDepartmentException;
import com.vagner.springboot.department.project.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController // a rest controller so can make rest api here
public class DepartmentController 
{
	@Autowired // Wires the Department Object Definition
	private DepartmentService departmentService;
	private final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

	/**
	 * POST a department into a college
	 * @param department - a valid Department object
	 * @param collegeId - the college to insert the department into
	 * @return - a department object
	 * @throws DuplicateDepartmentException - case the department with same code exist
	 * @throws CollegeNotFoundException - case the ID for college passed in parameter does not exist.
	 */
	@PostMapping("/departments/{collegeId}")
	//@Valid checks Department class for valid tags in object declaration
	public College saveDepartment(@Valid @RequestBody Department department, @PathVariable String collegeId) throws CollegeNotFoundException, DuplicateDepartmentException {
		LOGGER.info("In saveDepartment at DepartmentController - college id is " + collegeId);
		department.setDepartmentId(null); // causes the auto increment to be used.
		return departmentService.saveDepartment(department, collegeId); // will associate a dept with a collge id
	}

	/**
	 * GET all departments
	 * @return - a list of departments
	 */
	@GetMapping("/departments")
	public List<Department> fetchDepartmentList()
	{
		LOGGER.info("In fetchDepartmentList at DepartmentController");
		return departmentService.fetchDepartmentList();
	}

	/**
	 * GET a department by ID
	 * @param id - the ID for the department to retrieve
	 * @return - the department witht the id passed as param
	 * @throws DepartmentNotFoundException - case the department with the id does not exist.
	 */
	@GetMapping("/departments/{id}")
	public Department getDepartmentByID(@PathVariable("id") Long id) throws DepartmentNotFoundException
	{
		LOGGER.info("In getDepartmentByID at DepartmentController");
		return departmentService.getDepartmentByID(id);
	}

	/**
	 * DELETE a department
	 * @param id - ID for the department to delete
	 * @return - message stating that department was deleted.
	 * @throws DeleteDepartmentException - case the department with ID is not found.
	 */
	@DeleteMapping(value = "/departments/{id}")
	public ResponseEntity<String> deleteDepartmentByID(@PathVariable("id") Long id) throws DeleteDepartmentException
	{
		LOGGER.info("In deleteDepartmentByID at DepartmentController");
		return departmentService.deleteDepartmentByID(id);
	}

	/**
	 * PUT department - to be used to update a department in a college
	 * @param dept - the deartment object with updated values
	 * @param collegeId - the college id for the department to belong to
	 * @param departmentId - the id for the dept to update.
	 * @return - the updated college objet
	 * @throws UpdateDepartmentException - case the department id does not exist.
	 * @throws MethodArgumentNotValidException - case the object is not valid.
	 * @throws CollegeNotFoundException - case the college with id passed is not found
	 */
	@PutMapping("/departments/college/{collegeId}/department/{departmentId}") //
	public College updateDepartmentByID(@RequestBody @Valid Department dept, @PathVariable("collegeId") Long collegeId, @PathVariable("departmentId") Long departmentId) throws UpdateDepartmentException, MethodArgumentNotValidException, CollegeNotFoundException {
		LOGGER.info("In updateDepartmentByID at DepartmentController");
		return departmentService.updateDepartmentByID(dept, collegeId, departmentId);
	}

	/**
	 * GET a department by exact name
	 * @param name - the name to search for
	 * @return - the department with matching name
	 * @throws NoDepartmentWithProvidedNameException - case no dept is found with exact name.
	 */
	@GetMapping("/departments/name/{name}")
	public List<Department> getDepartmentByName(@PathVariable String name) throws NoDepartmentWithProvidedNameException
	{
		LOGGER.info("In getDepartmentByID at DepartmentController");
		return departmentService.getDepartmentByName(name);
	}

	/**
	 * GET department with name similar to PARAM
	 * @param name - the department name to search for
	 * @return - the department with name similar to
	 * @throws NoDepartmentWithProvidedNameException - case there are not any department with name similar to that
	 */
	@GetMapping("/departments/name/like/{name}")
	public List<Department> getDepartmentNameLike(@PathVariable String name)throws NoDepartmentWithProvidedNameException
	{
		LOGGER.info("In getDepartmentNameLike at DepartmentController");
		return departmentService.customQueryForNameLike(name);
	}
}
