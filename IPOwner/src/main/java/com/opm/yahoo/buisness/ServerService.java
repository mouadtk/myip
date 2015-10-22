package com.opm.yahoo.buisness;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.opm.yahoo.models.Server;
import com.opm.yahoo.models.UserMYIPMS;

public interface ServerService {
	
	public Map<String, Server> LoadServers(String SrcFilePath, Map<String, Server> MyServers) throws IOException;
	public String getOwner(String ip, UserMYIPMS user);
	public int SetOwnerPerServer(List<UserMYIPMS> users, List<Server> servers);
	public Map<String, Server> LoadServersWithNoOwner(String SrcFilePath, Map<String, Server> MyServers) throws IOException;
	public void setOwnerToServers(Map<String,Server> Servers, List<UserMYIPMS> users);
}