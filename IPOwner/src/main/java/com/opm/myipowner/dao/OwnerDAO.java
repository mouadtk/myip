package com.opm.myipowner.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.opm.myipowner.models.Owner;

public interface OwnerDAO {

	public SessionFactory getSessionFactory();
	public Integer AddOwner(Owner MyOWner);
	public boolean UpdateOwner(Owner O);
	public List<Owner> getAllOwners();
	public Owner getOwnerByName(String Name);
	public Owner getOwnerByID(int id);
	
	
}
