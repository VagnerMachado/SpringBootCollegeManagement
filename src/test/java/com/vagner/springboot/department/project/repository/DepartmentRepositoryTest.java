package com.vagner.springboot.department.project.repository;

import com.vagner.springboot.department.project.entity.Department;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


@DataJpaTest // To use test data
public class DepartmentRepositoryTest 
{
	@Autowired
	private DepartmentRepositoryInterface departmentRepository;
	
	@Autowired
	private TestEntityManager entityManager;  // to test the entity
	
	@BeforeEach
	void setUp()
	{
		Department dept = Department.builder() //uses the loomok build
				.departmentAddress("New York")
				.departmentCode("someCode")
				//.departmentID(32)
				.departmentName("Engineering")
				.build();
		
		entityManager.persist(dept); //sets this data to persist for testing
				
	}
	@Test
	@DisplayName("Get Valid Department by ID")
	public void whenFindDepartmentByID_returnsDepartment()
	{
		Department dept= departmentRepository.findById(1L).get();
		assertEquals(dept.getDepartmentName(), "Engineering");
	}
	
}
