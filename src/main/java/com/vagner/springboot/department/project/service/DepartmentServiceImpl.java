package com.vagner.springboot.department.project.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import com.vagner.springboot.department.project.repository.DepartmentRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
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
		return ResponseEntity.ok("Deletion Successful");
		}
		throw new DeleteDepartmentException("Department Not Found, cannot delete.");
	}

	@Override
	public Department updateDepartmentByID(Department dept, Long id) 
	{
		Optional<Department> opt = departmentRepository.findById(id);
		Department depDB = null;
		//update existing
		if(opt.isPresent())
		{
			depDB = opt.get();
			if(Objects.nonNull(dept.getDepartmentName()) && !"".equalsIgnoreCase(dept.getDepartmentName()))
			{
				depDB.setDepartmentName(dept.getDepartmentName());
			}

			if(Objects.nonNull(dept.getDepartmentCode()) && !"".equalsIgnoreCase(dept.getDepartmentCode()))
			{
				depDB.setDepartmentCode(dept.getDepartmentCode());
			}

			if(Objects.nonNull(dept.getDepartmentAddress()) && !"".equalsIgnoreCase(dept.getDepartmentAddress()))
			{
				depDB.setDepartmentAddress(dept.getDepartmentAddress());
			}
			return departmentRepository.save(depDB);
		}
		else // test if all valid
		{
			if(Objects.nonNull(dept.getDepartmentName()) && !"".equalsIgnoreCase(dept.getDepartmentName()) &&
					Objects.nonNull(dept.getDepartmentCode()) && !"".equalsIgnoreCase(dept.getDepartmentCode()) &&
					Objects.nonNull(dept.getDepartmentAddress()) && !"".equalsIgnoreCase(dept.getDepartmentAddress()))
			{
				depDB = new Department();
				depDB.setDepartmentName(dept.getDepartmentName());
				depDB.setDepartmentCode(dept.getDepartmentCode());
				depDB.setDepartmentAddress(dept.getDepartmentAddress());
			}
		}
		return departmentRepository.save(depDB);
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
		if(obj == null)
			throw new NoDepartmentWithProvidedNameException("Failed to retrieve Departments with name similar to " + name);
		return obj; //will leave as no error if none present
	}

}
