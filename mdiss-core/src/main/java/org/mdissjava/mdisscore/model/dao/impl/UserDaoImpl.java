package org.mdissjava.mdisscore.model.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mdissjava.mdisscore.model.dao.UserDao;
import org.mdissjava.mdisscore.model.dao.hibernate.HibernateUtil;
import org.mdissjava.mdisscore.model.pojo.User;


public class UserDaoImpl implements UserDao {

	
	public UserDaoImpl() {
		
	}	
	
	@Override
	public void addUser(User user) {
		if(user != null){
			System.out.println("DAo // addUser*************");
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.save(user);
			tx.commit();
			//session.close();
		}
	}


	@Override
	public void updateUser(User user) {
		
		if (user != null) {
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(user);
			tx.commit();
		//	session.close();
		}		
	}
	
	@Override
	public void deleteUser(User user) {
		if (user != null) {
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.delete(user);
			tx.commit();
		//	session.close();
		}
	}
	
	@Override
	public boolean emailAlreadyExists(String email) {
		Session session = HibernateUtil.getSession();

		int num = (Integer) session.createQuery("from User where email = '"+email+"'").list().size();
	//	session.close();
		if(num>0)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean nickAlreadyExists(String nick) {
		Session session = HibernateUtil.getSession();

		int num = (Integer) session.createQuery("from User where nick = '"+nick+"'").list().size();
	//	session.close();
		if(num>0)
			return true;
		else
			return false;
	}
	
		
	@Override
	public User getUserById( int id ) {	  
		User user = null;
		Session session = HibernateUtil.getSession();
		Query q = session.createQuery("" + "from User as user "
				+ "where user.id =" + id);
		user = (User) q.uniqueResult();
	//	session.close();		
		return user;	
	}
	
	@Override
	public User getUserByNick(String nick) {		
		User user = null;
	Session session = HibernateUtil.getSession();
		
		Query q = session.createQuery("" + "from User as user where user.nick =" + "'" + nick + "'");
		user = (User) q.uniqueResult();
	//	session.close();
	//	System.out.println("getUserByNick: insert "+nick +" con Nick recuperado: "+user.getNick());
		return user;
	}
	
	
	@Override
	public void loggedIn(ObjectId id){
	   Date now = new Date();
	  	
	}
		

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findFollows(User user) {
		List<User> users = new ArrayList<User>();
		Session session = HibernateUtil.getSession();

		Query q = session.createQuery("Select follows from User as user "
				+ " where user.id =" + user.getId());
								
		users =  q.list();
		//	session.close();
		
		// user.getFollows();	
		return users;
	}

	@Override
	public void addFollow(int userid, int friendid) {
		//TODO: 
	}

	@Override
	public void deleteFollow(int userid, int friendid) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public List<User> findFollowers(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFollower(int userid, int friendid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFollower(int userid, int friendid) {
		// TODO Auto-generated method stub
		
	}



}
