package org.mdissjava.mdisscore.model.dao.Login;


import org.bson.types.ObjectId;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mdissjava.mdisscore.model.pojo.Login;
import org.mdissjava.mdisscore.model.pojo.User;

import com.mdiss.hibernate.HibernateUtil;


public class LoginDao implements ILoginDao {

	@Override
	public User login() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addLoginDetails(Login login) {
		if(login != null){
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.save(login);
			tx.commit();
		}
		
	}

	@Override
	public void modifyLoginDetails(ObjectId id, String email, String pass) {
		// TODO Auto-generated method stub		
	}
	
	@Override
	public void removeLoginDetails(ObjectId id) {
		// TODO Auto-generated method stub		
	}

}
