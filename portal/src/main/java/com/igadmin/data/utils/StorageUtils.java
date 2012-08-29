package com.igadmin.data.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;

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
	private static final Logger			LOG				= Logger.getLogger(StorageUtils.class);

	private final List<Location>		locationList	= new ArrayList<Location>();
	private final List<Trainer>			trainerList		= new ArrayList<Trainer>();
	private final List<Client>			clientList		= new ArrayList<Client>();
	private final DAO					dao				= new DAO();
	private static final StorageUtils	INSTANCE		= new StorageUtils();

	public static StorageUtils get()
	{
		return INSTANCE;
	}

	public List<Location> getLocationList(int first, int count)
	{
		return locationList.subList(first, count);
	}

	public List<Location> getLocationList()
	{
		return locationList;
	}

	public Integer getLocationListSize()
	{
		return locationList.size();
	}

	public List<Trainer> getTrainerList()
	{
		return trainerList;
	}

	public Integer getTrainerListSize()
	{
		return trainerList.size();
	}

	public List<Trainer> getTrainerListForLocation(final Long locationId)
	{
		final List<Trainer> list = new ArrayList<Trainer>();

		if (locationId == null)
		{
			LOG.warn("invalid input");
			return list;
		}

		final Key<Location> key = new Key<Location>(Location.class, locationId);

		for (Trainer t : trainerList)
		{
			if (t.getLocationKey().equals(key))
			{
				list.add(t);
			}
		}

		if (LOG.isTraceEnabled())
		{
			if (locationId != null)
			{
				LOG.trace(locationId + " has " + list.size() + " trainers");
			}
		}

		return list;
	}
	
	public List<Trainer> getTrainerListForLocation(final Long locationId, final SortParam sort)
	{
		List<Trainer> list = getTrainerListForLocation(locationId);
		
		if (sort == null)
		{
			return list;
		}

		if (sort.getProperty().equals(Trainer.NAME_LAST_PROPERTY))
		{
			if (sort.isAscending())
			{
				Collections.sort(list, ComparatorFactory.LAST_NAME_ASC);
			}
			else
			{
				Collections.sort(list, ComparatorFactory.LAST_NAME_DES);
			}
		}
		else if (sort.getProperty().equals(Trainer.NAME_FIRST_PROPERTY))
		{
			if (sort.isAscending())
			{
				Collections.sort(list, ComparatorFactory.FIRST_NAME_ASC);
			}
			else
			{
				Collections.sort(list, ComparatorFactory.FIRST_NAME_DES);
			}
		}
		
		return list;
	}

	public List<Trainer> getTrainerListForLocation(final Long locationId, int first, int count)
	{
		if (locationId == null)
		{
			return trainerList.subList(first, count);
		}

		return getTrainerListForLocation(locationId).subList(first, count);
	}

	public List<Client> getClientList()
	{
		return clientList;
	}

	public Integer getClientListSize()
	{
		return clientList.size();
	}

	public List<Client> getClientListForTrainer(final Long trainerId)
	{
		if (trainerId == null)
		{
			LOG.warn("invalid input");
			return clientList;
		}

		final List<Client> list = new ArrayList<Client>();
		final Key<Trainer> key = getTrainerKey(trainerId);

		for (Client c : clientList)
		{
			if (c.getTrainerKey().equals(key))
			{
				list.add(c);
			}
		}

		return list;
	}

	public List<Client> getClientListForLocation(Long locationId, SortParam sort)
	{
		final List<Client> list = new ArrayList<Client>();

		if (locationId != null)
		{
			final Key<Location> key = getLocationKey(locationId);

			for (Client c : clientList)
			{
				if (c.getLocationKey().equals(key))
				{
					list.add(c);
				}
			}
		}
		else
		{
			list.addAll(clientList);
		}

		if (LOG.isTraceEnabled())
		{
			if (list != null)
			{
				if (locationId != null)
				{
					LOG.trace(locationId + " has " + list.size() + " clients");
				}
				else
				{
					LOG.trace("All locations have " + list.size() + " clients");
				}
			}
		}

		if (sort == null)
		{
			return list;
		}

		if (sort.getProperty().equals(Client.NAME_LAST_PROPERTY))
		{
			if (sort.isAscending())
			{
				Collections.sort(list, ComparatorFactory.LAST_NAME_ASC);
			}
			else
			{
				Collections.sort(list, ComparatorFactory.LAST_NAME_DES);
			}
		}
		else if (sort.getProperty().equals(Client.NAME_FIRST_PROPERTY))
		{
			if (sort.isAscending())
			{
				Collections.sort(list, ComparatorFactory.FIRST_NAME_ASC);
			}
			else
			{
				Collections.sort(list, ComparatorFactory.FIRST_NAME_DES);
			}
		}

		return list;
	}

	public List<Client> getClientListForLocation(Long locationId, SortParam sort, int first, int count)
	{
		return getClientListForLocation(locationId, sort).subList(first, first + count);
	}

	/**
	 * Private constructor for singleton implementation.
	 */
	private StorageUtils()
	{
		updateLocationList();
		updateTrainerList();
		updateClientList();
	}

	public synchronized void updateTrainerList()
	{
		if (!trainerList.isEmpty())
		{
			for (Trainer t : trainerList)
			{
				trainerList.remove(t);
			}
		}

		final Query<Trainer> trainers = dao.ofy().query(Trainer.class);

		if (LOG.isTraceEnabled())
		{
			if (trainers != null)
			{
				LOG.trace("Number of trainers retrieved: " + trainers.count());
			}
		}

		trainerList.addAll(trainers.list());
	}

	public synchronized void updateClientList()
	{
		if (!clientList.isEmpty())
		{
			for (Client c : clientList)
			{
				clientList.remove(c);
			}
		}

		final Query<Client> clients = dao.ofy().query(Client.class);

		if (LOG.isTraceEnabled())
		{
			if (clients != null)
			{
				LOG.trace("Number of clients retrieved: " + clients.count());
			}
		}

		clientList.addAll(clients.list());
	}

	public synchronized void updateLocationList()
	{
		if (!locationList.isEmpty())
		{
			for (Location l : locationList)
			{
				locationList.remove(l);
			}
		}

		final Query<Location> locations = dao.ofy().query(Location.class);

		if (LOG.isTraceEnabled())
		{
			if (locations != null)
			{
				LOG.trace("Number of locations retrieved: " + locations.count());
			}
		}

		locationList.addAll(locations.list());
	}

	public Key<Location> getLocationKey(Long id)
	{
		return new Key<Location>(Location.class, id);
	}

	public Key<Trainer> getTrainerKey(Long id)
	{
		return new Key<Trainer>(Trainer.class, id);
	}

	public Key<Client> getClientKey(Long id)
	{
		return new Key<Client>(Client.class, id);
	}

	public Key<TrainingPackage> getPackageKey(Long id)
	{
		return new Key<TrainingPackage>(TrainingPackage.class, id);
	}

	public Key<TrainingSession> getSessionKey(Long id)
	{
		return new Key<TrainingSession>(TrainingSession.class, id);
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////////

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
		final Query<TrainingSession> sessions = dao.ofy().query(TrainingSession.class).filter("client.id =", client.getId()).filter("client.dateTrained >=", start)
				.filter("client.dateTrained <=", end);

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
