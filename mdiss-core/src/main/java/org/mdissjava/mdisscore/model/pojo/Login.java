package org.mdissjava.mdisscore.model.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="login")
public class Login {

	@Id private String id;
	// En ppio el id de [login, password] va a ser el mismo que el del usuario por lo que no hace 
	// falta poner @GeneratedValue
	private String login;
	private String password;
	
	public Login(){
		
	}
	
	public Login(String id, String login, String password) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
