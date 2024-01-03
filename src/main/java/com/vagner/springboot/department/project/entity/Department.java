package com.vagner.springboot.department.project.entity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity // So the Department can interact with DB repository
@Data // Lombok gives getter and setters, hascode and tostring, constructor
@Getter // Lombok if only getter
@Setter // Lombok if only setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // this indicates lombok is used to reduced boiler plate code for constructor..
public class Department
{
	@Id // indicated departmentID is primary key
	@GeneratedValue(strategy = GenerationType.AUTO) // auto generation for the primary key
	private Long departmentId;
	@NotBlank(message = "Please add department name") // from validation dependency in pom
	private String departmentName;
	@Length(max=5, min=5, message="Department code has to be length 5")
	private String departmentCode;
	@Length(max=12, min=12, message="Phone w 12 chars in format 123-45-6789")
	private String phone;
	@NotBlank @Email
	String email;

	@OneToOne(cascade = CascadeType.ALL) // Indicates a one-to-one relationship between department and address
	@MapsId // Indicates that the relationship share the same id
	Address address;

	/*****************************
	 * VALIDATIONS
	 *
	 * @Length(max = 5, min= 3) //for strings
	 * @Size(max = 10, min = 3) //for objects, arrays, strings
	 * @NotNull validates that the annotated property value isn’t null.
	 * @AssertTrue validates that the annotated property value is true.
	 * @Size validates that the annotated property value has a size between the attributes min and max. We can apply it to String, Collection, Map, and array properties.
	 * @Min validates that the annotated property has a value no smaller than the value attribute.
	 * @Max validates that the annotated property has a value no larger than the value attribute.
	 * @Email validates that the annotated property is a valid email address.
	 * @NotEmpty validates that the property isn’t null or empty. We can apply it to String, Collection, Map or Array values.
	 * @NotBlank can be applied only to text values, and validates that the property isn’t null or whitespace.
	 * @Positive and @PositiveOrZero apply to numeric values, and validate that they’re strictly positive, or positive including 0.
	 * @Negative and @NegativeOrZero apply to numeric values, and validate that they’re strictly negative, or negative including 0.
	 * @Past and @PastOrPresent validate that a date value is in the past, or the past including the present. We can apply it to date types, including those added in Java 8.
	 * @Future and @FutureOrPresent validate that a date value is in the future, or in the future including the present.
	 ******************************/
}
