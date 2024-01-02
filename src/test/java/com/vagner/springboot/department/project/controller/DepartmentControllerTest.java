package com.vagner.springboot.department.project.controller;

import com.vagner.springboot.department.project.entity.Department;
import com.vagner.springboot.department.project.service.DepartmentService;
import com.vagner.springboot.department.project.service.DepartmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentServiceImpl departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = Department.builder()
                .departmentAddress("Ahmedabad")
                .departmentCode("IT-06")
                .departmentName("IT")
                .departmentID(1L)
                .build();
    }

    @Test
    void saveDepartment() throws Exception {
        Department inputDepartment = Department.builder()
                .departmentAddress("Ahmedabad")
                .departmentCode("IT-06")
                .departmentName("IT")
                .build();

        Mockito.when(departmentService.saveDepartment(inputDepartment))
                .thenReturn(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/departments")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"departmentName\":  \"math\",\r\n" +
						"	  \"departmentAddress\": \"Everywhere\",\r\n" + 
						"		\"departmentCode\":\"CEW 3\"}"))
				.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void fetchDepartmentById() throws Exception {
        Mockito.when(departmentService.getDepartmentByID(1L))
                .thenReturn(department);

        mockMvc.perform(MockMvcRequestBuilders.get("/departments/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.departmentName").
                value(department.getDepartmentName()));
    }
}