package org.mdissjava.mdisscore.model.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;


@Entity
@Table(name="users")
@DynamicInsert(false)
@DynamicUpdate(true)
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum Gender {Male,Female};
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private int id;	
	private String nick;
	private String name;	
	private String surname;	
	private Gender gender;	
	private Date birthdate;	
	private int phone;
	private int avatar;
	private Date registereddate;	
	private Date lastsession;
	//@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean active;	
	private String preferences;
	private String role;
	
	private String email;
	private String pass;


	@ManyToMany( 
		fetch = FetchType.LAZY,
		targetEntity = org.mdissjava.mdisscore.model.pojo.User.class
	)		   
	@JoinTable(name="follows",
	 joinColumns=@JoinColumn(name="userid"),
	 inverseJoinColumns=@JoinColumn(name="followsuserid")
	)
	private List<User> follows = new ArrayList<User>();
	
	@ManyToMany( 
			fetch = FetchType.LAZY,
			targetEntity = org.mdissjava.mdisscore.model.pojo.User.class			
	)
	@JoinTable(name="followedby",
	 joinColumns=@JoinColumn(name="userid"),
	 inverseJoinColumns=@JoinColumn(name="followedbyid")
	)
	private List<User> followers;
	

	@Embedded
	private Address address;
	@Embedded 
	private Configuration configuration;
	
	@Transient
	public List<Camera> cameras;
	//@Transient
	//public List<Album> albums;*/
	
	
	public User(){
		configuration = new Configuration();		
		registereddate = new Date();
		lastsession = new Date();		
		followers = new ArrayList<User>();
		follows = new ArrayList<User>();
	}	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
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
		return registereddate;
	}
	
	public void setRegisteredDate(Date registeredDate) {
		this.registereddate = registeredDate;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Date getLastSession() {
		return lastsession;
	}
	
	public void setLastSession(Date lastSession) {
		this.lastsession = lastSession;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public List<String> getPreferences() {		
		return getPreferencesList(this.preferences);
	}
	
	public void setPreferences(List<String> preferences) {
		this.preferences =getConvertPreferences(preferences);
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public List<User> getFollows() {
		return this.follows;
	}
	
	public void setFollows(List<User> follows) {
		this.follows = follows;
	}
	
	public void addFollow(User friend) {
		if (this.follows == null)
			follows = new ArrayList<User>();
		this.follows.add(friend);
	}
	
	public void removeFollow(User friend) {
		if (this.follows == null)
			follows = new ArrayList<User>();
		this.follows.remove(friend);
	}
	
	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}
	
	public void addFollower(User follower) {
		if (this.followers == null)
			followers = new ArrayList<User>();
		this.followers.add(follower);
	}
	
	public void removeFollower(User follower) {
		if (this.followers == null)
			followers = new ArrayList<User>();
		this.followers.remove(follower);
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
		this.preferences +=","+preference;	
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

	public List<Camera> getCameras() {
		return cameras;
	}

	public void setCameras(List<Camera> cameras) {
		this.cameras = cameras;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        if (id != user.id) return false;
        if (nick != null ? !nick.equals(user.nick) : user.nick != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;        
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (birthdate != null ? !birthdate.equals(user.birthdate) : user.birthdate != null) return false;
        if (phone != (user.phone)) return false;
        if (registereddate != null ? !registereddate.equals(user.registereddate) : user.registereddate != null) return false;
        if (lastsession != null ? !lastsession.equals(user.lastsession) : user.lastsession != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        return true;
    }
	
	
	@Override
	public String toString(){
		String rest;
		rest = "Name:" + name +   " Surname:" + surname + "\n" +
			   "Birthdate:" + birthdate + " Phone:" + phone + "\n" +
			   "Avatar:" + avatar +  " RegisteredDate:" + registereddate + "\n";

		return rest;
	}
	
	private String getConvertPreferences(List<String> lista) {
		String cadena = "";
		for (int i = 0; i < lista.size(); i++) {
			if (i < lista.size())
				cadena += lista.get(i) + ",";
			else
				cadena += lista.get(i);
		}
		return cadena;
	}

	private List<String> getPreferencesList(String UserPreferences) {
		List<String> Preferences = new ArrayList<String>();
		String Cadena = "";
		for (int i = 0; i < UserPreferences.length(); i++) {
			if (UserPreferences.charAt(i) == ',') {
				Preferences.add(Cadena);
				Cadena = "";
			} else {
				Cadena += UserPreferences.charAt(i);
			}
		}
		Preferences.add(Cadena);
		return Preferences;
	}
	
}