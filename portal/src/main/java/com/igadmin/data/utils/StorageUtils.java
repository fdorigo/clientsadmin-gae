package com.igadmin.data.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.googlecode.objectify.Query;
import com.igadmin.auth.User;
import com.igadmin.conf.AppConfig;
import com.igadmin.data.Client;
import com.igadmin.data.DAO;
import com.igadmin.data.GymPackage;
import com.igadmin.data.GymSession;
import com.igadmin.data.Location;
import com.igadmin.data.Trainer;

public class StorageUtils
{
	private static final Logger		LOG			= Logger.getLogger(StorageUtils.class);
	private static final Boolean	useTestData	= AppConfig.getInstance().useTestValues();

	public static List<Location> getLocationList()
	{
		if (useTestData)
		{
			List<Location> temps = new ArrayList<Location>(1);
			temps.add(TestDataGenerator.createLocation());

			return temps;
		}

		DAO dao = new DAO();

		Query<Location> locations = dao.ofy().query(Location.class);

		if (LOG.isTraceEnabled())
		{
			if (locations != null)
			{
				LOG.trace("Number of locations retrieved: " + locations.count());
			}
		}

		return locations.list();
	}

	public static List<Client> getClientListForTrainer(Trainer trainer)
	{
		DAO dao = new DAO();

		Query<Client> clients = dao.ofy().query(Client.class).filter("trainer.id =", trainer.getId());

		if (LOG.isTraceEnabled())
		{
			if (clients != null)
			{
				LOG.trace(trainer.getName() + " has " + clients.count() + " clients");
			}
		}

		return clients.list();
	}

	public static List<Trainer> getTrainerListForLocation(Location location)
	{
		DAO dao = new DAO();
		Query<Trainer> trainers;

		if (location != null)
		{
			trainers = dao.ofy().query(Trainer.class).filter("location.id =", location.getId());
		}
		else
		{
			trainers = dao.ofy().query(Trainer.class);
		}

		if (LOG.isTraceEnabled())
		{
			if (trainers != null)
			{
				if (location != null)
				{
					LOG.trace(location.getLocationName() + " has " + trainers.count() + " trainers");
				}
				else
				{
					LOG.trace("All locations have " + trainers.count() + " trainers");
				}
			}
		}

		return trainers.list();
	}

	public static List<GymPackage> getPackageListForClient(Client client)
	{
		DAO dao = new DAO();

		Query<GymPackage> packages = dao.ofy().query(GymPackage.class).filter("client.id =", client.getId());

		if (LOG.isTraceEnabled())
		{
			if (packages != null)
			{
				LOG.trace(client.getName() + " has " + packages.count() + " packages");
			}
		}

		return packages.list();
	}

	public static List<GymSession> getSessionListForPackage(GymPackage pack)
	{
		DAO dao = new DAO();

		Query<GymSession> sessions = dao.ofy().query(GymSession.class).filter("package.id =", pack.getId());

		if (LOG.isTraceEnabled())
		{
			if (sessions != null)
			{
				LOG.trace("Selected package has " + sessions.count() + " sessions");
			}
		}

		return sessions.list();
	}

	/**
	 * Retrieves a list of client sessions logged within a specific time range.
	 * 
	 * @param client
	 *            the client to whom the session belong
	 * @param start
	 *            the start date for the search
	 * @param end
	 *            the end date of the search
	 * @return the list of session within the specified time range
	 */
	public static List<GymSession> getClientSessionListForDateRange(final Client client, final Date start, final Date end)
	{
		final DAO dao = new DAO();

		Query<GymSession> sessions = dao.ofy().query(GymSession.class).filter("client.id =", client.getId()).filter("client.dateTrained >=", start).filter("client.dateTrained <=", end);

		if (LOG.isTraceEnabled())
		{
			if (sessions != null)
			{
				LOG.trace(client.getName() + " has " + sessions.count() + " sessions");
			}
		}

		return sessions.list();
	}

	public static User getUserFromDatabase(final String username, final String password)
	{
		final DAO dao = new DAO();

		Query<User> users = dao.ofy().query(User.class).filter("user.username =", username).filter("user.password =", password);

		if (users.count() == 1)
		{
			return users.fetch().iterator().next();
		}

		return null;
	}
}
