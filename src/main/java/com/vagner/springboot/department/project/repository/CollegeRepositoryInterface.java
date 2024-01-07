package com.vagner.springboot.department.project.repository;

import com.vagner.springboot.department.project.entity.Address;
import com.vagner.springboot.department.project.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepositoryInterface extends JpaRepository<College, Long>
{

}
