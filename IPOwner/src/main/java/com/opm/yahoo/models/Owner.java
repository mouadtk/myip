package com.opm.yahoo.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="owner", uniqueConstraints = {
		@UniqueConstraint(columnNames = "Name"),
		@UniqueConstraint(columnNames = "webSite")})
public class Owner implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7286415575031745544L;
	
	@Id @GeneratedValue
	@Column(name = "OWNER_ID")
	private int Id;
	private String Name;
	private String webSite;
	private String OwnerDetails;
	
	@OneToMany( mappedBy = "Owner")
	private Set<Server> Servers = new HashSet<Server>(0);
	
	public Owner(){}

	public Owner(String... params){
		
		Name		 = params[0];
		webSite 	 = params[1];
		OwnerDetails = params[2];
	}
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getOwnerDetails() {
		return OwnerDetails;
	}

	public void setOwnerDetails(String ownerDetails) {
		OwnerDetails = ownerDetails;
	}

	public Set<Server> getServers() {
		return Servers;
	}

	public void setServers(Set<Server> servers) {
		Servers = servers;
	}

}