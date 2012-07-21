package com.igadmin.data;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;

@Entity
@Unindexed
public class GymPackage implements Serializable
{
	private static final long	serialVersionUID	= -225102796207461555L;

	public static final String	DATE_PROPERTY			= "date";
	public static final String	MAX_SESSIONS_PROPERTY	= "maxSessions";
	public static final String	PRICE_PROPERTY			= "price";
	public static final String	CLIENT_PROPERTY			= "client";
	public static final String	SESSIONS_PROPERTY		= "sessions";

	@Id
	private Long id;
    
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}


}
