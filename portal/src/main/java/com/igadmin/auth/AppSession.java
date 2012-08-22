package com.igadmin.auth;

import org.apache.log4j.Logger;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import com.igadmin.data.DAO;
import com.igadmin.data.Location;
import com.igadmin.data.utils.StorageUtils;

public class AppSession extends AuthenticatedWebSession
{
	private static final Logger LOG = Logger.getLogger(AppSession.class);
	private static final long serialVersionUID = 1878540573259244794L;
	private User loggedInUser = null;

	public AppSession(final Request request)
	{
		super(request);
	}
	
	public Location getSessionLocation()
	{
		if (loggedInUser == null)
		{
			throw new RestartResponseException(LoginPage.class);
		}
		
		/* Admin user can access any location */
		if (loggedInUser.getRole().hasRole("ADMIN"))
		{
			return null;
		}
		
		return new DAO().ofy().get(loggedInUser.getLocationKey());
	}
	
	@Override
	public void invalidate()
	{
		super.invalidate();		
		this.signOut();
	}

	@Override
	public boolean authenticate(final String username, final String password)
	{
		User user = null;
		
		if (username.equals("guest") == false)
		{
			user = StorageUtils.getUserFromDatabase(username, password);
		}
		else
		{
			if (password.equals("guest"))
			{
				user = new User(username, password, "ADMIN", null);
			}
		}
		
		boolean success = user == null ? false : true;

		if (success)
		{
			loggedInUser = user;
			LOG.debug("Successful login for user: " + loggedInUser.getUsername());
		}

		return success;
	}

	@Override
	public Roles getRoles()
	{
		if (this.isSignedIn())
		{
			return loggedInUser.getRole();
		}
		
		return null;
	}
}
