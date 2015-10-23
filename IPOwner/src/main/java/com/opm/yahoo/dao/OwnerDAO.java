package com.opm.yahoo.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.opm.yahoo.models.Owner;

public interface OwnerDAO {

	public SessionFactory getSessionFactory();
	public Integer AddOwner(Owner MyOWner);
	public boolean UpdateOwner(Owner O);
	public List<Owner> getAllOwners();
	public Owner getOwnerByName(String Name);
}
