[//]: # (<p align="center"> ![image]&#40;https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.AuII18L1hrDgBpr-x8ioCQHaFj%26pid%3DApi&f=1&ipt=a9a01d007527478921ce95216907e053b43a76f7c51f474eb9dd9953681c20d9&ipo=images&#41; </p>)

# READ ME  

--- 

<div style="text-align:center"><img src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.AuII18L1hrDgBpr-x8ioCQHaFj%26pid%3DApi&f=1&ipt=a9a01d007527478921ce95216907e053b43a76f7c51f474eb9dd9953681c20d9&ipo=images"/></div>

# Project Notes

---
Please read the comments in the controller endpoints for usage. 

This project explores creating a Spring Boot API to manage colleges and its departments, and in the process, enrich the application by
exploring spring's various annotations, hibernate, jpa, exception handling, different types of data persistence with h2
database and other topics, one to many and one to one via annotations, etc.

As this was coded in offline mode during commute, it was set to use `h2 database` for developments and one can customize
that easily in configurations located at `application.properties`

With the above, some data persistence is still achieved as h2 is set to save the data to file in properties file aforementioned.
A few notes below for reference for memory vs file based `h2 ddatabase`

- When line below is set in configuration file, data is lost once app is restarted  
`
spring.datasource.url=jdbc:h2:mem:dept
`
- When the below lines are set in configuration, data is persisted in file as per ddl-auto value.
`
spring.datasource.url=jdbc:h2:file:~/dept
spring.jpa.hibernate.ddl-auto=update
`
- In addition to `update` you can set to `create, none, create-drop, validate` and details for each can be found when 
scrolling through this post [here](https://stackoverflow.com/questions/42135114/how-does-spring-jpa-hibernate-ddl-auto-property-exactly-work-in-spring)
- While developing I am keeping at `create` so earlier constraints are not applied to a changed schema. 

Upon running the application one can call the APIs via the Postman collection to be added to the root of the app once
it is done. Additionally, one can use the swagger ui and api docs in provided links below.

### Links for the project

- Once the application is running you can access the h2 console here
  - http://localhost:8080/h2-console/ 
- Swagger UI that can be used in place of Postman.
  - http://localhost:8080/swagger-ui/index.html
- Once running, access API docs in this link.
  - http://localhost:8080/v3/api-docs 
- Useful to convert api docs to yaml
  - https://json2yaml.com/ 

### Spring JPA / Hibernate

Adding the @ManyToOne annotation to College in order to track a College having many Departments took a bit of reading
and testing the value for ` mappedBy = "departmentId"` in the `College` `@Entity`. The annotation tells JPA that the 
one to many relation is mapped by the `departmentId` in the child entity. 
Relevant comments for the annotations can be found in the `@Entity` classes.   
Relevant links:

* [Discussion in Stack overflow](https://stackoverflow.com/questions/48310688/error-annotationexception-mappedby-reference-an-unknown-target-entity-property)
* [Baeldung Article](https://www.baeldung.com/hibernate-one-to-many)
* [BezKoder Article and samples](https://www.bezkoder.com/jpa-manytoone/) 
* [@ElementCollection annotation forum](https://stackoverflow.com/questions/3774198/org-hibernate-mappingexception-could-not-determine-type-for-java-util-list-at)

Faced some issues when implementing the relations @OneToOne and @OneToMany and adding proper annotations.
These videos were helpful in clarifying the usage [One to One and One to Many](https://www.youtube.com/watch?v=2fvUrpYG95w&t=589)
and [Many to Many](https://www.youtube.com/watch?v=v40e5SOMftQ) and example below.

One-to-One: A one-to-one relationship is a relationship between two entities where one entity has exactly one instance 
of another entity. For example, a Person entity can have only one Address entity. Here’s an example of a one-to-one 
relationship in JPA:

``` java
  @Entity
  public class Person {
  @Id
  private Long id;
  private String name;
  @OneToOne
  private Address address;
  // additional properties
  // standard constructors, getters, and setters
  }
  
  @Entity
  public class Address {
  @Id
  private Long id;
  private String street;
  private String city;
  private String state;
  private String zipCode;
  // additional properties
  // standard constructors, getters, and setters
  }
```

One-to-Many: A one-to-many relationship is a relationship between two entities where one entity can have many 
instances of another entity. For example, a Department entity can have
many Employee entities. Here’s an example of a one-to-many relationship
in JPA:

``` java
@Entity
public class Department {
@Id
private Long id;
private String name;
@OneToMany(mappedBy = "department")
private Set<Employee> employees;
// additional properties
// standard constructors, getters, and setters
}

@Entity
public class Employee {
@Id
private Long id;
private String name;
@ManyToOne
private Department department;
// additional properties
// standard constructors, getters, and setters
}
```

Many-to-Many: A many-to-many relationship is a relationship between two entities where one entity can have many
instances of another entity, and vice versa. For example, a Student entity can take many Course entities, and a Course
entity can be taken by many Student entities. Here’s an example of a many-to-many relationship in JPA:

```
@Entity
public class Student {
@Id
private Long id;
private String name;
@ManyToMany
private Set<Course> courses;
// additional properties
// standard constructors, getters, and setters
}

@Entity
public class Course {
@Id
private Long id;
private String name;
@ManyToMany(mappedBy = "courses")
private Set<Student> students;
// additional properties
// standard constructors, getters, and setters
}
```
---

# Further exploration

---

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.1/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.1/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

