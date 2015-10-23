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
import com.opm.yahoo.models.Owner;
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
	public ModelAndView index() {
		
		List<Owner> _Owners = ServiceMYIPMS.getAllOWners();
		for(Owner O: _Owners)
			System.out.println(O.getServers().size());
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("_owners", _Owners);
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
			this.serverService.setOwnerToServers(MyServers, _users);
			System.out.println("nbr servers"+MyServers.size());
			return "nbr servers"+MyServers.size();
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
