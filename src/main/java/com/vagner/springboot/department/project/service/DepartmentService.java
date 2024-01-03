package com.vagner.springboot.department.project.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.vagner.springboot.department.project.entity.Address;
import com.vagner.springboot.department.project.error.department.UpdateDepartmentException;
import com.vagner.springboot.department.project.repository.DepartmentRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.vagner.springboot.department.project.entity.Department;
import com.vagner.springboot.department.project.error.department.DeleteDepartmentException;
import com.vagner.springboot.department.project.error.department.DepartmentNotFoundException;
import com.vagner.springboot.department.project.error.department.NoDepartmentWithProvidedNameException;

@Service // denotes this is a service
public class DepartmentService
{
	@Autowired
	private DepartmentRepositoryInterface departmentRepository;

	public Department saveDepartment(Department department)
	{
		return departmentRepository.save(department); //saves the department to database
	}

	public List<Department> fetchDepartmentList()
	{
		return departmentRepository.findAll();
	}

	public Department getDepartmentByID(Long id) throws DepartmentNotFoundException {
		Optional<Department> dept = departmentRepository.findById(id);
		if(dept.isPresent())
			return dept.get();
		throw new DepartmentNotFoundException("Department Not Found");
	}

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

	public Department updateDepartmentByID(Department updatedDepartment, Long id) throws UpdateDepartmentException {
		Optional<Department> opt = departmentRepository.findById(id);
		Department departmentFromDatabase = null;

		//update the object case the id exists in the database
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

			if(Objects.nonNull(updatedDepartment.getPhone()) && !"".equalsIgnoreCase(updatedDepartment.getPhone()))
			{
				departmentFromDatabase.setPhone(updatedDepartment.getPhone());
			}

			if(Objects.nonNull(updatedDepartment.getEmail()) && !"".equalsIgnoreCase(updatedDepartment.getEmail()))
			{
				departmentFromDatabase.setEmail(updatedDepartment.getEmail());
			}

			//update the address field by field as needed
			if(Objects.nonNull(updatedDepartment.getAddress()))
			{
				Address updatedAddress = updatedDepartment.getAddress();
				Address databaseAddress = departmentFromDatabase.getAddress();

				if(Objects.nonNull(updatedAddress.getDepartmentAddress()) && !"".equalsIgnoreCase(updatedAddress.getDepartmentAddress()))
				{
					databaseAddress.setDepartmentAddress(updatedAddress.getDepartmentAddress());
				}
				if(Objects.nonNull(updatedAddress.getAdditionalAddress()) && !"".equalsIgnoreCase(updatedAddress.getAdditionalAddress()))
				{
					databaseAddress.setAdditionalAddress(updatedAddress.getAdditionalAddress());
				}
				if(Objects.nonNull(updatedAddress.getCity()) && !"".equalsIgnoreCase(updatedAddress.getCity()))
				{
					databaseAddress.setCity(updatedAddress.getCity());
				}
				if(Objects.nonNull(updatedAddress.getState()) && !"".equalsIgnoreCase(updatedAddress.getState()))
				{
					databaseAddress.setState(updatedAddress.getState());
				}
				if(Objects.nonNull(updatedAddress.getZipCode()) && !"".equalsIgnoreCase(updatedAddress.getZipCode()))
				{
					databaseAddress.setZipCode(updatedAddress.getZipCode());
				}
				departmentFromDatabase.setAddress(databaseAddress);
			}

			return departmentRepository.save(departmentFromDatabase);
		}
		else // POST into database if departmentID is not found
		{
			if(Objects.nonNull(updatedDepartment.getDepartmentName()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentName()) &&
					Objects.nonNull(updatedDepartment.getDepartmentCode()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentCode()) &&
					Objects.nonNull(updatedDepartment.getPhone()) && !"".equalsIgnoreCase(updatedDepartment.getPhone()) &&
					Objects.nonNull(updatedDepartment.getEmail()) && !"".equalsIgnoreCase(updatedDepartment.getEmail()) &&
					Objects.nonNull(updatedDepartment.getAddress()))
			{
				departmentFromDatabase = new Department();
				departmentFromDatabase.setDepartmentName(updatedDepartment.getDepartmentName());
				departmentFromDatabase.setDepartmentCode(updatedDepartment.getDepartmentCode());
				departmentFromDatabase.setPhone(updatedDepartment.getPhone());
				departmentFromDatabase.setEmail(updatedDepartment.getEmail());
				departmentFromDatabase.setAddress(updatedDepartment.getAddress());
			}
			else // if some fields are missing then do not post into the database.
			{
				throw new UpdateDepartmentException("Cannot put object as some required fields are missing");
			}
		}
		return departmentRepository.save(departmentFromDatabase);
	}

	public List<Department> getDepartmentByName(String departmentName) throws NoDepartmentWithProvidedNameException
	{
		List<Department> dept = departmentRepository.findByDepartmentName(departmentName);
		if (dept == null || dept.isEmpty())
			throw new NoDepartmentWithProvidedNameException("There is no Department with provided name");
		return dept;
	}

	public List<Department> customQueryForNameLike(String name) throws NoDepartmentWithProvidedNameException
	{
		List<Department> obj =  departmentRepository.customQueryForNameLike(name);
		if(obj == null || obj.isEmpty())
			throw new NoDepartmentWithProvidedNameException("Failed to retrieve Departments with name similar to " + name);
		return obj; //will leave as no error if none present
	}

}
