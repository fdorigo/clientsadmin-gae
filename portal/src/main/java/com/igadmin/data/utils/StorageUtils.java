package com.igadmin.data.utils;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;
import com.igadmin.auth.User;
import com.igadmin.data.Client;
import com.igadmin.data.DAO;
import com.igadmin.data.Location;
import com.igadmin.data.Trainer;
import com.igadmin.data.TrainingPackage;
import com.igadmin.data.TrainingSession;

public class StorageUtils
{
	private static final Logger	LOG	= Logger.getLogger(StorageUtils.class);

	public static Key<Location> getlocationKey(Long id)
	{
		return new Key<Location>(Location.class, id);
	}
	
	public static List<Location> getLocationList()
	{
		final DAO dao = new DAO();
		final Query<Location> locations = dao.ofy().query(Location.class);

		if (LOG.isTraceEnabled())
		{
			if (locations != null)
			{
				LOG.trace("Number of locations retrieved: " + locations.count());
			}
		}

		return locations.list();
	}

	public static List<Location> getLocationList(int first, int count)
	{
		final DAO dao = new DAO();
		final Query<Location> locations = dao.ofy().query(Location.class).offset(first).limit(count);

		if (LOG.isTraceEnabled())
		{
			if (locations != null)
			{
				LOG.trace("Number of locations retrieved: " + locations.count());
			}
		}

		return locations.list();
	}
	
	
	public static List<Trainer> getTrainerListForLocation(final Long locationId, int first, int count)
	{
		final DAO dao = new DAO();
		final Query<Trainer> trainers;

		if (locationId != null && locationId > 0)
		{
			trainers = dao.ofy().query(Trainer.class).filter("locationKey =", new Key<Location>(Location.class, locationId)).offset(first).limit(count);
		}
		else
		{
			trainers = dao.ofy().query(Trainer.class).offset(first).limit(count);
		}

		if (LOG.isTraceEnabled())
		{
			if (trainers != null)
			{
				if (locationId != null)
				{
					LOG.trace(locationId + " has " + trainers.count() + " trainers");
				}
				else
				{
					LOG.trace("All locations have " + trainers.count() + " trainers");
				}
			}
		}

		return trainers.list();
	}


	public static List<Client> getClientListForTrainer(final Trainer trainer)
	{
		final DAO dao = new DAO();
		final Query<Client> clients = dao.ofy().query(Client.class).filter("trainer.id =", trainer.getId());

		if (LOG.isTraceEnabled())
		{
			if (clients != null)
			{
				LOG.trace(trainer.getName() + " has " + clients.count() + " clients");
			}
		}

		return clients.list();
	}

	public static List<Trainer> getTrainerListForLocation(final Location location)
	{
		final DAO dao = new DAO();
		final Query<Trainer> trainers;

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

	public static List<TrainingPackage> getPackageListForClient(final Client client)
	{
		final DAO dao = new DAO();
		final Query<TrainingPackage> packages = dao.ofy().query(TrainingPackage.class).filter("client.id =", client.getId());

		if (LOG.isTraceEnabled())
		{
			if (packages != null)
			{
				LOG.trace(client.getName() + " has " + packages.count() + " packages");
			}
		}

		return packages.list();
	}

	public static List<TrainingSession> getSessionListForPackage(final TrainingPackage pack)
	{
		final DAO dao = new DAO();
		final Query<TrainingSession> sessions = dao.ofy().query(TrainingSession.class).filter("package.id =", pack.getId());

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
	public static List<TrainingSession> getClientSessionListForDateRange(final Client client, final Date start, final Date end)
	{
		final DAO dao = new DAO();
		final Query<TrainingSession> sessions = dao.ofy().query(TrainingSession.class).filter("client.id =", client.getId()).filter("client.dateTrained >=", start).filter("client.dateTrained <=", end);

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
		final Query<User> users = dao.ofy().query(User.class).filter("user.username =", username).filter("user.password =", password);

		if (users.count() == 1)
		{
			return users.fetch().iterator().next();
		}

		return null;
	}
}
