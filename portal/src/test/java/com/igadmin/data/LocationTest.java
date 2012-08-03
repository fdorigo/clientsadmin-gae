package com.igadmin.data;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class LocationTest
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
	public void testLocationWrite()
	{
		DAO dao = new DAO();

		Location location = new Location();
		location.setLocationName("Test Location 1");

		dao.ofy().put(location);

		assertNotNull(location.getId());
	}

	@Test
	public void testLocationRead()
	{
		DAO dao = new DAO();

		Location location = new Location();
		location.setLocationName("Test Location 2");
		dao.ofy().put(location);
		assertNotNull(location.getId());
		
        List<Location> locations = dao.ofy().query(Location.class).list();
        Assert.assertEquals(1,locations.size());
        
        Assert.assertFalse("Test Location 1".equals(locations.get(0).getLocationName()));
        Assert.assertEquals("Test Location 2", locations.get(0).getLocationName());
        
        System.out.println(location.toString());
        
	}
	@Test

	public void testLocationDelete()
	{
		DAO dao = new DAO();

		Location location = new Location();
		location.setLocationName("Test Location 3");
		dao.ofy().put(location);
		assertNotNull(location.getId());
		
        List<Location> locations = dao.ofy().query(Location.class).list();
        
        Assert.assertEquals(1,locations.size());       
        Assert.assertTrue(!"Test Location 1".equals(locations.get(0).getLocationName()));
        Assert.assertEquals("Test Location 3", locations.get(0).getLocationName());
        
        dao.ofy().delete(locations);
        
        List<Location> locations_new = dao.ofy().query(Location.class).list();
        Assert.assertEquals(0,locations_new.size());              
        try { 
        	Assert.assertNotNull(locations_new.get(0).getId());
        }
        catch (IndexOutOfBoundsException e)
        {
        }
	}
	
	public void testLocationListCreate()
	{
		DAO dao = new DAO();

		Location location = new Location();
		location.setLocationName("Test Location 4");
		dao.ofy().put(location);
		assertNotNull(location.getId());

		Location location2 = new Location();
		location2.setLocationName("Test Location 5");
		dao.ofy().put(location2);
		assertNotNull(location2.getId());

        List<Location> locations = dao.ofy().query(Location.class).list();
        Assert.assertEquals(2,locations.size());       
	}
	
	public void testLocationListDelete()
	{
		DAO dao = new DAO();

		Location location = new Location();
		location.setLocationName("Test Location 4");
		dao.ofy().put(location);
		assertNotNull(location.getId());

		Location location2 = new Location();
		location2.setLocationName("Test Location 5");
		dao.ofy().put(location2);
		assertNotNull(location2.getId());

        List<Location> locations = dao.ofy().query(Location.class).list();
        Assert.assertEquals(2,locations.size());       
        
        dao.ofy().delete(locations);
        
        List<Location> locations_new = dao.ofy().query(Location.class).list();
        Assert.assertEquals(0,locations_new.size());              
        try { 
        	Assert.assertNotNull(locations_new.get(0).getId());
        }
        catch (IndexOutOfBoundsException e)
        {
        }
	}
}
