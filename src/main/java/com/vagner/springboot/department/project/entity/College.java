package com.vagner.springboot.department.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class College
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // auto generation for the primary key
    private Long collegeId;
    @NotBlank(message = "Please add college name") // from validation dependency in pom
    private String collegeName;
    @Length(max=12, min=12, message="Phone with 12 chars in format 123-45-6789")
    private String phone;
    @NotBlank @Email(message="Enter a valid email for the college")
    String email;
    @NotNull(message="Enter the president name")
    String president;
    //NOTE - Cascading is needed to save cascading dependencies / tables
    @OneToMany (mappedBy = "departmentId", //the id that will have many values
                orphanRemoval = true,
                cascade = CascadeType.ALL) // cascade the child tables
    List<Department> departments;
    @OneToOne(cascade = CascadeType.ALL) // one university has one address (primary) - each dept will have its address.
    @MapsId
    Address address; //say a college will have a main address.
    @NotNull  @ElementCollection(targetClass=String.class) //JPA did not automatically determine the type so indicated via @Element annotation
    List<String> majors;
}
