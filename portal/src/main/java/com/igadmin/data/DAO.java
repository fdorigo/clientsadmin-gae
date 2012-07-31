package com.igadmin.data;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.igadmin.auth.User;

public class DAO extends DAOBase
{
	static {
        ObjectifyService.register(Location.class);
        ObjectifyService.register(Trainer.class);
        ObjectifyService.register(Client.class);
        ObjectifyService.register(Address.class);
        ObjectifyService.register(Phone.class);
        ObjectifyService.register(GymPackage.class);
        ObjectifyService.register(GymSession.class);
        ObjectifyService.register(User.class);
   }

    public Location getOrCreateLocation(long id)
    {
        Location found = ofy().find(Location.class, id);
        
        if (found == null)
            return new Location();
        else
            return found;
    }

    public Trainer getOrCreateTrainer(long id)
    {
    	Trainer found = ofy().find(Trainer.class, id);
        
    	if (found == null)
            return new Trainer();
        else
            return found;
    }

	public Client getOrCreateClient(long id)
	{
		Client found = ofy().find(Client.class, id);
		
		if (found == null)
			return new Client();
		else return found;
	}
}
