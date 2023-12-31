package com.orm.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT")
@NamedQueries(
		{
			@NamedQuery(name="GET_ALL_PRODUCTS", query = "from Product p"),
			@NamedQuery(name="DELETE_PRODUCT", query = "from Product p")
		}

		)
public class Product {

	@Id
	@GeneratedValue
	@Column(name = "p_id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "cost")
	private double price;

	public Product() {
	}

	public Product(String name, double price) {
		this.name = name;
		this.price = price;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String toString() {
		return id + " - " + name + " - " + price;
	}
}