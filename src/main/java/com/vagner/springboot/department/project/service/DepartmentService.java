package com.vagner.springboot.department.project.service;

import com.vagner.springboot.department.project.entity.College;
import com.vagner.springboot.department.project.entity.Department;
import com.vagner.springboot.department.project.entity.DepartmentDetails;
import com.vagner.springboot.department.project.error.college.CollegeNotFoundException;
import com.vagner.springboot.department.project.error.department.DeleteDepartmentException;
import com.vagner.springboot.department.project.error.department.DepartmentNotFoundException;
import com.vagner.springboot.department.project.error.department.NoDepartmentWithProvidedNameException;
import com.vagner.springboot.department.project.error.department.UpdateDepartmentException;
import com.vagner.springboot.department.project.repository.CollegeRepositoryInterface;
import com.vagner.springboot.department.project.repository.DepartmentRepositoryInterface;
import com.vagner.springboot.department.project.utils.CollegeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
		List<Department> departmentsFromDatabase = collegeFromDatabase.getDepartments();
		departmentsFromDatabase.add(department);
		collegeFromDatabase.setDepartments(departmentsFromDatabase);
		CollegeUtils.validateDepartmentCodes(collegeFromDatabase); //validate that the list of dept has no dup code
		return collegeRepository.save(collegeFromDatabase);
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

	public College updateDepartmentByID(Department updatedDepartment, long collegeId, long departmentId) throws UpdateDepartmentException, CollegeNotFoundException {
		// TODO: GET THE COLLEGE TO NARROW DOWN DEPT LIST.
		Optional<College> optCollege = collegeRepository.findById(collegeId);
		if(!optCollege.isPresent())
		{
			throw new CollegeNotFoundException("Cannot PUT department, collegeI does not exist");
		}
		College collegeFromDatabase = optCollege.get();
		Department departmentFromDatabase = null;
		for(Department d : collegeFromDatabase.getDepartments())
		{
			if(d.getDepartmentId() == departmentId)
			{
				departmentFromDatabase = d;
				break;
			}
		}

		if(departmentFromDatabase == null)
		{
				throw new UpdateDepartmentException("Cannot PUT department, departmentId does not exist");
		}

		// update block
		//Department departmentFromDatabase = departmentFromDatabase = optDept.get();
			if(Objects.nonNull(updatedDepartment.getDepartmentName()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentName()))
			{
				departmentFromDatabase.setDepartmentName(updatedDepartment.getDepartmentName());
			}
		if(Objects.nonNull(updatedDepartment.getDepartmentCode()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentCode()))
			{
				departmentFromDatabase.setDepartmentCode(updatedDepartment.getDepartmentCode());
			}
		if(Objects.nonNull(updatedDepartment.getDepartmentPhone()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentPhone()))
			{
				departmentFromDatabase.setDepartmentPhone(updatedDepartment.getDepartmentPhone());
			}
		if(Objects.nonNull(updatedDepartment.getDepartmentEmail()) && !"".equalsIgnoreCase(updatedDepartment.getDepartmentEmail()))
			{
				departmentFromDatabase.setDepartmentEmail(updatedDepartment.getDepartmentEmail());
			}
		if(Objects.nonNull(updatedDepartment.getDepartmentDetails()))
		{
			DepartmentDetails departmentDetailsFromDatabase = departmentFromDatabase.getDepartmentDetails();
			DepartmentDetails departmentDetailsUpdated = updatedDepartment.getDepartmentDetails();

			if(Objects.nonNull(departmentDetailsUpdated.getDepartmentBuilding()) && !"".equalsIgnoreCase(departmentDetailsUpdated.getDepartmentBuilding()))
			{
				departmentDetailsFromDatabase.setDepartmentBuilding(departmentDetailsUpdated.getDepartmentBuilding());
			}
			if(Objects.nonNull(departmentDetailsUpdated.getDepartmentFloor()) && !"".equalsIgnoreCase(departmentDetailsUpdated.getDepartmentFloor()))
			{
				departmentDetailsFromDatabase.setDepartmentFloor(departmentDetailsUpdated.getDepartmentFloor());
			}
			if(Objects.nonNull(departmentDetailsUpdated.getDepartmentBusinessHours()) && !"".equalsIgnoreCase(departmentDetailsUpdated.getDepartmentBusinessHours()))
			{
				departmentDetailsFromDatabase.setDepartmentBusinessHours(departmentDetailsUpdated.getDepartmentBusinessHours());
			}
			departmentFromDatabase.setDepartmentDetails(departmentDetailsFromDatabase);
		}
			return collegeRepository.save(collegeFromDatabase);
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
