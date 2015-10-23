package com.opm.myipowner.service;

import java.util.List;

import com.opm.myipowner.models.Owner;
import com.opm.myipowner.models.UserMYIPMS;

public interface ServiceMYIPMS {
	
	public List<UserMYIPMS> getAllUsers(); 
	public List<UserMYIPMS> getAllActiveUsers();
	public Owner AddNewOwner(String... params);
	public List<Owner> getAllOWners();
	
	
}
