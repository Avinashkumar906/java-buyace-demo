package com.buyace.core.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.buyace.core.beans.Customer;
import com.buyace.core.hibernate.util.HibernateUtil;

public class CustomerDao {
	
	public static int register(Customer user){
		if(existingUser(user) == null) {
			int i = 0;
			SessionFactory factory = HibernateUtil.getSessionFactory();
			System.out.println(user);
			Session session = factory.openSession();
//			user.setRole("admin");
			session.beginTransaction();
			i=(Integer)session.save(user);
			session.getTransaction().commit();
			session.close();
			return i;
		}
		else
			return 0;
	}
	public static Customer existingUser(Customer user) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();

		String email = user.getEmail();
		String password = user.getPassword();

		session.beginTransaction();
		List<Customer> rs = session.createQuery("from Customer where usermail='"+email+"'").list();
		session.getTransaction().commit();
		session.close();
		if(rs.size() != 0)
		{
			return rs.get(0);
		}
		return null;
	}

	public static Customer validateUser(Customer user) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		
		String email = user.getEmail();
		String password = user.getPassword();
		
		session.beginTransaction();
		List<Customer> rs = session.createQuery("from Customer where usermail='"+email+"'and userpass='"+password+"'").list();
		session.getTransaction().commit();
		session.close();
		if(rs.size() != 0)
		{
			return rs.get(0);
		}
		return null;
	}
	
	public static List<Customer> fetchUser(){
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		
		session.beginTransaction();
		List<Customer> rs = session.createQuery("from Customer").list();
		session.getTransaction().commit();
		session.close();
		if(rs.size() != 0)
		{
			return rs;
		}
		return rs;
	}
	public static Customer getCustomer(int userId){
		List<Customer> rs = fetchUser();
		Iterator<Customer> itr = rs.iterator();
		while(itr.hasNext()){
			Customer customer = itr.next();
			if(customer.getUserid()==userId)
				return customer;
		}
		return new Customer();
	}
	public static List<Customer> fetchAdmin(){
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		
		session.beginTransaction();
		List<Customer> rs = session.createQuery("from Customer where role='User'").list();
		session.getTransaction().commit();
		session.close();
		if(rs.size() != 0)
		{
			return rs;
		}
		return rs;
	}
	
	public static void removeCustomer(int userId){
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Customer customer = getCustomer(userId);
		session.beginTransaction();
		session.delete(customer);
		session.getTransaction().commit();
		session.close();
	}

	public static void makeAdmin(int userId) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Customer customer = getCustomer(userId);
		customer.setRole("admin");
		session.beginTransaction();
		session.update(customer);
		session.getTransaction().commit();
		session.close();
	}
}
