package org.mdissjava.mdisscore.model.dao.User;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.model.dao.Login.LoginDao;
import org.mdissjava.mdisscore.model.dao.Login.LoginDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Address;
import org.mdissjava.mdisscore.model.pojo.Configuration;
import org.mdissjava.mdisscore.model.pojo.Login;
import org.mdissjava.mdisscore.model.pojo.User;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.mapping.Mapper;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;


public class UserDaoImpl extends BasicDAO<User, ObjectId> implements UserDao {

	LoginDao loginDao;
	
	public UserDaoImpl(Datastore ds) {
		super(ds);
		loginDao = new LoginDaoImpl();
	}
	
	
	private Query<User> queryToFindMe(ObjectId id){
		   return ds.createQuery(User.class).field(Mapper.ID_KEY).equal(id);
	}
	
	@Override
	public void addUser(User user) {
		ds.save(user);
		Login login = new Login(user.getId().toString(), user.getEmail(), user.getPass());
		loginDao.addLoginDetails(login);				
	}

	@Override
	public void deleteUser(User user) {
		ds.delete(user);	
	}
		
	@Override
	public User getUserById( ObjectId id ) {	  
	    return ds.get(User.class, id);
	}
	
	
	@Override
	public void loggedIn(ObjectId id){
	   Date now = new Date();
	   UpdateOperations<User> ops = ds.createUpdateOperations(User.class).set("lastSession", now);
	   ds.update(queryToFindMe(id), ops);
	
	}
		
	@Override
	public void modifyAddress(ObjectId id, Address address){
		   UpdateOperations<User> ops = ds.createUpdateOperations(User.class).set("street", address.getStreet()).
				   						set("city", address.getCity()).set("country", address.getCountry()).
				   						set("state", address.getState()).set("zip",address.getZip());
		   ds.update(queryToFindMe(id), ops);
	}
	
	@Override
	public void modifyConfiguration(ObjectId id, Configuration conf){
		   UpdateOperations<User> ops = ds.createUpdateOperations(User.class).set("showNick", conf.isShowNick()).
				   						set("showName", conf.isShowName()).set("showSurname", conf.isShowSurname()).
				   						set("showEmail", conf.isShowEmail()).set("showBirthdate",conf.isShowBirthdate()).
				   						set("showPhone", conf.isShowPhone()).set("showGender", conf.isShowGender()).
				   						set("showCity", conf.isShowCity()).set("showCountry",conf.isShowCountry()).
				   						set("showAddress", conf.isShowAddress()).set("showZip",conf.isShowZip()).
				   						set("showCameras", conf.isShowCameras());
		   ds.update(queryToFindMe(id), ops);
	}

}
