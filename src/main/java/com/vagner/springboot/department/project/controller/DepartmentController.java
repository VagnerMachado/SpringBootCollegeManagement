package com.vagner.springboot.department.project.controller;

import java.util.List;
import javax.validation.Valid;
import com.vagner.springboot.department.project.entity.Department;
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

	@PostMapping("/departments")
	//@Valid checks Department class for valid tags in object declaration
	public Department saveDepartment(@Valid @RequestBody Department department)
	{
		LOGGER.info("In saveDepartment at DepartmentController");
		department.setDepartmentId(null); // causes the auto increment to be used.
		return departmentService.saveDepartment(department);
	}
	
	@GetMapping("/departments")
	public List<Department> fetchDepartmentList()
	{
		LOGGER.info("In fetchDepartmentList at DepartmentController");
		return departmentService.fetchDepartmentList();
	}
	
	@GetMapping("/departments/{id}")
	public Department getDepartmentByID(@PathVariable("id") Long id) throws DepartmentNotFoundException
	{
		LOGGER.info("In getDepartmentByID at DepartmentController");
		return departmentService.getDepartmentByID(id);
	}
	
	@DeleteMapping(value = "/departments/{id}")
	public ResponseEntity<String> deleteDepartmentByID(@PathVariable("id") Long id) throws DeleteDepartmentException
	{
		LOGGER.info("In deleteDepartmentByID at DepartmentController");
		return departmentService.deleteDepartmentByID(id);
	}
	
	@PutMapping("/departments/{id}") // 
	public Department updateDepartmentByID(@RequestBody @Valid Department dept, @PathVariable("id") Long id) throws UpdateDepartmentException, MethodArgumentNotValidException {
		LOGGER.info("In updateDepartmentByID at DepartmentController");
		return departmentService.updateDepartmentByID(dept, id);
	}
	
	@GetMapping("/departments/name/{name}")
	public List<Department> getDepartmentByName(@PathVariable String name) throws NoDepartmentWithProvidedNameException
	{
		LOGGER.info("In getDepartmentByID at DepartmentController");
		return departmentService.getDepartmentByName(name);
	}
	
	@GetMapping("/departments/name/like/{name}")
	public List<Department> getDepartmentNameLike(@PathVariable String name)throws NoDepartmentWithProvidedNameException
	{
		LOGGER.info("In getDepartmentNameLike at DepartmentController");
		return departmentService.customQueryForNameLike(name);
	}
}
