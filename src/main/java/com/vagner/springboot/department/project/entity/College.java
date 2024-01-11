package com.vagner.springboot.department.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class College
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // auto generation for the primary key
    @Column(name="college_id") // custom column name to be used as FK for department list.
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
    @OneToMany (cascade = CascadeType.ALL) // cascade the child tables
    @JoinColumn(name="fk_college_id", referencedColumnName = "college_id") //name=alias for reference; referenceColumnName: indicated which col to use as fk in department
    List<Department> departments;
    @OneToOne(cascade = CascadeType.ALL)   // one university has one address (primary) - each dept will have its address.
    //@MapsId
    @JoinColumn(name="fk_college_address") //@join column indicates the entity that has the FK to other entity.
    CollegeAddress collegeAddress;
    @NotNull @ElementCollection(targetClass=String.class) //JPA did not automatically determine the type so indicated via @Element annotation
    List<String> majors; // let's make them unique
}
