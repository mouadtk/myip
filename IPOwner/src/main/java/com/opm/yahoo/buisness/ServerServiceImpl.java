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
import java.util.TimeZone;

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
import com.opm.yahoo.models.Server;
import com.opm.yahoo.models.UserMYIPMS;


@Service("ServerService")
@Transactional
public class ServerServiceImpl implements ServerService {

	@Autowired
	ServerDAO srv;
	@Autowired
	IPadressDAO ip;

	
	@Override
	public Map<String, Server> LoadServers(String SrcFilePath, Map<String, Server> MyServers) throws IOException{
		
		try {
			FileInputStream file = new FileInputStream(new File(SrcFilePath));
			XSSFWorkbook workbook = new XSSFWorkbook(file); // Create Workbook instance holding reference to .xlsx file
			XSSFSheet sheet = workbook.getSheetAt(0); 		// Get first/desired sheet  from the workbook
			Iterator<Row> rowIterator = sheet.iterator(); 	// Iterate through each rows one by one
			rowIterator.next();
			while (rowIterator.hasNext()) {
				/**
				 * Read an excel row 
				 **/
				Row row = rowIterator.next();
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
				
				Server _srv 	= null;//new Server(name, ip, domain);
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
								this.srv.AddServer(_srv);							/** persist in DB if not*/
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
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return MyServers;
	}


	
	@Override
	public String getOwner(String ip, UserMYIPMS user) {
		String Results = null;
		Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd_hh:mm:ss");
        TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
        ft.setTimeZone(tzInAmerica);
        String timestamp = ft.format(dNow);
        
        //timestamp = "2015-10-16_20:07:12";
		String _link = "http://api.myip.ms/"+ip+"/api_id/"+user.getAPI_id()+"/api_key/"+user.getAPI_Key();
		System.out.println("sign input: " +_link+"/timestamp/"+timestamp);
		String signature =  getSignature(_link+"/timestamp/"+timestamp);
		System.out.println("time: "+timestamp);
		//signature = getSignature("http://api.myip.ms/66.147.230.66/api_id/id20763/api_key/1159677427-2147275262-626413380/timestamp/"+timestamp);
		System.out.println("signature: "+signature);
		
		String QUERY_URL = user.getAPI_URL()+"/"+ip+"/api_id/"+user.getAPI_id()+"/api_key/"+user.getAPI_Key()+"/signature/"+signature+"/timestamp/"+timestamp+"";
		System.out.println("url: "+QUERY_URL);
		try{
			Results =  readUrl(QUERY_URL);
			System.out.println(Results);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return Results;
	} 

	/***
	 * @param val 
	 * @return the myip.ms signature API
	 */
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
		// TODO Auto-generated method stub
		return 0;
	}

}
