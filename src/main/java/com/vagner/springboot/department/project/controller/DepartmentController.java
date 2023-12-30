package com.vagner.springboot.department.project.controller;

import java.util.List;

import javax.validation.Valid;

import com.vagner.springboot.department.project.entity.Department;
import com.vagner.springboot.department.project.error.DeleteDepartmentException;
import com.vagner.springboot.department.project.error.DepartmentNotFoundException;
import com.vagner.springboot.department.project.error.NoDepartmentWithProvidedNameException;
import com.vagner.springboot.department.project.service.DepartmentServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	private DepartmentServiceInterface departmentService;
	private final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);
	
	@PostMapping("/departments")
	/**
	 * @RequestBody - converts JSON object to a Department
	 * @param department - a department to be saved
	 * @return Department
	 */
	//@Valid checks Department class for valid tags in instance
	public Department saveDepartment(@Valid @RequestBody Department department)
	{
		LOGGER.info("In saveDepartment at DepartmentController");
		//DepartmentServiceInterface service = new DepartmentServiceImplementation(); give up for autowire
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
	
	@DeleteMapping("/departments/{id}")
	public String deleteDepartmentByID(@PathVariable("id") Long id) throws DeleteDepartmentException
	{
		LOGGER.info("In deleteDepartmentByID at DepartmentController");
		return departmentService.deleteDepartmentByID(id);
		//return "Deleted SuccessFully";
	}
	
	@PutMapping("/departments/{id}") // 
	public Department updateDepartmentByID(@RequestBody Department dept, @PathVariable("id") Long id)
	{
		LOGGER.info("In updateDepartmentByID at DepartmentController");
		return departmentService.updateDepartmentByID(dept, id);
	}
	
	@GetMapping("/departments/name/{name}")
	public Department getDepartmentByID(@PathVariable("name") String name) throws NoDepartmentWithProvidedNameException
	{
		LOGGER.info("In getDepartmentByID at DepartmentController");
		return departmentService.getDepartmentByName(name);
	}
	
	@GetMapping("/departments/contains/{name}")
	public List<String> getDepartmentNameLike(@PathVariable String name)throws NoDepartmentWithProvidedNameException
	{
		
		return departmentService.myQueryForNameLike(name); 
      //picked a random name for custom query
	}
}
