package com.opm.myipowner.dao;

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

import com.opm.myipowner.models.IPAdress;
import com.opm.myipowner.models.Owner;
import com.opm.myipowner.models.Server;

@Repository
@Transactional
public class OwnerDAOImpl implements OwnerDAO {

	
	static Logger LOGGER = LoggerFactory.getLogger(OwnerDAOImpl.class);
	
	@Autowired
	private SessionFactory HibernateSessFactory;
	
	public void setSessionFactory(SessionFactory sf){
        this.HibernateSessFactory = sf;
    } 
	
	@Override
	public SessionFactory getSessionFactory(){
		return HibernateSessFactory;
	}
	
	@Override
	public Integer AddOwner(Owner MyOWner) {
		 
		try {
			Session session = HibernateSessFactory.getCurrentSession();		
			Integer ID = (Integer)session.save(MyOWner);
			//MyOWner.setId(ID);
			return ID;
		}catch(Exception e){
			System.err.println(" *************************"+e.getMessage()+"**************");
		}
		return -1;
	
	}

	@Override
	public boolean UpdateOwner(Owner O) {
		try{
			Session session = HibernateSessFactory.getCurrentSession();			
			session.update(O);	
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Owner> getAllOwners() {
		 
		try{
			@SuppressWarnings("unchecked")
			List<Owner> list= (List<Owner>) HibernateSessFactory.getCurrentSession()
					.createCriteria(Owner.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();	
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public Owner getOwnerByName(String Name) {
		
		try{
			Session session = HibernateSessFactory.getCurrentSession();
			Criteria crit = session.createCriteria(Owner.class);
			crit.add(Restrictions.like("Name", Name));
			return  crit.uniqueResult()!=null ? (Owner) crit.uniqueResult() : null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public Owner getOwnerByID(int id) {
		
		try{
			Session session = HibernateSessFactory.getCurrentSession();
			Criteria crit = session.createCriteria(Owner.class);
			crit.add(Restrictions.eq("Id", id));
			return  crit.uniqueResult()!=null ? (Owner) crit.uniqueResult() : null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}