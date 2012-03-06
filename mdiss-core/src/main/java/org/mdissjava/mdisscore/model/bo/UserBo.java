package org.mdissjava.mdisscore.model.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mdissjava.mdisscore.model.bll.UserBll;
import org.mdissjava.mdisscore.model.bll.impl.UserBllImpl;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.model.pojo.User.Gender;


public class UserBo {

	private UserBll userBll= new UserBllImpl();
	
	
	private String id="";	
	private String nick;
	private String name;	
	private String surname;	
	private Date birthdate;	
	private int phone;
	private int avatar;
	private Date registeredDate;	
	private boolean active;
	private Date lastSession;
	private String role;
	private List<String> preferences=new ArrayList<String>();		
	private Gender gender;		
	private String email;
	private String pass;
	private List<UserBo> friends;	
	private AddressBo address; 
	private ConfigurationBo configuration;
	private List<AlbumBo> albums;
	
	public UserBo(){}
	
	public UserBo(User user)
	{
		this.id=user.getId().toString();
		this.nick=user.getNick();
		this.name=user.getName();	
		this.surname=user.getNick();	
		this.birthdate=user.getBirthdate();	
		this.phone=user.getPhone();
		this.avatar=user.getAvatar();
		this.registeredDate=user.getRegisteredDate();	
		this.active=user.isActive();
		this.lastSession=user.getLastSession();
		this.role=user.getRole();
		this.preferences=user.getPreferences();		
		this.gender=user.getGender();	
		this.email=user.getEmail();
		this.pass=user.getPass();
		this.address=new AddressBo(user.getAddress()); 
		this.configuration=new ConfigurationBo(user.getConfiguration());	

	//			this.friends=user.getFriends();
			
			
	}

	//clase get and set, menos id , que solo es get
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public int getAvatarId()
	{return avatar;}
	
	public PhotoBo getAvatarPhoto() {
		return null;		
//		return UserBll.getPhoto(avatar);
	}
	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}
	public Date getRegisteredDate() {
		return registeredDate;
	}

	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Date getLastSession() {
		return lastSession;
	}
	public void setLastSession(Date lastSession) {
		this.lastSession = lastSession;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<String> getPreferences() {
		return preferences;
	}
	
	public void addPreference(String preferencia)
	{
		this.preferences.add(preferencia);
	}
	
	public void setPreferences(List<String> preferences) {
		this.preferences = preferences;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public List<UserBo> getFriends() {
		
		if(friends.size()==0)
			friends=userBll.getFriends(this.id);
		return friends;
	}
	
	public void addFriend(UserBo user)
	{
		this.friends.add(user);
	}
	
	public void setFriends(List<UserBo> friends) {
		this.friends = friends;
	}
	public AddressBo getAddress() {
		return address;
	}
	public void setAddress(AddressBo address) {
		this.address = address;
	}
	public ConfigurationBo getConfiguration() {
		return configuration;
	}
	public void setConfiguration(ConfigurationBo configuration) {
		this.configuration = configuration;
	}
	public String getId() {
		return id;
	}

	
	
	/**Save the user changes or create a new user if not exists*/
	
	public void Save()
	{
		String code;
		code=userBll.saveUser(this);
		if(!code.isEmpty())
			this.id=code;
	
	}
	/**change the active mode and other things**/
	public void Delete()
	{}
	
	/**Save the data of the logout**/
	public void CloseSession()
	{}
	
	/**equals method for all variable data**/
	public boolean Equals(UserBo user)
	{

		if (!id.equals(user.id)) return false;
        if (!nick.equals(user.nick)) return false;
        if (!name.equals(user.name)) return false;
        if (!surname.equals(user.surname)) return false;
        if (!birthdate.equals(user.birthdate)) return false;
        if (phone != user.phone) return false;
        if (avatar!=user.avatar) return false;
		if (!role.equals(user.role))return false;
		if (!preferences.equals(user.preferences))return false;
		if (!gender.equals(user.gender))return false;
		if (!email.equals(user.email))return false;
		if (!address.equals(user.address))return false;
		if (!configuration.equals(user.configuration))return false;		
		return true;
	}
	
	
	
	
}
