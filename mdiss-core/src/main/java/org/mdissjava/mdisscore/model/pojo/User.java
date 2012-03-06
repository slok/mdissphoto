package org.mdissjava.mdisscore.model.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.model.bo.UserBo;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Transient;


@Entity("users")
public class User {
	
	public static enum Gender {Male,Female};
	
	@Id private ObjectId id;	
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
	private List<String> preferences;		
	private Gender gender;	
	
	private String email;
	@Transient
	private String pass;
	
	@Transient
	private List<User> friends;	
	@Embedded
	private Address address;
	@Embedded 
	private Configuration configuration;
	
	public User(){
		configuration = new Configuration();
		preferences = new ArrayList<String>();		
		registeredDate = new Date();
		lastSession = new Date();		
	}
	public User(UserBo user)
	{
		this.id=new ObjectId(user.getId());
		this.nick=user.getNick();
		this.name=user.getName();
		this.surname=user.getSurname();
		this.birthdate=user.getBirthdate();
		this.phone=user.getPhone();
		this.avatar=user.getAvatarId();
		this.registeredDate=user.getRegisteredDate();
		this.active=user.isActive();
		this.lastSession=user.getLastSession();
		this.role=user.getRole();
		this.preferences=user.getPreferences();
		this.gender=user.getGender();
		this.address=new Address(user.getAddress());
		this.configuration=new Configuration(user.getConfiguration());
	}
		
	public ObjectId getId() {
		return id;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
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
	
	public int getAvatar() {
		return avatar;
	}
	
	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}
	
	public Date getRegisteredDate() {
		return registeredDate;
	}
	
	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
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
	
	public void setPreferences(List<String> preferences) {
		this.preferences = preferences;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public List<User> getFriends() {
		return friends;
	}
	public void setFriends(List<User> friends) {
		this.friends = friends;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}
	
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	public void addPreference(String preference){
		this.preferences.add(preference);	
	}
		
	/*
	@Reference
	public List<Camera> cameras;
	@Reference
	public List<Album> albums;
	*/	
	
	
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

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (nick != null ? !nick.equals(user.nick) : user.nick != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;        
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (birthdate != null ? !birthdate.equals(user.birthdate) : user.birthdate != null) return false;
        if (phone != (user.phone)) return false;
       // if (Avatar != null ? !Avatar.equals(user.Avatar) : user.Avatar != null) return false;

        if (registeredDate != null ? !registeredDate.equals(user.registeredDate) : user.registeredDate != null) return false;
        if (lastSession != null ? !lastSession.equals(user.lastSession) : user.lastSession != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        return true;
    }
	
	
	@Override
	public String toString(){
		String rest;
		rest = "Name:" + name +   " Surname:" + surname + "\n" +
			   "Birthdate:" + birthdate + " Phone:" + phone + "\n" +
			   "Avatar:" + avatar +  " RegisteredDate:" + registeredDate + "\n";

		return rest;
	}
}
