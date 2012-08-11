package com.igadmin.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;
import com.igadmin.annotation.WorkInProgress;

@Entity
@Unindexed
@WorkInProgress
public class GymSession implements Serializable
{
	private static final long	serialVersionUID	= 5860005841461802814L;

	public static final String DATE_PROPERTY = "date";
    public static final String PRICE_PROPERTY = "price";
    public static final String TYPE_PROPERTY = "type";
    public static final String CLIENT_PROPERTY = "client";
    public static final String PACKAGE_PROPERTY = "package";

    private Key<Long> trainer;
    private Key<Long> client;
    private Double price;
    private Double trainerRate;
    @Indexed private Date trainedDate;
    
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
    
    @Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
	public Date getTrainedDate()
	{
		return trainedDate;
	}
	public void setTrainedDate(Date trainedDate)
	{
		this.trainedDate = trainedDate;
	}


}
