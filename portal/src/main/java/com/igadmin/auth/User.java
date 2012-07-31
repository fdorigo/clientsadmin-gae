package com.igadmin.auth;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;
import com.igadmin.data.Location;

@Entity
@Unindexed
public class User implements Serializable
{
	private static final long	serialVersionUID	= -5462992047551850520L;

	@Id
	private Long				id;

	@Indexed
	private Key<Location>		locationKey;

	@Indexed
	private String				role;

	@Indexed
	private String				username;

	private String				password;

	public User()
	{
	}

	public User(final String user, final String pass, final String role, final Key<Location> key)
	{
		this.username = user;
		this.password = pass;
		this.locationKey = key;
		this.role = role;
	}
	
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(final String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(final String password)
	{
		this.password = password;
	}

	public Key<Location> getLocationKey()
	{
		return locationKey;
	}

	public void setLocationKey(final Key<Location> key)
	{
		this.locationKey = key;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public Roles getRole()
	{
		Roles retVal = null;

		if (role.equals("ADMIN"))
		{
			retVal = new Roles("ADMIN, MANAGER, TRAINER, USER");
		}
		else if (role.equals("MANAGER"))
		{
			retVal = new Roles("MANAGER, TRAINER, USER");
		}
		else if (role.equals("TRAINER"))
		{
			retVal = new Roles("TRAINER, USER");
		}
		else if (role.equals("USER"))
		{
			retVal = new Roles("USER");
		}
		
		return retVal;
	}

	public void setRole(final Roles role)
	{
		if (role.hasRole("ADMIN"))
		{
			this.role = "ADMIN";
		}
		else if (role.hasRole("MANAGER"))
		{
			this.role = "MANAGER";
		}
		else if (role.hasRole("TRAINER"))
		{
			this.role = "TRAINER";
		}
		else if (role.hasRole("USER"))
		{
			this.role = "USER";
		}
		else 
		{
			this.role = "UNKOWN";
		}
	}
}
