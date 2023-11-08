package com.orm.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee {

	@Id
	@GeneratedValue
	@Column(name = "e_id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "salary")
	private int salary;
	
	@Column(name = "city")
	private String location;

	public Employee() {}
	
	public Employee( String name, int salary, String location) {
		super();
		this.name = name;
		this.salary = salary;
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", location=" + location + "]";
	}
	
	
	
}