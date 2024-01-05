package com.vagner.springboot.department.project.repository;

import com.vagner.springboot.department.project.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepositoryInterface extends JpaRepository<Address, Long>
{

}
