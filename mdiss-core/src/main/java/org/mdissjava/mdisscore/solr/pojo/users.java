package org.mdissjava.mdisscore.solr.pojo;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

import com.google.code.morphia.annotations.Entity;

@Entity
public class users {
	
	@Field
	private int id;
	@Field
	private int city;
	@Field
	private int country;
	@Field
	private int state;
	@Field
	private String street;
	@Field
	private String zip;
	@Field
	private int avatar;
	@Field
	private Date birthdate;
	@Field
	private boolean showaddress;
	@Field
	private boolean showbirthdate;
	@Field
	private boolean showcameras;
	@Field
	private boolean showcity;
	@Field
	private boolean showcountry;
	@Field
	private boolean showemail;
	@Field
	private boolean showgender;
	@Field
	private boolean showname;
	@Field
	private boolean shownick;
	@Field
	private boolean showphone;
	@Field
	private boolean showsurname;
	@Field
	private boolean showzip;
	@Field
	private String email;
	@Field
	private String gender;
	@Field
	private Date lastsession;
	@Field
	private String name;
	@Field
	private String nick;
	@Field
	private String pass;
	@Field
	private int phone;
	@Field
	private String preferences;
	@Field
	private Date registereddate;
	@Field
	private String role;
	@Field
	private String surname;
	@Field
	private boolean isprivate;
	public int getId() {
		return id;
	}
	public int getCity() {
		return city;
	}
	public int getCountry() {
		return country;
	}
	public int getState() {
		return state;
	}
	public String getStreet() {
		return street;
	}
	public String getZip() {
		return zip;
	}
	public int getAvatar() {
		return avatar;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public boolean isShowaddress() {
		return showaddress;
	}
	public boolean isShowbirthdate() {
		return showbirthdate;
	}
	public boolean isShowcameras() {
		return showcameras;
	}
	public boolean isShowcity() {
		return showcity;
	}
	public boolean isShowcountry() {
		return showcountry;
	}
	public boolean isShowemail() {
		return showemail;
	}
	public boolean isShowgender() {
		return showgender;
	}
	public boolean isShowname() {
		return showname;
	}
	public boolean isShownick() {
		return shownick;
	}
	public boolean isShowphone() {
		return showphone;
	}
	public boolean isShowsurname() {
		return showsurname;
	}
	public boolean isShowzip() {
		return showzip;
	}
	public String getEmail() {
		return email;
	}
	public String getGender() {
		return gender;
	}
	public Date getLastsession() {
		return lastsession;
	}
	public String getName() {
		return name;
	}
	public String getNick() {
		return nick;
	}
	public String getPass() {
		return pass;
	}
	public int getPhone() {
		return phone;
	}
	public String getPreferences() {
		return preferences;
	}
	public Date getRegistereddate() {
		return registereddate;
	}
	public String getRole() {
		return role;
	}
	public String getSurname() {
		return surname;
	}
	public boolean isIsprivate() {
		return isprivate;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCity(int city) {
		this.city = city;
	}
	public void setCountry(int country) {
		this.country = country;
	}
	public void setState(int state) {
		this.state = state;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public void setShowaddress(boolean showaddress) {
		this.showaddress = showaddress;
	}
	public void setShowbirthdate(boolean showbirthdate) {
		this.showbirthdate = showbirthdate;
	}
	public void setShowcameras(boolean showcameras) {
		this.showcameras = showcameras;
	}
	public void setShowcity(boolean showcity) {
		this.showcity = showcity;
	}
	public void setShowcountry(boolean showcountry) {
		this.showcountry = showcountry;
	}
	public void setShowemail(boolean showemail) {
		this.showemail = showemail;
	}
	public void setShowgender(boolean showgender) {
		this.showgender = showgender;
	}
	public void setShowname(boolean showname) {
		this.showname = showname;
	}
	public void setShownick(boolean shownick) {
		this.shownick = shownick;
	}
	public void setShowphone(boolean showphone) {
		this.showphone = showphone;
	}
	public void setShowsurname(boolean showsurname) {
		this.showsurname = showsurname;
	}
	public void setShowzip(boolean showzip) {
		this.showzip = showzip;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setLastsession(Date lastsession) {
		this.lastsession = lastsession;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}
	public void setRegistereddate(Date registereddate) {
		this.registereddate = registereddate;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public void setIsprivate(boolean isprivate) {
		this.isprivate = isprivate;
	}
	
	@Override
	public String toString() {
		return "usuario [id=" + id + ", city=" + city
				+ ", country=" + country + ", state=" + state + ", street="
				+ street + ", zip=" + zip + ", avatar=" + avatar
				+ ", birthdate=" + birthdate + ", email=" + email + ", gender="
				+ gender + ", lastsession=" + lastsession + ", name=" + name
				+ ", nick=" + nick + ", pass=" + pass + ", phone=" + phone
				+ ", preferences=" + preferences + ", registereddate="
				+ registereddate + ", role=" + role + ", surname=" + surname
				+ ", isprivate=" + isprivate +"]";
	}

}
