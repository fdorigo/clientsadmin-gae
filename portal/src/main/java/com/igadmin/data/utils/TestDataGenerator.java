package com.igadmin.data.utils;

import com.igadmin.data.Client;
import com.igadmin.data.DAO;
import com.igadmin.data.Location;
import com.igadmin.data.Trainer;

public class TestDataGenerator
{

	public static Location createLocation()
	{
		final DAO dao = new DAO();
		final Location location = dao.getOrCreateLocation(0);
		
		location.setLocationName("Test HQ");
		
		dao.ofy().put(location);
		
		return location;
	}
	
	public static Trainer createTrainer()
	{
		final DAO dao = new DAO();
		final Trainer trainer = dao.getOrCreateTrainer(0);
		
		trainer.setNameFirst("Darth");
		trainer.setNameLast("Vader");
		trainer.setCompRate(50.0);
		trainer.setLocation(createLocation());
		
		dao.ofy().put(trainer);
		
		return trainer;
	}
	
	public static Client createClient()
	{
		final DAO dao = new DAO();
		final Client client = dao.getOrCreateClient(0);
		
		client.setNameFirst("Joe");
		client.setNameLast("Butterball");
		client.setTrainer(createTrainer());
		client.setLocation(createLocation());
		
		dao.ofy().put(client);
		
		return client;
	}
}
