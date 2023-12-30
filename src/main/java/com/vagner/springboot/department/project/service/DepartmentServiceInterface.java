package com.vagner.springboot.department.project.service;

import java.util.List;

import com.vagner.springboot.department.project.entity.Department;
import com.vagner.springboot.department.project.error.DeleteDepartmentException;
import com.vagner.springboot.department.project.error.DepartmentNotFoundException;
import com.vagner.springboot.department.project.error.NoDepartmentWithProvidedNameException;


public interface DepartmentServiceInterface
{

	Department saveDepartment(Department department);

	List<Department> fetchDepartmentList();

	Department getDepartmentByID(Long id) throws DepartmentNotFoundException;

	String deleteDepartmentByID(Long id) throws DeleteDepartmentException;

	Department updateDepartmentByID(Department dept, Long id);

	Department getDepartmentByName(String departmentName) throws NoDepartmentWithProvidedNameException;

	List<String> myQueryForNameLike(String name) throws NoDepartmentWithProvidedNameException; //custom query
	



}
