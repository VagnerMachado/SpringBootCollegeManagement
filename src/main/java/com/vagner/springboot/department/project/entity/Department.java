package com.vagner.springboot.department.project.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // So the Department can interact with DB repository

//    ****   LOMBOK PLUGIN  *****
@Data // Lombok gives getter and setters, hascode and tostring, constructor
//@Getter // Lombol if only getter
//@Setter //Lombok if only setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // this indicates lombok is used to reduced boiler plate code. Tags above
			// determine what lombok builds.
public class Department {
	@Id // indicated departmentID is primary key
	@GeneratedValue(strategy = GenerationType.AUTO) // auto generation for the primary key
	private long departmentID;
	@NotBlank(message = "please add deparment name") // from validation dependency in pom
	/*******
	 * VALIDATIONS *********
	 * 
	 * @Length(max = 5, min= 3) //for strings
	 * @Size(max = 10, min = 3) //for objects, arrays, strings
	 * @Email //well formed email address
	 * @Positive //for number
	 * @Negative //for number
	 * @PositiveOrZero
	 * @Negative
	 * @FutureOrPresent
	 * @PastOrPresent
	 */
	private String departmentName;
	private String departmentAddress;
	private String departmentCode;

	/*
	 * Taken care by lombok public Department() { super(); } public Department(long
	 * departmentID, String departmentName, String departmentAddress, String
	 * departmentCode) { this.departmentID = departmentID; this.departmentName =
	 * departmentName; this.departmentAddress = departmentAddress;
	 * this.departmentCode = departmentCode; }
	 * 
	 * public long getDepartmentID() { return departmentID; }
	 * 
	 * public void setDepartmentID(long departmentID) { this.departmentID =
	 * departmentID; } public String getDepartmentName() { return departmentName; }
	 * public void setDepartmentName(String departmentName) { this.departmentName =
	 * departmentName; } public String getDepartmentAddress() { return
	 * departmentAddress; } public void setDepartmentAddress(String
	 * departmentAddress) { this.departmentAddress = departmentAddress; } public
	 * String getDepartmentCode() { return departmentCode; } public void
	 * setDepartmentCode(String departmentCode) { this.departmentCode =
	 * departmentCode; }
	 * 
	 * @Override public String toString() { return "Department [departmentID=" +
	 * departmentID + ", departmentName=" + departmentName + ", departmentAddress="
	 * + departmentAddress + ", departmentCode=" + departmentCode + "]"; }
	 * 
	 * 
	 */

}
