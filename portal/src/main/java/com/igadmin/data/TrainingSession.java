package com.igadmin.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@Entity
@Unindexed
public class TrainingSession implements Serializable
{
	private static final long	serialVersionUID	= 5860005841461802814L;

	public static final String DATE_PROPERTY = "trainingDate";
    public static final String CLIENT_PROPERTY = "clientKey";
    public static final String PACKAGE_PROPERTY = "packageKey";
    public static final String TRAINER_PROPERTY = "trainerKey";
    public static final String PRICE_PROPERTY = "sessionPrice";
    public static final String TRAINER_RATE_PROPERTY = "trainerRate";
    public static final String TRAINING_TYPE_PROPERTY = "trainingType";

    @Indexed private Date trainedDate;
    @Indexed private Key<Client> clientKey;
    @Indexed private Key<TrainingPackage> packageKey;
    @Indexed private Key<Trainer> trainerKey;
    private Double sessionPrice;
    private Double trainerRate;
    @Indexed private TrainingSessionType trainingType;
    
    
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
	
    //Properties Getters and Setters
	public Key<Trainer> getTrainerKey()
	{
		return trainerKey;
	}
	public void setTrainerKey(Key<Trainer> trainer)
	{
		this.trainerKey = trainer;
	}
	public Key<Client> getClientKey()
	{
		return clientKey;
	}
	public void setClientKey(Key<Client> client)
	{
		this.clientKey = client;
	}
	public Double getTrainerRate()
	{
		return trainerRate;
	}
	public void setTrainerRate(Double trainerRate)
	{
		this.trainerRate = trainerRate;
	}
	public Date getTrainedDate()
	{
		return trainedDate;
	}
	public void setTrainedDate(Date trainedDate)
	{
		this.trainedDate = trainedDate;
	}
	public Key<TrainingPackage> getPackageKey()
	{
		return packageKey;
	}
	public void setPackageKey(Key<TrainingPackage> packageKey)
	{
		this.packageKey = packageKey;
	}
	public Double getSessionPrice()
	{
		return sessionPrice;
	}
	public void setSessionPrice(Double sessionPrice)
	{
		this.sessionPrice = sessionPrice;
	}
	public TrainingSessionType getTrainingType()
	{
		return trainingType;
	}
	public void setTrainingType(TrainingSessionType trainingType)
	{
		this.trainingType = trainingType;
	}
}
