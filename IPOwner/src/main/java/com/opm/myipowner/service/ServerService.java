package com.opm.myipowner.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.opm.myipowner.models.Owner;
import com.opm.myipowner.models.Server;
import com.opm.myipowner.models.UserMYIPMS;

public interface ServerService {
	
	public Map<String, Server> LoadServers(String SrcFilePath, Map<String, Server> MyServers) throws IOException;
	public String getOwner(String ip, UserMYIPMS user);
	public int SetOwnerPerServer(List<UserMYIPMS> users, List<Server> servers);
	public Map<String, Server> LoadServersWithNoOwner(String SrcFilePath, Map<String, Server> MyServers) throws IOException;
	public List<Owner> setOwnerToServers(Map<String,Server> Servers, List<UserMYIPMS> users);
	/**
	 * return Nbr of servvers have been treated
	 */
	public int getRangesforOwners(List<UserMYIPMS> users, List<Owner> owners);
	
}