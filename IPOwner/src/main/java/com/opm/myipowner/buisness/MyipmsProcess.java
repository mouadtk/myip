package com.opm.myipowner.buisness;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.opm.myipowner.models.Owner;
import com.opm.myipowner.models.UserMYIPMS;
import com.opm.myipowner.service.ServiceMYIPMS;


@Service
@Scope("singleton")
public class MyipmsProcess {

	/**
	 *Map <Key, SNDSProcess>
	 **/
	@Autowired
	static ServiceMYIPMS MYIPMS;
	
	static public Map<String, Myipms> IPsRangeProcess =  new HashMap<String, Myipms>();
	
	static public void OwnersIPRanges(String key ,List<UserMYIPMS> _users, List<Owner> _owners) {
		
			Myipms _myipmsGetOwnersIPRange = new Myipms();
			try{
				_myipmsGetOwnersIPRange.setOwners(_owners);
				_myipmsGetOwnersIPRange.setUser(_users.get(0));
				//_myipmsGetOwnersIPRange.MYIPMS = MYIPMS;
				ExecutorService taskExecutor = Executors.newSingleThreadExecutor();
		        taskExecutor.execute(_myipmsGetOwnersIPRange);
				taskExecutor.shutdown();
				taskExecutor.awaitTermination(0, TimeUnit.SECONDS);
	        }catch (Exception e){
	        	 e.printStackTrace();
	        }
			
			if(_myipmsGetOwnersIPRange.getOwners().size() >0 )
				IPsRangeProcess.put(key, _myipmsGetOwnersIPRange);
	}
}
