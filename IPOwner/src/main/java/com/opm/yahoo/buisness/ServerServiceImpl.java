package com.opm.yahoo.buisness;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opm.yahoo.dao.IPadressDAO;
import com.opm.yahoo.dao.ServerDAO;
import com.opm.yahoo.models.IPAdress;
import com.opm.yahoo.models.Owner;
import com.opm.yahoo.models.Server;
import com.opm.yahoo.models.UserMYIPMS;


@Service("ServerService")
@Transactional
public class ServerServiceImpl implements ServerService {

	@Autowired
	ServerDAO srv;
	@Autowired
	IPadressDAO ip;
	@Autowired
	ServiceMYIPMS _MYIPMS;
	@Autowired
	MyipsOwnerProcess OwnerProcess;
	@Override
	public Map<String, Server> LoadServers(String SrcFilePath, Map<String, Server> MyServers) throws IOException{
		
		try {
			FileInputStream file = new FileInputStream(new File(SrcFilePath));
			XSSFWorkbook workbook = new XSSFWorkbook(file); // Create Workbook instance holding reference to .xlsx file
			XSSFSheet sheet = workbook.getSheetAt(0); 		// Get first/desired sheet  from the workbook
			Iterator<Row> rowIterator = sheet.iterator(); 	// Iterate through each rows one by one
			Row row = rowIterator.next();
			System.out.println("*"+row.getLastCellNum());
			//if(row.getLastCellNum() != 9) return null;
			System.out.println("**"+row.getCell(8));
			//if(!row.getCell(8).equals("Date In")) return null;
			while (rowIterator.hasNext()) {
				/**
				 * Read an excel row 
				 **/
				row = rowIterator.next();
				if( row.getCell(0) == null) break;
				if( row.getCell(0).toString()== "") break; 	/** stop loop if the next cell empty */
				String provider	= row.getCell(1) != null  ? row.getCell(1).toString() : "" ;
				String name    	= row.getCell(2) != null  ? row.getCell(2).toString() : "" ;
				String blok	   	= row.getCell(3) != null  ? row.getCell(3).toString() : "" ;
				String domain  	= row.getCell(4) != null  ? row.getCell(4).toString() : "" ;
				String ip 	   	= row.getCell(5) != null  ? row.getCell(5).toString() : "" ;
				String rdns    	= row.getCell(6) != null  ? row.getCell(6).toString() : "" ;
				String Registrar= row.getCell(7) != null  ? row.getCell(7).toString() : "" ;
				String DateIn  	= row.getCell(8) != null  ? row.getCell(8).toString() : "" ;
				/**
				 * Instantiate Objects
				 **/
				Server _srv = null;
				/**
				 * Check if IP already exists in Database
				 **/
				if( this.ip.getIPAdressByIP(ip) == null ){
					
					IPAdress _ip 	= new IPAdress( ip, domain, null, null, "new");
					/**
					 * Fill the Map & Database
					 **/
					if(!MyServers.containsKey(name)){
						
						_srv = this.srv.getServerByName(name); 						/** get. if server already exist in DB */
						if( _srv == null){											/** if not exists */
							_srv = new Server(name, ip, domain);
							this.srv.AddServer(_srv);								/** persist in DB if not*/
						}
						MyServers.put(name,_srv);									/** add new server*/
						_ip.setParent(_srv);
						this.ip.AddIPAdress(_ip);						
					} else {
						_ip.setParent(MyServers.get(name));					
						this.ip.AddIPAdress(_ip);
					}
				}
			}
			
			/**
			 * Refresh Servers Objects and get there IPs 
			 * */
			for (Map.Entry<String, Server> entry : MyServers.entrySet())
			{
				try{
					this.srv.getSessionFactory().getCurrentSession().refresh(entry.getValue());
			    	Hibernate.initialize(entry.getValue().getChilds());
			    }catch(Exception e){
			    	System.out.println("-->"+e.getMessage());
			    }
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return MyServers;
	}

	/***
	 * 
	 **/
	@Override
	public String getOwner(String ip, UserMYIPMS user) {
		
		String Results 		= null;
		Date dNow 			= new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd_HH:mm:ss");
        String timestamp 	= ft.format(dNow);
        String _link 	 	= "http://api.myip.ms/"+ip+"/api_id/"+user.getAPI_id()+"/api_key/"+user.getAPI_Key();
		String signature 	= getSignature(_link+"/timestamp/"+timestamp);
		String QUERY_URL 	= user.getAPI_URL()+"/"+ip+"/api_id/"+user.getAPI_id()+"/api_key/"+user.getAPI_Key()+"/signature/"+signature+"/timestamp/"+timestamp+"";
		try{
			Results =  readUrl(QUERY_URL);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return Results;
	} 

	/***
	 * @param val
	 * @return the myip.ms signature API
	 ***/
	private String getSignature(String val){
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(val.getBytes());
	        byte byteData[] = md.digest();
	        //convert the byte to hex format method 1
	        StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
	    	return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/***
	 * @param urlString : url source 
	 * @return url content
	 * @throws Exception
	 */
	private String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}

	@Override
	public int SetOwnerPerServer(List<UserMYIPMS> users, List<Server> servers) {

		int i = 0, userIndex = 0;
		for(Server srv : servers){
			String owner = getOwner(srv.getIp(), users.get(0));
		}
		return i;
	}

	@Override
	public Map<String, Server> LoadServersWithNoOwner(String SrcFilePath, Map<String, Server> MyServers)
			throws IOException {
		try {
			FileInputStream file = new FileInputStream(new File(SrcFilePath));
			XSSFWorkbook workbook = new XSSFWorkbook(file); // Create Workbook instance holding reference to .xlsx file
			XSSFSheet sheet = workbook.getSheetAt(0); 		// Get first/desired sheet  from the workbook
			Iterator<Row> rowIterator = sheet.iterator(); 	// Iterate through each rows one by one
			Row row = rowIterator.next();
			System.out.println("*"+row.getLastCellNum());
			//if(row.getLastCellNum() != 9) return null;
			System.out.println("**"+row.getCell(8));
			//if(!row.getCell(8).equals("Date In")) return null;
			while (rowIterator.hasNext()) {
				/**
				 * Read an excel row 
				 **/
				row = rowIterator.next();
				if( row.getCell(0) == null) break;
				if( row.getCell(0).toString()== "") break; 	/** stop loop if the next cell empty */
				String provider	= row.getCell(1) != null  ? row.getCell(1).toString() : "" ;
				String name    	= row.getCell(2) != null  ? row.getCell(2).toString() : "" ;
				String blok	   	= row.getCell(3) != null  ? row.getCell(3).toString() : "" ;
				String domain  	= row.getCell(4) != null  ? row.getCell(4).toString() : "" ;
				String ip 	   	= row.getCell(5) != null  ? row.getCell(5).toString() : "" ;
				String rdns    	= row.getCell(6) != null  ? row.getCell(6).toString() : "" ;
				String Registrar= row.getCell(7) != null  ? row.getCell(7).toString() : "" ;
				String DateIn  	= row.getCell(8) != null  ? row.getCell(8).toString() : "" ;
				
				/**
				 * Instantiate Objects
				 **/
				Server _srv = this.srv.getServerByName(name);
				/**
				 * Check if Server already exists in Database
				 **/
				if(  _srv == null ){
					/**
					 * Fill in the Map & Database
					 **/
					_srv = new Server(name, ip, domain);	
					this.srv.AddServer( _srv);
					MyServers.put(name, _srv);
				}
				else {		/** if server exists in DB check if it has no owner */
					this.srv.getSessionFactory().getCurrentSession().refresh(_srv);
			    	Hibernate.initialize(_srv.getOwner());
			    	if(_srv.getOwner() ==  null)
			    		MyServers.put(name,	_srv);					/** add new server  */
				}
			}
			
			/***
			 * Refresh Servers Objects 
			 ***/
			for (Map.Entry<String, Server> entry : MyServers.entrySet())
			{
				try{
					this.srv.getSessionFactory().getCurrentSession().refresh(entry.getValue());
			    }catch(Exception e){
			    	System.out.println("-->"+e.getMessage());
			    }
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return MyServers;
	}

	
	@Override
	public void setOwnerToServers(Map<String, Server> MyServers,List<UserMYIPMS> _users) {
		
		int i = 0;
		for (Map.Entry<String, Server> serv : MyServers.entrySet()) {
			try {
				String resMYIPMS = getOwner(serv.getValue().getIp(), _users.get(0));
				Owner  O = _MYIPMS.AddNewOwner(resMYIPMS);
				System.out.println("owner id : "+O.getId());
				serv.getValue().setOwner(O);
				this.srv.UpdateServer(serv.getValue());
				Thread.sleep(500);
				i++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	@Override
	public int getRangesforOwners(List<UserMYIPMS> users, List<Owner> owners) {
	
		return 0;
	}
}
