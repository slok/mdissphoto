package org.mdissjava.mdisscore.model.dao.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mdissjava.mdisscore.model.dao.UserDao;
import org.mdissjava.mdisscore.model.dao.hibernate.HibernateUtil;
import org.mdissjava.mdisscore.model.pojo.Address;
import org.mdissjava.mdisscore.model.pojo.Configuration;
import org.mdissjava.mdisscore.model.pojo.User;


public class UserDaoImpl implements UserDao {
	
	public UserDaoImpl() {

	}	
	
	@Override
	public void addUser(User user) {
		if(user != null){
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.save(user);
			tx.commit();
		}
	}

	@Override
	public void deleteUser(User user) {
		if (user != null) {
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.delete(user);
			tx.commit();			
		}
	}
		
	@Override
	public User getUserById( int id ) {	  

		User user = null;
		Session session = HibernateUtil.getSession();

		Query q = session.createQuery("" + "from User as user "
				+ "where user.id =" + id);
		user = (User) q.uniqueResult();

		
		return user;
	
	}
	
	
	@Override
	public void loggedIn(ObjectId id){
	   Date now = new Date();
	   
	
	}
		
	@Override
	public void modifyAddress(ObjectId id, Address address){

	}
	
	@Override
	public void modifyConfiguration(ObjectId id, Configuration conf){

	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> findFriends(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Configuration findConfiguration(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
