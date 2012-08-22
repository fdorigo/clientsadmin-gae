package com.igadmin.data;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.igadmin.data.utils.StorageUtils;

public class TrainerTest
{
	private final static LocalServiceTestHelper	helper	= new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp()
	{
		helper.setUp();
	}

	@After
	public void tearDown()
	{
		helper.tearDown();
	}

	@Test
	public void testTrainerWrite()
	{
		DAO dao = new DAO();

		Location location = new Location();
		location.setLocationName("Test Location 1");

		dao.ofy().put(location);

		Trainer trainer = new Trainer();
		trainer.setNameFirst("Mario");
		trainer.setLocationKey(new Key<Location>(Location.class, location.getId()));
		
		dao.ofy().put(trainer);
		Assert.assertNotNull("Failed to write trainer object to DB", trainer.getId());
	}

	@Test
	public void testTrainerRead()
	{
		DAO dao = new DAO();

		Location location = new Location();
		location.setLocationName("Test Location 1");
		dao.ofy().put(location);
		Assert.assertNotNull("Failed to write location object to DB", location.getId());
		
		Trainer trainer = new Trainer();
		trainer.setNameFirst("Mario");
		trainer.setLocationKey(new Key<Location>(Location.class, location.getId()));
		dao.ofy().put(trainer);
		Assert.assertNotNull("Failed to write trainer object to DB", trainer.getId());
		
		Client client1 = new Client();
		client1.setLocationKey(new Key<Location>(Location.class, location.getId()));
		client1.setTrainerKey(new Key<Trainer>(Trainer.class, trainer.getId()));
		dao.ofy().put(client1);
		Assert.assertNotNull("Failed to write client1 object to DB", client1.getId());

		Client client2 = new Client();
		client2.setLocationKey(new Key<Location>(Location.class, location.getId()));
		client2.setTrainerKey(new Key<Trainer>(Trainer.class, trainer.getId()));
		dao.ofy().put(client2);
		Assert.assertNotNull("Failed to write client2 object to DB", client2.getId());

		List<Trainer> trainers = dao.ofy().query(Trainer.class).list();
		Assert.assertEquals("Failed to read trainer list", 1, trainers.size());
		
		Trainer newTrainer = dao.ofy().query(Trainer.class).filter("id =", trainer.getId()).get();
		Assert.assertNotNull("Faiiled to query for trainer by id", newTrainer);
	}

	@Test
	public void testTrainerGetClientList()
	{
		DAO dao = new DAO();

		Location location = new Location();
		location.setLocationName("Test Location 1");
		dao.ofy().put(location);
		Assert.assertNotNull("Failed to write location object to DB", location.getId());
		
		Trainer trainer = new Trainer();
		trainer.setNameFirst("Mario");
		trainer.setLocationKey(new Key<Location>(Location.class, location.getId()));
		dao.ofy().put(trainer);
		Assert.assertNotNull("Failed to write trainer object to DB", trainer.getId());
		
		Client client1 = new Client();
		client1.setLocationKey(new Key<Location>(Location.class, location.getId()));
		client1.setTrainerKey(new Key<Trainer>(Trainer.class, trainer.getId()));
		dao.ofy().put(client1);
		Assert.assertNotNull("Failed to write client1 object to DB", client1.getId());

		Client client2 = new Client();
		client2.setLocationKey(new Key<Location>(Location.class, location.getId()));
		client2.setTrainerKey(new Key<Trainer>(Trainer.class, trainer.getId()));
		dao.ofy().put(client2);
		Assert.assertNotNull("Failed to write client2 object to DB", client2.getId());

		List<Trainer> trainers = dao.ofy().query(Trainer.class).list();
		Assert.assertEquals("Failed to read trainer list", 1, trainers.size());
		
		Trainer newTrainer = dao.ofy().query(Trainer.class).filter("id =", trainer.getId()).get();
		Assert.assertNotNull("Faiiled to query for trainer by id", newTrainer);
		
		Trainer trainer2 = new Trainer();
		trainer2.setNameFirst("Calisto");
		trainer2.setLocationKey(new Key<Location>(Location.class, location.getId()));
		dao.ofy().put(trainer2);
		Assert.assertNotNull("Failed to write trainer2 object to DB", trainer2.getId());
		
		Client client3 = new Client();
		client3.setLocationKey(new Key<Location>(Location.class, location.getId()));
		client3.setTrainerKey(new Key<Trainer>(Trainer.class, trainer.getId()));
		dao.ofy().put(client3);
		Assert.assertNotNull("Failed to write client3 object to DB", client3.getId());
		List<Client> clients1 = StorageUtils.get().getClientListForTrainer(trainer2.getId());
		Assert.assertEquals("Client list has wrong length", 1, clients1.size());

		List<Client> clients = StorageUtils.get().getClientListForTrainer(newTrainer.getId());
		Assert.assertEquals("Client list has wrong length", 2, clients.size());
	}
}
