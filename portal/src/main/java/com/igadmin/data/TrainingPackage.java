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
public class TrainingPackage implements Serializable
{
	private static final long	serialVersionUID		= -225102796207461555L;

	public static final String	CLIENT_PROPERTY			= "clientKey";
	public static final String	TRAINER_PROPERTY		= "trainerKey";
	public static final String	DATE_PROPERTY			= "purchaseDate";
	public static final String	PRICE_PROPERTY			= "packagePrice";
	public static final String	PRICE_SESSIONS_PROPERTY	= "priceSession";
	public static final String	USED_SESSIONS_PROPERTY	= "usedSessions";
	public static final String	MAX_SESSIONS_PROPERTY	= "maxSessions";
	public static final String	PACKAGE_BALANCE			= "packageBalance";
	public static final String	PAYMENT_TYPE			= "paymentType";
	public static final String	CHECK_NUMBER			= "checkNumber";

	@Indexed
	private Key<Client>			clientKey;
	@Indexed
	private Key<Trainer>		trainerKey;
	@Indexed
	private Date				purchaseDate;
	private Double				packagePrice;
	private Double				priceSession;
	private int					usedSessions;
	private int					maxSessions;
	private Double				packageBalance;
	@Indexed
	private PackagePaymentType	paymentType;
	private String				checkNumber;

	@Id
	private Long				id;

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Key<Client> getClientKey()
	{
		return clientKey;
	}

	public void setClientKey(Key<Client> clientKey)
	{
		this.clientKey = clientKey;
	}

	public Key<Trainer> getTrainerKey()
	{
		return trainerKey;
	}

	public void setTrainerKey(Key<Trainer> trainerKey)
	{
		this.trainerKey = trainerKey;
	}

	public Date getPurchaseDate()
	{
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate)
	{
		this.purchaseDate = purchaseDate;
	}

	public Double getPackagePrice()
	{
		return packagePrice;
	}

	public void setPackagePrice(Double packagePrice)
	{
		this.packagePrice = packagePrice;
	}

	public Double getPriceSession()
	{
		return priceSession;
	}

	public void setPriceSession(Double priceSession)
	{
		this.priceSession = priceSession;
	}

	public int getUsedSessions()
	{
		return usedSessions;
	}

	public void setUsedSessions(int usedSessions)
	{
		this.usedSessions = usedSessions;
	}

	public int getMaxSessions()
	{
		return maxSessions;
	}

	public void setMaxSessions(int maxSessions)
	{
		this.maxSessions = maxSessions;
	}

	public Double getPackageBalance()
	{
		return packageBalance;
	}

	public void setPackageBalance(Double packageBalance)
	{
		this.packageBalance = packageBalance;
	}

	public String getCheckNumber()
	{
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber)
	{
		this.checkNumber = checkNumber;
	}

	public PackagePaymentType getPaymentType()
	{
		return paymentType;
	}

	public void setPaymentType(PackagePaymentType paymentType)
	{
		this.paymentType = paymentType;
	}
}
