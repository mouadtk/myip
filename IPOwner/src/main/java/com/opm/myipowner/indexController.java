package com.opm.myipowner;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.FilenameUtils;
import org.glassfish.jersey.process.internal.RequestScope.Instance;
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

import com.opm.myipowner.buisness.Myipms;
import com.opm.myipowner.buisness.MyipmsProcess;
import com.opm.myipowner.dao.ServerDAO;
import com.opm.myipowner.models.Owner;
import com.opm.myipowner.models.Server;
import com.opm.myipowner.models.UserMYIPMS;
import com.opm.myipowner.service.ServerService;
import com.opm.myipowner.service.ServiceMYIPMS;

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
	MyipmsProcess _myipsqProcess;
	
	@Autowired
	private Environment environment;
	
	@RequestMapping(value = {"/","/index"}, method = RequestMethod.GET)
	public ModelAndView index() {
		
		
		List<Owner> _Owners = ServiceMYIPMS.getAllOWners();
		ModelAndView mv = new ModelAndView("index");		
		mv.addObject("_owners", _Owners);
		return mv;
		
	}
	
	@RequestMapping(value = "/formProcess", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file){
			/**
			 * check if source is an excel file
			 * & check format
			 **/
			String ext = FilenameUtils.getExtension(file.getOriginalFilename());
			Map<String , Server> MyServers =  new HashMap<String, Server>();
			/***
			 * Upload source file.
			 **/
			Date dNow 			= new Date();
	        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd_HH-mm-ss");
	        String name 	= ft.format(dNow);
			String srcFile = this.UploadFile(name, file);
			if(srcFile.contains("You failed"))
				return "Failed to upload Servers File";
			/**
			 * Upload Servers
			 **/
			try{
				MyServers = serverService.LoadServersWithNoOwner(srcFile, MyServers);
				if(MyServers == null){
					return "Server List is empty";
					
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			/***
			 * Get Users (myip.ms userAccounts)
			 **/
			List<UserMYIPMS> _users = ServiceMYIPMS.getAllActiveUsers();
			System.out.println(_users.size());
			(new Scanner(System.in)).nextLine();
			if(_users.isEmpty())
				return "no user exist!";
			/**
			 * get Owner of each Server
			 **/
			
			List<Owner> _owners = this.serverService.setOwnerToServers(MyServers, _users);

			Myipms process = new Myipms();
			process.setOwners(_owners);
			process.setUser(_users.get(0));
			//_myipsqProcess.OwnersIPRanges("", process);
			
			return "nbr servers"+MyServers.size();
	}
	
	@RequestMapping(value = "/getIPsRange", method = RequestMethod.GET)
	public @ResponseBody String getIPsRange(@RequestParam("id") String id){
		
		List<Owner> ListOwners = new ArrayList<Owner>();
		ListOwners.add(ServiceMYIPMS.getOwnerByID(Integer.parseInt(id)));
		
		List<UserMYIPMS> ListUsers =  new ArrayList<UserMYIPMS>();
		ListUsers.addAll(ServiceMYIPMS.getAllActiveUsers());
		//_myipsqProcess.OwnersIPRanges(id+"", ListUsers, ListOwners);
		Myipms _myipmsGetOwnersIPRange = new Myipms();
		_myipmsGetOwnersIPRange.setOwners(ListOwners);
		_myipmsGetOwnersIPRange.setUser(ListUsers.get(0));
		_myipmsGetOwnersIPRange.MYIPMS  = this.ServiceMYIPMS;
		// if (_myipmsGetOwnersIPRange.MYIPMS instanceof ServiceMYIPMS && _myipmsGetOwnersIPRange.MYIPMS!= null);
		_myipmsGetOwnersIPRange.run();
		
		return id;
	
	}
	
	/***
	 * Upload & save file, rename it , and return path to it
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
