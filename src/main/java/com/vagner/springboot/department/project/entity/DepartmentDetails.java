package com.vagner.springboot.department.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long departmentLocationId;

    @NotNull(message = "Please enter department building name")
    String departmentBuilding;

    @NotNull(message = "Please enter department office floor")
    String departmentFloor;

    @NotNull(message = "Please enter the office hours for the department")
    String departmentBusinessHours;
}
