package com.opm.myipowner.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;

import com.opm.myipowner.models.UserMYIPMS;

public interface UsermyipmsDAO {

public SessionFactory getSessionFactory();
	
	public Integer AddUserMYIPMS(UserMYIPMS MyServer);
	public boolean UpdateUserMYIPMS(UserMYIPMS O);
	public List<UserMYIPMS> getAllUserMYIPMS();	
	public List<UserMYIPMS> getAllActiveUserMYIPMS();	
	public UserMYIPMS getUserMYIPMSByUserName(String Name);
	
	
}
