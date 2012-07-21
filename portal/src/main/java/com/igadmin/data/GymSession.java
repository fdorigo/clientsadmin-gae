package com.igadmin.data;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;

@Entity
@Unindexed
public class GymSession implements Serializable
{
	private static final long	serialVersionUID	= 5860005841461802814L;

	public static final String DATE_PROPERTY = "date";
    public static final String PRICE_PROPERTY = "price";
    public static final String TYPE_PROPERTY = "type";
    public static final String CLIENT_PROPERTY = "client";
    public static final String PACKAGE_PROPERTY = "package";
    public static final String TO_TRAINER_PROPERTY = "toTrainer";
	
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
