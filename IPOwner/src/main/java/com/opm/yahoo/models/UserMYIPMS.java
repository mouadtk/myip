package com.opm.yahoo.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="usermyipms")
public class UserMYIPMS implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5098069330895044018L;
	
	@Id @GeneratedValue
	private int Id;
	String username;
	String password;
	String API_id;
	String API_Key;
	String API_URL = "http://api.myip.ms";
	
	public UserMYIPMS() {
			
	}
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAPI_id() {
		return API_id;
	}

	public void setAPI_id(String aPI_id) {
		API_id = aPI_id;
	}

	public String getAPI_Key() {
		return API_Key;
	}

	public void setAPI_Key(String aPI_Key) {
		API_Key = aPI_Key;
	}

	public String getAPI_URL() {
		return API_URL;
	}

	public void setAPI_URL(String aPI_URL) {
		API_URL = aPI_URL;
	}

}
