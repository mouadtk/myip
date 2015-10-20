package com.opm.yahoo.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import com.opm.yahoo.models.IPAdress;
import com.opm.yahoo.models.Server;

public interface ServerDAO {
	
	public SessionFactory getSessionFactory();
	public Integer AddServer(Server MyServer);
	public boolean UpdateServer(Server O);
	public List<Server> getAllServers();
	public List<Server> getAllServersFull(); // with IPSList
	
	public Server getServerByName(String Name);
	public Server getServerByIP(String ip);
	
	public Set<IPAdress> getServersIPs(String ServerName);
	

}