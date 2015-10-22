package com.opm.yahoo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.opm.yahoo.buisness.ServerService;
import com.opm.yahoo.buisness.ServiceMYIPMS;
import com.opm.yahoo.dao.ServerDAO;
import com.opm.yahoo.models.Server;
import com.opm.yahoo.models.UserMYIPMS;

@Controller
@RequestMapping(value = {"", "/index", ""})
@PropertySource(value = { "classpath:application.properties" })
public class indexController {

	@Autowired
	ServerService serverService; 
	
	@Autowired
	ServiceMYIPMS ServiceMYIPMS;
	@Autowired
	ServerDAO ServerDAO;
	
	@Autowired
	private Environment environment;
	
	@RequestMapping(value = {"/","/index"}, method = RequestMethod.GET)
	public ModelAndView provideUploadInfo() {
		Server srv  = ServerDAO.getServerByNameNoOwner("ssv4210");
		if(srv != null)
			System.out.println(ServerDAO.getServerByNameNoOwner("ssv4210").getIp()+"-----------");
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
	@RequestMapping(value = "/formProcess", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(
				@RequestParam("name") String name,
				@RequestParam("file") MultipartFile file){
			/**
			 * check if source is an excel file
			 * & check format
			 **/
			String ext = FilenameUtils.getExtension(file.getOriginalFilename());
			System.out.println(ext);
			
			Map<String , Server> MyServers =  new HashMap<String, Server>();
			/***
			 * Upload source file.
			 **/
			String srcFile = this.UploadFile(name, file);
			if(srcFile.contains("You failed"))
				return "Failed to upload Servers File";
			/**
			 * Upload Servers
			 **/
			try{
				MyServers = serverService.LoadServersWithNoOwner(srcFile, MyServers);
				if(MyServers == null){
					return "kahwya lista";
				}
				
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			/***
			 * Get Users (myip.ms userAccounts)
			 **/
			List<UserMYIPMS> _users = ServiceMYIPMS.getAllUsers();
			if(_users.isEmpty())
				return "no user exist!";
			/**
			 * get Owner of each Server
			 **/
			int i = 0;
			
			for (Map.Entry<String, Server> serv : MyServers.entrySet()) {
				try {
					String resMYIPMS = serverService.getOwner(serv.getValue().getIp(), _users.get(0));
					//System.out.println("myips user: "+_users.get(0).getUsername());
					//(new Scanner(System.in)).nextLine();
					this.ServiceMYIPMS.AddNewOwner(resMYIPMS);
					Thread.sleep(500);
					i++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
//			String resMYIPMS = serverService.getOwner("66.147.230.66", _users.get(0));
//			this.ServiceMYIPMS.AddNewOwner(resMYIPMS);
			System.out.println("nbr owner"+ i);
			System.out.println("nbr servers"+MyServers.size());
			return "nbr owner"+ i+" - "+" nbr servers"+MyServers.size();
	}
	
	/***
	 * @param 	name
	 * @param 	file
	 * @return  path/file name uploaded
	 ***/
	private String UploadFile(String name, MultipartFile file){
		
		if (!file.isEmpty()){
			try {
				File _FILE = new File(environment.getRequiredProperty("srcfilepath")+name+".xls");
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(_FILE));
				stream.write(bytes);
				stream.close();
				System.out.println("absolute file path: "+_FILE.getAbsolutePath());
				return _FILE.getAbsolutePath();
			} catch (Exception e) {
				e.printStackTrace();
				return "You failed to upload " + name + "" + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}
}
