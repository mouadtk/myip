package com.opm.myipowner.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ipadress")
public class IPAdress implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7939084955289195358L;
	
	@Id @GeneratedValue
	@Column(name = "IPADRESS_ID")
	private int Id;
	private String IP;
	private String Domain;
	private String MailBox;
	private String DSN;
	private String State;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVER_ID", nullable = true)
	private Server Parent;
	
	/****/
	public IPAdress(){
		
	};
	
	public IPAdress(String... args){
		
		IP 			=  args[0];
		Domain		=  args[1];
		MailBox		=  args[2];
		DSN			=  args[3];
		State		=  args[4];
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getDomain() {
		return Domain;
	}
	public void setDomain(String domain) {
		Domain = domain;
	}
	public String getMailBox() {
		return MailBox;
	}
	public void setMailBox(String mailBox) {
		MailBox = mailBox;
	}
	public String getDSN() {
		return DSN;
	}
	public void setDSN(String dSN) {
		DSN = dSN;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public Server getParent() {
		return Parent;
	}
	public void setParent(Server parent) {
		Parent = parent;
	}
	
}
