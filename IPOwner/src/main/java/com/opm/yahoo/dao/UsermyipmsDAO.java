package com.opm.yahoo.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;

import com.opm.yahoo.models.UserMYIPMS;

public interface UsermyipmsDAO {

public SessionFactory getSessionFactory();
	
	public Integer AddUserMYIPMS(UserMYIPMS MyServer);
	public boolean UpdateUserMYIPMS(UserMYIPMS O);
	public List<UserMYIPMS> getAllUserMYIPMS();	
	public UserMYIPMS getUserMYIPMSByUserName(String Name);
	
	
}
