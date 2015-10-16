package com.opm.yahoo.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.opm.yahoo.models.IPAdress;
import com.opm.yahoo.models.Server;

@Repository
@Transactional
public class IPadressDAOImpl implements IPadressDAO {

	static Logger LOGGER = LoggerFactory.getLogger(IPadressDAOImpl.class);
	
	@Autowired
	private SessionFactory HibernateSessFactory;
	
	public void setSessionFactory(SessionFactory sf){
        this.HibernateSessFactory = sf;
    }

	@Override
	public Integer AddIPAdress(IPAdress MyIPAdress) {
		
		try {
			Session session = HibernateSessFactory.getCurrentSession();
			Integer IPID = (Integer)session.save(MyIPAdress);
			MyIPAdress.setId(IPID);
			return IPID;
		} catch(Exception e) {
			System.err.println("************"+e.getMessage()+"**************");
		}	
		return -1;
	}

	@Override
	public boolean UpdateIPAdress(IPAdress O) {
		 
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
	public List<IPAdress> getAllIPAdresses() {
		 
		try{
			@SuppressWarnings("unchecked")
			List<IPAdress> listIPs = (List<IPAdress>) HibernateSessFactory.getCurrentSession()
					.createCriteria(IPAdress.class).add(Restrictions.like("name", ""))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();	
			return listIPs;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public IPAdress getIPAdressByIP(String ip) {
		 
		try{
			Session session = HibernateSessFactory.getCurrentSession();
			Criteria crit = session.createCriteria(IPAdress.class);
			crit.add(Restrictions.like("IP", ip));
			return  crit.uniqueResult()!=null ? (IPAdress) crit.uniqueResult() : null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public List<IPAdress> getServersIPs(Server _SRV) {
		
		try{
			Session session = HibernateSessFactory.getCurrentSession();
			Criteria crit = session.createCriteria(IPAdress.class);
			crit.add(Restrictions.like("Parent", _SRV));
			return (List<IPAdress>) crit.list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
