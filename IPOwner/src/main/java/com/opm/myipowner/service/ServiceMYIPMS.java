package com.opm.myipowner.service;

import java.util.List;

import com.opm.myipowner.models.Owner;
import com.opm.myipowner.models.UserMYIPMS;

public interface ServiceMYIPMS {
	
	/**
	 * User section
	 **/
	public List<UserMYIPMS> getAllUsers(); 
	public List<UserMYIPMS> getAllActiveUsers();
	/**
	 * 
	 * Owner section
	 */
	public List<Owner> getAllOWners();
	public Owner AddNewOwner(String... params);
	public Owner getOwnerByID(int id); 
	public boolean UpdateOwner(Owner o);
}