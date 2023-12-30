package com.vagner.springboot.department.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.vagner.springboot.department.project.entity.Department;

@Repository
/**
 * Repository extends JpaRepository to use its methods, passes the Department and 
 * primary key data type as parameters
 */
public interface DepartmentRepositoryInterface extends JpaRepository<Department, Long>
{
	//if the definition follow convention, no implementation is needed. Else see below for custom queries
	public Department findByDepartmentName(String departmentName); //the naming has to match the entity variable, camel case;
	
	// this method is added in a db with CAR, it will return cAr, Car, CAr, ... as valid results
	public Department findByDepartmentNameIgnoreCase(String departmentName); //enforces case insensitive
	
	//more conventions can be found at 
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
	
	@Query(value = "select DEPARTMENT_NAME from DEPARTMENT where DEPARTMENT.DEPARTMENT_NAME like %?1%", nativeQuery = true)
	public List<String> myQueryForNameLike(String name); //picked a random name for a custom query
}
