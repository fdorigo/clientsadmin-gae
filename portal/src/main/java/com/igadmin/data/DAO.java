package com.igadmin.data;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.igadmin.auth.User;

public class DAO extends DAOBase
{
	static
	{
		ObjectifyService.register(Location.class);
		ObjectifyService.register(Trainer.class);
		ObjectifyService.register(Client.class);
		ObjectifyService.register(Address.class);
		ObjectifyService.register(Phone.class);
		ObjectifyService.register(TrainingPackage.class);
		ObjectifyService.register(TrainingSession.class);
		ObjectifyService.register(User.class);
	}

	public Location getOrCreateLocation(Key<Location> locationKey)
	{
		Location found = ofy().find(locationKey);

		if (found == null)
			return new Location();
		else
			return found;
	}

	public Location getOrCreateLocation(long id)
	{
		if (id == 0)
		{
			return new Location();
		}

		Location found = ofy().find(Location.class, id);

		if (found == null)
			return new Location();
		else
			return found;
	}

	public Trainer getOrCreateTrainer(Key<Trainer> trainerKey)
	{
		Trainer found = ofy().find(trainerKey);

		if (found == null)
			return new Trainer();
		else
			return found;
	}

	public Trainer getOrCreateTrainer(long id)
	{
		if (id == 0)
		{
			return new Trainer();
		}

		Trainer found = ofy().find(Trainer.class, id);

		if (found == null)
			return new Trainer();
		else
			return found;
	}

	public Client getOrCreateClient(Key<Client> clientKey)
	{
		Client found = ofy().find(clientKey);

		if (found == null)
			return new Client();
		else
			return found;
	}

	public Client getOrCreateClient(Long id)
	{
		if (id == 0)
		{
			return new Client();
		}

		Client found = ofy().find(Client.class, id);

		if (found == null)
			return new Client();
		else
			return found;
	}
}
