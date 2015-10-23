package com.opm.yahoo.buisness;

import java.util.List;

import com.opm.yahoo.models.Owner;
import com.opm.yahoo.models.UserMYIPMS;

public interface ServiceMYIPMS {
	
	public List<UserMYIPMS> getAllUsers(); 
	public Owner AddNewOwner(String... params);
	public List<Owner> getAllOWners();
}
