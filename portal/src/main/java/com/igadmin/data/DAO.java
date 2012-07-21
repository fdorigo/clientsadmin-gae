package com.igadmin.data;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;

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
   }

    public Location getOrCreateLocation(long id)
    {
        Location found = ofy().find(Location.class, id);
        if (found == null)
            return new Location();
        else
            return found;
    }
}
