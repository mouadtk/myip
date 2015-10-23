package com.opm.myipowner.service;

import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.opm.myipowner.dao.OwnerDAO;
import com.opm.myipowner.dao.UsermyipmsDAO;
import com.opm.myipowner.models.Owner;
import com.opm.myipowner.models.UserMYIPMS;

@Service("ServiceMYIPMS")
@Transactional
public class ServiceMYIPMSImpl implements ServiceMYIPMS{

	@Autowired
	UsermyipmsDAO _user;
	@Autowired
	OwnerDAO _owner;
	
	@Override
	public List<UserMYIPMS> getAllUsers() {
		return _user.getAllUserMYIPMS();
	}

	/**
	 * params: result of api query 
	 */
	@Override
	public Owner AddNewOwner(String... params) {

		JSONParser parser = new JSONParser();
        try {
        	/**
        	 * Extracting json data from 'params'
        	 */
            Object obj = parser.parse(params[0]);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject owners = (JSONObject) jsonObject.get("owners");
//            if(	!((String)  owners.get("status")).equals("ok")){
//            	System.out.println(owners.toString());
//            	return null;
//            }
            JSONObject owner = (JSONObject) owners.get("owner");
            
            System.out.println("Owner Object to String  :"+   owner.toString());
            System.out.println("Owner web site :"+ (String)  owner.get("website"));
            String WebSiteOwner = (String)  owner.get("website");
            String NameOwner = (String)  owner.get("ownerName");
            
            /**
             * Persist Owner to DB
             * if not exist
             **/
            Owner _currentOwner =  this._owner.getOwnerByName(NameOwner);
            if( _currentOwner == null){
            	_currentOwner = new Owner();
            	_currentOwner.setName(NameOwner);
				_currentOwner.setOwnerDetails(owners.toString());
				_currentOwner.setWebSite(WebSiteOwner);
				_owner.AddOwner(_currentOwner);
            }
        	return _currentOwner;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public List<Owner> getAllOWners() {
		List<Owner> owners =  _owner.getAllOwners();
		if(owners!= null){
			for (Owner O : owners) {
				Hibernate.initialize(O.getServers());
				Hibernate.initialize(O.getRange());
			}
		}
		return owners;
		
	}

	@Override
	public List<UserMYIPMS> getAllActiveUsers() {

		return _user.getAllActiveUserMYIPMS();
	}

	
}
