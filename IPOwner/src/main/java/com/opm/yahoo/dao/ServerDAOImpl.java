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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.opm.yahoo.models.IPAdress;
import com.opm.yahoo.models.Server;

@Repository
@Transactional
public class ServerDAOImpl implements ServerDAO {

	static Logger LOGGER = LoggerFactory.getLogger(ServerDAOImpl.class);
	
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
	public Integer AddServer(Server MyServer) {
		
		try {
			Session session = HibernateSessFactory.getCurrentSession();		
			Integer ServerID = (Integer)session.save(MyServer);
			MyServer.setId(ServerID);
			return ServerID;
		} catch(Exception e) {
			System.err.println(" *************************"+e.getMessage()+"**************");
		} 				
		return -1;
	}

	@Override
	public boolean UpdateServer(Server S) {
		try{
			Session session = HibernateSessFactory.getCurrentSession();			
			session.update(S);	
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Server> getAllServers() {
		 
		try{
			@SuppressWarnings("unchecked")
			List<Server> listCampaign = (List<Server>) HibernateSessFactory.getCurrentSession()
					.createCriteria(Server.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();	
			return listCampaign;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Server getServerByName(String Name) {
		
		try{
			Session session = HibernateSessFactory.getCurrentSession();
			Criteria crit = session.createCriteria(Server.class);
			crit.add(Restrictions.like("name", Name));
			return  (crit.uniqueResult()!=null) ? (Server) crit.uniqueResult() : null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Set<IPAdress> getServersIPs(String ServerName) {
		
		Server _SRV = this.getServerByName(ServerName);
		Hibernate.initialize(_SRV.getChilds());
		try{
			return _SRV.getChilds();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Server getServerByIP(String ip) {
		try{
			Session session = HibernateSessFactory.getCurrentSession();
			Criteria crit = session.createCriteria(Server.class);
			crit.add(Restrictions.like("ip", ip));
			return  (crit.uniqueResult()!=null) ? (Server) crit.uniqueResult() : null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Server> getAllServersFull() {

		try{
			@SuppressWarnings("unchecked")
			List<Server> listServer = (List<Server>) HibernateSessFactory.getCurrentSession()
					.createCriteria(Server.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			for(Server srv : listServer)
				Hibernate.initialize(srv.getChilds());
			return listServer;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Server getServerByNameNoOwner(String Name) {
		try{
			Session session = HibernateSessFactory.getCurrentSession();
			Criteria crit = session.createCriteria(Server.class);
			crit.add(Restrictions.like("name", Name));
			crit.add(Restrictions.isNull("Owner"));
			return  (crit.uniqueResult()!=null) ? (Server) crit.uniqueResult() : null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	
}
