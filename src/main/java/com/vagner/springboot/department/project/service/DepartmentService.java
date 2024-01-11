package com.vagner.springboot.department.project.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.vagner.springboot.department.project.entity.CollegeAddress;
import com.vagner.springboot.department.project.entity.College;
import com.vagner.springboot.department.project.error.college.CollegeNotFoundException;
import com.vagner.springboot.department.project.error.department.UpdateDepartmentException;
import com.vagner.springboot.department.project.repository.CollegeRepositoryInterface;
import com.vagner.springboot.department.project.repository.DepartmentRepositoryInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.vagner.springboot.department.project.entity.Department;
import com.vagner.springboot.department.project.error.department.DeleteDepartmentException;
import com.vagner.springboot.department.project.error.department.DepartmentNotFoundException;
import com.vagner.springboot.department.project.error.department.NoDepartmentWithProvidedNameException;

@Slf4j
@Service // denotes this is a service
public class DepartmentService
{
	@Autowired
	private DepartmentRepositoryInterface departmentRepository;

	@Autowired
	private CollegeRepositoryInterface collegeRepository;

	public College saveDepartment(Department department, String collegeId) throws Exception {
		log.info(String.valueOf(department));
		Optional<College> college = collegeRepository.findById(Long.valueOf(collegeId));
		if(!college.isPresent())
			throw new CollegeNotFoundException("College not present");
		College collegeFromDatabase = college.get();
		collegeFromDatabase.setCollegeName("Update name of college via the department controller");
			/*
		validation blocck
		- retrieve the college with id passed
			- if not existing then throw an exception
		- when retrieved then validate the new dept code is not existing in current list of depts.
		- if unique then post
		 */
		return collegeRepository.save(collegeFromDatabase); //saves the department to database
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

//	public Department updateDepartmentByID(Department updatedDepartment, Long id) throws UpdateDepartmentException {
//		Optional<Department> opt = departmentRepository.findById(id);
//		Department departmentFromDatabase = null;
//
//		//update the object case the id exists in the database
//		if(opt.isPresent())
//		{
//			departmentFromDatabase = opt.get();
//			if(Objects.nonNull(updatedDepartment.getDepartmentName()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentName()))
//			{
//				departmentFromDatabase.setDepartmentName(updatedDepartment.getDepartmentName());
//			}
//
//			if(Objects.nonNull(updatedDepartment.getDepartmentCode()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentCode()))
//			{
//				departmentFromDatabase.setDepartmentCode(updatedDepartment.getDepartmentCode());
//			}
//
//			if(Objects.nonNull(updatedDepartment.getDepartmentPhone()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentPhone()))
//			{
//				departmentFromDatabase.setDepartmentPhone(updatedDepartment.getDepartmentPhone());
//			}
//
//			if(Objects.nonNull(updatedDepartment.getEmail()) && !"".equalsIgnoreCase(updatedDepartment.getEmail()))
//			{
//				departmentFromDatabase.setDepartmentEmail(updatedDepartment.getEmail());
//			}
//
//			//update the address field by field as needed
//			if(Objects.nonNull(updatedDepartment.getCollegeAddress()))
//			{
//				CollegeAddress updatedCollegeAddress = updatedDepartment.getCollegeAddress();
//				CollegeAddress databaseCollegeAddress = departmentFromDatabase.getCollegeAddress();
//
//				if(Objects.nonNull(updatedCollegeAddress.getMainAddress()) && !"".equalsIgnoreCase(updatedCollegeAddress.getMainAddress()))
//				{
//					databaseCollegeAddress.setMainAddress(updatedCollegeAddress.getMainAddress());
//				}
//				if(Objects.nonNull(updatedCollegeAddress.getAdditionalAddress()) && !"".equalsIgnoreCase(updatedCollegeAddress.getAdditionalAddress()))
//				{
//					databaseCollegeAddress.setAdditionalAddress(updatedCollegeAddress.getAdditionalAddress());
//				}
//				if(Objects.nonNull(updatedCollegeAddress.getCity()) && !"".equalsIgnoreCase(updatedCollegeAddress.getCity()))
//				{
//					databaseCollegeAddress.setCity(updatedCollegeAddress.getCity());
//				}
//				if(Objects.nonNull(updatedCollegeAddress.getState()) && !"".equalsIgnoreCase(updatedCollegeAddress.getState()))
//				{
//					databaseCollegeAddress.setState(updatedCollegeAddress.getState());
//				}
//				if(Objects.nonNull(updatedCollegeAddress.getZipCode()) && !"".equalsIgnoreCase(updatedCollegeAddress.getZipCode()))
//				{
//					databaseCollegeAddress.setZipCode(updatedCollegeAddress.getZipCode());
//				}
//				departmentFromDatabase.setCollegeAddress(databaseCollegeAddress);
//			}
//
//			return departmentRepository.save(departmentFromDatabase);
//		}
//		else // POST into database if departmentID is not found
//		{
//			if(Objects.nonNull(updatedDepartment.getDepartmentName()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentName()) &&
//					Objects.nonNull(updatedDepartment.getDepartmentCode()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentCode()) &&
//					Objects.nonNull(updatedDepartment.getDepartmentPhone()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentPhone()) &&
//					Objects.nonNull(updatedDepartment.getDepartmentEmail()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentEmail()) &&
//					)
//			{
//				departmentFromDatabase = new Department();
//				departmentFromDatabase.setDepartmentName(updatedDepartment.getDepartmentName());
//				departmentFromDatabase.setDepartmentCode(updatedDepartment.getDepartmentCode());
//				departmentFromDatabase.setDepartmentPhone(updatedDepartment.getDepartmentPhone());
//				departmentFromDatabase.setDepartmentEmail(updatedDepartment.getDepartmentEmail());
//				departmentFromDatabase.setDepartmentDetails(updatedDepartment.getDepartmentDetails());
//			}
//			else // if some fields are missing then do not post into the database.
//			{
//				throw new UpdateDepartmentException("Cannot put object as some required fields are missing");
//			}
//		}
//		return departmentRepository.save(departmentFromDatabase);
//	}

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
