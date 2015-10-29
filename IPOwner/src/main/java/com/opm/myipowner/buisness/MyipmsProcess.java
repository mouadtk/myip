package com.opm.myipowner.buisness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.opm.myipowner.models.Owner;
import com.opm.myipowner.models.UserMYIPMS;
import com.opm.myipowner.service.ServiceMYIPMS;


@Service
@Scope("singleton")
public class MyipmsProcess {

	/**
	 * Map <Key, myipms>
	 **/
	@Autowired
	ServiceMYIPMS MYIPMS;
	
	public Map<String, ThreadPoolTaskExecutor> IPsRangeProcess =  new HashMap<String, ThreadPoolTaskExecutor>();
	
	public void _OwnersIPRanges(List<UserMYIPMS> _users, List<Owner> _owners) {
		
			String key = KeyGenerator(_owners);
			Myipms _myipmsGetOwnersIPRange = new Myipms();
			try{
				_myipmsGetOwnersIPRange.setOwners(_owners);
				_myipmsGetOwnersIPRange.setUser(_users.get(0));
				_myipmsGetOwnersIPRange.MYIPMS  = this.MYIPMS;
				ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
				taskExecutor.setCorePoolSize(1);
		        taskExecutor.setMaxPoolSize(1);
		        taskExecutor.setQueueCapacity(50);
		        taskExecutor.initialize();
				taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		        taskExecutor.execute(_myipmsGetOwnersIPRange);
		        IPsRangeProcess.put(key, taskExecutor);
		        
	        }catch (Exception e){
	        	 e.printStackTrace();
	        }
	}
	
	
	/**
	 *
	 **/
	public void getSignleOwnerIPsRange(UserMYIPMS _user, Owner _owner){
		
		Myipms _myipmsGetOwnersIPRange = new Myipms();
		try{
			List<Owner> Owns = new ArrayList<Owner>();
			Owns.add(_owner);
			_myipmsGetOwnersIPRange.setOwners(Owns);
			_myipmsGetOwnersIPRange.setUser(_user);
			_myipmsGetOwnersIPRange.MYIPMS  = this.MYIPMS;
			ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
			taskExecutor.setCorePoolSize(1);
	        taskExecutor.setMaxPoolSize(1);
	        taskExecutor.setQueueCapacity(1);
	        taskExecutor.initialize();
			taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
			taskExecutor.execute(_myipmsGetOwnersIPRange);
	        IPsRangeProcess.put(_owner.getId()+"", taskExecutor);
		}catch (Exception e){
        	 e.printStackTrace();
        	 return;
        }
	}
	
	/**
	 * 
	 **/
	private String KeyGenerator(List<Owner> owners){
		
		String id = "-";
		for (int i = 0; i < owners.size(); i++) {
			id +=owners.get(i).getId()+"-";
		}
		return id;
	}
}
