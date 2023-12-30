package com.vagner.springboot.department.project.service;

import com.vagner.springboot.department.project.entity.Department;
import com.vagner.springboot.department.project.error.NoDepartmentWithProvidedNameException;
import com.vagner.springboot.department.project.repository.DepartmentRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DepartmentServiceTest {

    @Autowired
    private DepartmentServiceInterface departmentService;

    @MockBean
    private DepartmentRepositoryInterface departmentRepository;

    @BeforeEach
    void setUp() {
        Department department =
                Department.builder()
                .departmentName("IT")
                .departmentAddress("Ahmedabad")
                .departmentCode("IT-06")
                .departmentID(1L)
                .build();

        Mockito.when(departmentRepository.findByDepartmentNameIgnoreCase("IT"))
                .thenReturn(department);

    }

    @Test
    @DisplayName("Get Data based on Valid Department Name")
    public void whenValidDepartmentName_thenDepartmentShouldFound() throws NoDepartmentWithProvidedNameException {
        String departmentName = "IT";
        Department found = departmentService.getDepartmentByName(departmentName);

        assertEquals(departmentName, found.getDepartmentName());
    }
}