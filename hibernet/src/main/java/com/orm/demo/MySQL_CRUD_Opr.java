package com.orm.demo;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class MySQL_CRUD_Opr {
	private static SessionFactory factory;
	
	public static void main(String[] args) throws Exception {
		
		Configuration configuration  = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		
		factory = configuration.buildSessionFactory();
		
		insert();
		//getProductByIdUsingNativeQuery(1);
		//getVsLoad();
		updateMergeTest();
		factory.close();
	}
	public static void getVsLoad() {
		Session session = factory.openSession();
		//Product product = session.get(Product.class, 1L); //JPA, returns actual entity
		Product product = session.load(Product.class, 1L); //Hibernate, returns proxy entity
		System.out.println("product name: " + product.getName());
		session.close();
	}
	public static Product insert() {
		Session session = factory.openSession();
		Product product = new Product("Chair", 2000);//Transient
		Transaction t = session.beginTransaction();
		
		long pid= (Long)session.save(product);//Managed, Persistent - Hibernate, Returns PK
		//session.persist(product); //JPA, Returns void
		
		t.commit();
		session.close();		
		return product;
	}
	public static void update(long p_id) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		Product product = session.load(Product.class, p_id);
		product.setPrice(3000);
		session.update(product);
		t.commit();
		session.close();
	}
	public static List<Product> getAllProducts() {
		Session session = factory.openSession();
		//Query query = session.createQuery("from Product"); //HQL
		Query query = session.getNamedQuery("getAllProducts"); //Named query
		List<Product> products = query.list();
		for(Product tempProduct: products)
			System.out.println(tempProduct);
		session.close();
		return products;
	}
	public static void delete() {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("delete from Product"); //HQL
		int noOfDeletes = query.executeUpdate();
		t.commit();
		session.close();
	}
	public static List<Product> getProductByIdUsingNativeQuery(long p_id) {
		Session session = factory.openSession();
		Query query = session.createSQLQuery("select * from product_master as pm where pm.p_id=:pid")
				.addEntity(Product.class)
				.setParameter("pid", p_id);
		List<Product> products = query.list();
		return products;
	}
	
	public static void updateMergeTest() {
		Session session = factory.openSession();
		Product product = session.get(Product.class, 1L); //Managed
		session.close();
		product.setName("Laptop"); //Detached
		
		session = factory.openSession();
		Transaction t = session.beginTransaction();
		Product product_2 = session.get(Product.class, 1L); //Managed
		product_2.setPrice(4000);
		//session.update(product);
		session.merge(product); //Managed
		t.commit();
		session.close();
	}
}