package com.opm.yahoo.dao;

import java.util.List;
import java.util.Set;

import com.opm.yahoo.models.IPAdress;
import com.opm.yahoo.models.Server;


public interface IPadressDAO {
	
	public Integer AddIPAdress(IPAdress MyIPAdress);
	public boolean UpdateIPAdress(IPAdress O);
	public List<IPAdress> getAllIPAdresses();
	public IPAdress getIPAdressByIP(String ip);
	
	public List<IPAdress> getServersIPs(Server srv);
}
