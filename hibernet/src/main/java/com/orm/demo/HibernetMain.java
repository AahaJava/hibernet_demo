package com.orm.demo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

public class HibernetMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = configuration.buildSessionFactory(); // Heavy Operation

		// productOperation(sessionFactory);

		employeeOperation(sessionFactory);

	}

	private static void employeeOperation(SessionFactory sessionFactory) {

//		Employee employee = new Employee("Arif", 3000, "Ghaziabad"); // Transient Object State
//		Session session = sessionFactory.openSession();
//		Transaction transaction = session.beginTransaction();
//		long emplyeeId = (Long) session.save(employee); //Managed or Persistence Object State
//		transaction.commit();
//		session.close();
//		System.out.println("Product Inserted...");
//		

		// hqlAssignment(sessionFactory);
		criteriaAssignment(sessionFactory);
	}

	private static void criteriaAssignment(SessionFactory sessionFactory) {

		Session session = sessionFactory.openSession();
		Criteria cr = session.createCriteria(Employee.class);

		// A.
		cr.setProjection(Projections.sum("salary"));
		System.out.println("SUM: " + cr.list().get(0));

		// B.
		Criteria crOrder = session.createCriteria(Employee.class);
		crOrder.addOrder(Order.desc("salary"));
		System.out.println("Order: " + crOrder.list());

		// B.
		Criteria crGroup = session.createCriteria(Employee.class);
		crGroup.add(Restrictions.like("location", "Ghaziabad"));
		System.out.println("Group: " + crGroup.list());
		//crGroup.setProjection(Projections.projectionList().add( Projections.groupProperty("location")));

		// D.
		cr.setProjection(Projections.avg("salary"));
		System.out.println("Average: " + cr.list().get(0));

		// E.
		Criteria crGreater = session.createCriteria(Employee.class);
		crGreater.add(Restrictions.gt("salary", 10000));
		System.out.println("Greater: " + crGreater.list());
	}

	private static void hqlAssignment(SessionFactory sessionFactory) {

		// A
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("Select sum(emp.salary) FROM Employee emp");/// HQL
		System.out.println("SUM: " + query.list().get(0));
		session.close();

		// B.
		session = sessionFactory.openSession();
		query = session.createQuery("FROM Employee E ORDER BY E.salary ASC");/// HQL
		System.out.println("Order: " + query.list());
		session.close();

		// C.
		session = sessionFactory.openSession();
		Query qry = session
				.createQuery("SELECT E.name FROM Employee E where E.location like '%Ghaziabad%' GROUP BY E.name");/// HQL
		List<Object[]> employees = qry.list();
		System.out.println("Employees in Ghaziabad :" + employees);
		session.close();

		// D.
		session = sessionFactory.openSession();
		query = session.createQuery("Select avg(emp.salary) FROM Employee emp");/// HQL
		System.out.println("Average: " + query.list().get(0));
		session.close();

		// E.
		session = sessionFactory.openSession();
		query = session.createQuery("FROM Employee E WHERE E.salary > 10000 ");/// HQL
		System.out.println("Greater: " + query.list());
		session.close();

	}

	private static void productOperation(SessionFactory sessionFactory) {
		// INSERTION
		Product product = new Product("Chair", 1000); // Transient Object State
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		long productId = (Long) session.save(product); // Managed or Persistence Object State
		transaction.commit();
		product.setPrice(100); // Will be reflect in DB
		session.close();
		// product.setPrice(100); //Detach
		System.out.println("Product Inserted...");

		// UPDATE
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		product = session.load(Product.class, productId);
		product.setPrice(1010);
		transaction.commit();
		session.close();
		System.out.println("Product Updated...");

		// READ
		session = sessionFactory.openSession();
		// Query query = session.createQuery("from Product p");///HQL
		Query query = session.getNamedQuery("GET_ALL_PRODUCTS");
		List<Product> products = query.list();

		for (Product product2 : products) {
			System.out.println(product2);
		}
		session.close();
		System.out.println("Product Read...");

		// DELETE
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		product = session.load(Product.class, productId);
		session.delete(product);
		transaction.commit();
		session.close();
		System.out.println("Product Deleted...");

		sessionFactory.close();

	}

}
