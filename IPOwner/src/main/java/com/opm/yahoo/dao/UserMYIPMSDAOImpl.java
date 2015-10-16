package com.opm.yahoo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.opm.yahoo.models.Server;
import com.opm.yahoo.models.UserMYIPMS;

@Repository
@Transactional
public class UserMYIPMSDAOImpl implements UsermyipmsDAO{

static Logger LOGGER = LoggerFactory.getLogger(ServerDAOImpl.class);
	
	@Autowired
	private SessionFactory HibernateSessFactory;
	
	public void setSessionFactory(SessionFactory sf){
        this.HibernateSessFactory = sf;
    } 
	
	public SessionFactory getSessionFactory(){
		return HibernateSessFactory;
	}
	

	@Override
	public Integer AddUserMYIPMS(UserMYIPMS MyUser) {

		try {
			Session session = HibernateSessFactory.getCurrentSession();		
			Integer ServerID = (Integer)session.save(MyUser);
			MyUser.setId(ServerID);
			return ServerID;
		} catch(Exception e) {
			System.err.println(" *************************"+e.getMessage()+"**************");
		} 				
		return -1;
	}

	@Override
	public boolean UpdateUserMYIPMS(UserMYIPMS u) {
		try{
			Session session = HibernateSessFactory.getCurrentSession();			
			session.update(u);	
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<UserMYIPMS> getAllUserMYIPMS() {
		try{
			@SuppressWarnings("unchecked")
			List<UserMYIPMS> listUsers = (List<UserMYIPMS>) HibernateSessFactory.getCurrentSession()
					.createCriteria(UserMYIPMS.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();	
			return listUsers;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public UserMYIPMS getUserMYIPMSByUserName(String Name) {

		try{
			Session session = HibernateSessFactory.getCurrentSession();
			Criteria crit = session.createCriteria(UserMYIPMS.class);
			crit.add(Restrictions.like("username", Name));
			return  (crit.uniqueResult()!=null) ? (UserMYIPMS) crit.uniqueResult() : null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	
	}

}
