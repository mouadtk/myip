package com.opm.yahoo.buisness;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.opm.yahoo.dao.UsermyipmsDAO;
import com.opm.yahoo.models.UserMYIPMS;

@Service
@Repository("UserService")
public class UserServiceImpl implements UserService{

	@Autowired
	UsermyipmsDAO _user;
	
	@Override
	public List<UserMYIPMS> getAllUsers() {
		return _user.getAllUserMYIPMS();
	}

}
