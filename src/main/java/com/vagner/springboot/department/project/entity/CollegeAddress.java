package com.vagner.springboot.department.project.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollegeAddress
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long collegeAddressId;
    @NotBlank(message = "Please add main address")
    private String collegeAddress;
    private String additionalAddress;
    @NotBlank(message = "Please add city to address")
    String city;
    @NotBlank(message = "Please add state to address with 2 chars")
    String state;
    @Length(max=5, min=5, message="Zip code has to be length 5")
    private String zipCode;

    @OneToOne(mappedBy = "collegeAddress") //indicated the field in owning entity : aka College owns College Address
    private College college;
}
