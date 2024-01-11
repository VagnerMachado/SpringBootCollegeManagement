package com.vagner.springboot.department.project.repository;

import com.vagner.springboot.department.project.entity.CollegeAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepositoryInterface extends JpaRepository<CollegeAddress, Long>
{

}
