package com.opm.yahoo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.opm.yahoo.buisness.UserService;
import com.opm.yahoo.models.Server;
import com.opm.yahoo.models.UserMYIPMS;

@Controller
@RequestMapping(value = {"", "/index", ""})
@PropertySource(value = { "classpath:application.properties" })
public class indexController {

	@Autowired
	ServerService serverService; 
	
	@Autowired
	UserService userService;
	
	@Autowired
	private Environment environment;
	
	@RequestMapping(value = {"/","/index"}, method = RequestMethod.GET)
	public ModelAndView provideUploadInfo() {

		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
	@RequestMapping(value = "/formProcess", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(
				@RequestParam("name") String name,
				@RequestParam("file") MultipartFile file){
			/**
			 * check if source is an excel file
			 **
			String ext = FilenameUtils.getExtension(file.getOriginalFilename());
			System.out.println(ext);
			
			Map<String , Server> MyServers =  new HashMap<String, Server>();
			/***
			 * Upload source file.
			 **
			String srcFile = this.UploadFile(name, file);
			/**
			 * Upload Servers
			 **
			try{
				MyServers = serverService.LoadServers(srcFile, MyServers);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			/***
			 * Get Users (myip.ms userAccounts)
			 */
			List<UserMYIPMS> _users = userService.getAllUsers();
			if(_users.isEmpty())
				return "users 0";
			/**
			 * get Owner of each Server
			 **/
			
			return "owner : " + serverService.getOwner("66.147.230.66", _users.get(0));
			//return MyServers.size()+"   -   "+file.getContentType();
	}
	
	/***
	 * @param name
	 * @param file
	 * @return path/file name uploaded
	 ***/
	private String UploadFile(String name, MultipartFile file){
		if (!file.isEmpty()) {
			try {
				File _FILE = new File(environment.getRequiredProperty("srcfilepath")+name);
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(_FILE));
				stream.write(bytes);
				stream.close();
				return _FILE.getAbsolutePath();
			} catch (Exception e) {
				e.printStackTrace();
				return "to upload " + name + "" + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}
}
