package com.vagner.springboot.department.project.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.vagner.springboot.department.project.error.UpdateDepartmentException;
import com.vagner.springboot.department.project.repository.DepartmentRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.vagner.springboot.department.project.entity.Department;
import com.vagner.springboot.department.project.error.DeleteDepartmentException;
import com.vagner.springboot.department.project.error.DepartmentNotFoundException;
import com.vagner.springboot.department.project.error.NoDepartmentWithProvidedNameException;

@Service // denotes this is a service
public class DepartmentServiceImpl implements DepartmentService
{
	@Autowired
	private DepartmentRepositoryInterface departmentRepository;

	@Override
	public Department saveDepartment(Department department)
	{
		return departmentRepository.save(department); //saves the department to database
	}

	@Override
	public List<Department> fetchDepartmentList()
	{
		return departmentRepository.findAll();
	}

	@Override
	public Department getDepartmentByID(Long id) throws DepartmentNotFoundException {
		Optional<Department> dept = departmentRepository.findById(id);
		if(dept.isPresent())
			return dept.get();
		throw new DepartmentNotFoundException("Department Not Found");
	}

	@Override
	public ResponseEntity<String> deleteDepartmentByID(Long id) throws DeleteDepartmentException
	{
		Optional<Department> opt = departmentRepository.findById(id);
		if(opt.isPresent())
		{
			departmentRepository.deleteById(id);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			return ResponseEntity.status(200).headers(headers).body("{ \"Deletion\" : \"Successful for Department with id " + id + "\"}");
		}
		throw new DeleteDepartmentException("Department Not Found, cannot delete.");
	}

	@Override
	public Department updateDepartmentByID(Department updatedDepartment, Long id) throws UpdateDepartmentException {
		Optional<Department> opt = departmentRepository.findById(id);
		Department departmentFromDatabase = null;
		//update existing
		if(opt.isPresent())
		{
			departmentFromDatabase = opt.get();
			if(Objects.nonNull(updatedDepartment.getDepartmentName()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentName()))
			{
				departmentFromDatabase.setDepartmentName(updatedDepartment.getDepartmentName());
			}

			if(Objects.nonNull(updatedDepartment.getDepartmentCode()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentCode()))
			{
				departmentFromDatabase.setDepartmentCode(updatedDepartment.getDepartmentCode());
			}

			if(Objects.nonNull(updatedDepartment.getDepartmentAddress()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentAddress()))
			{
				departmentFromDatabase.setDepartmentAddress(updatedDepartment.getDepartmentAddress());
			}
			return departmentRepository.save(departmentFromDatabase);
		}
		else // POST into database if departmentID is not found
		{
			if(Objects.nonNull(updatedDepartment.getDepartmentName()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentName()) &&
					Objects.nonNull(updatedDepartment.getDepartmentCode()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentCode()) &&
					Objects.nonNull(updatedDepartment.getDepartmentAddress()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentAddress()))
			{
				departmentFromDatabase = new Department();
				departmentFromDatabase.setDepartmentName(updatedDepartment.getDepartmentName());
				departmentFromDatabase.setDepartmentCode(updatedDepartment.getDepartmentCode());
				departmentFromDatabase.setDepartmentAddress(updatedDepartment.getDepartmentAddress());
			}
			else // if some fields are missing then do not post into the database.
			{
				throw new UpdateDepartmentException("Cannot put object as some fields are missing. Sample:\n {\n" +
						"    \"departmentName\" : \"name update\",\n" +
						"    \"departmentAddress\" : \"Address\",\n" +
						"    \"departmentCode\" : \"123DT\"\n" +
						"}");
			}
		}
		return departmentRepository.save(departmentFromDatabase);
	}

	@Override
	public List<Department> getDepartmentByName(String departmentName) throws NoDepartmentWithProvidedNameException
	{
		List<Department> dept = departmentRepository.findByDepartmentName(departmentName);
		if (dept == null || dept.isEmpty())
			throw new NoDepartmentWithProvidedNameException("There is no Department with provided name");
		return dept;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> customQueryForNameLike(String name) throws NoDepartmentWithProvidedNameException
	{
		List<String> obj =  departmentRepository.customQueryForNameLike(name);
		if(obj == null || obj.isEmpty())
			throw new NoDepartmentWithProvidedNameException("Failed to retrieve Departments with name similar to " + name);
		return obj; //will leave as no error if none present
	}

}
