package com.igadmin.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;

@Entity
@Unindexed
public class Location implements Serializable
{
	private static final long	serialVersionUID	= -4886489072359582991L;

	public static final String	ADDRESS_APT_PROPERTY		= "addressApt";
	public static final String	ADDRESS_CITY_PROPERTY		= "addressCity";
	public static final String	ADDRESS_NUM_PROPERTY		= "addressNum";
	public static final String	ADDRESS_STATE_PROPERTY		= "addressState";
	public static final String	ADDRESS_STREET_PROPERTY		= "addressStreet";
	public static final String	ADDRESS_ZIP_PROPERTY		= "addressZip";
	public static final String	MGR_NAME_FIRST_PROPERTY		= "mgrNameFirst";
	public static final String	MGR_NAME_LAST_PROPERTY		= "mgrNameLast";
	public static final String	NAME_PROPERTY				= "name";
	public static final String	OPENING_DATE_PROPERTY		= "openingDate";
	public static final String	PHONE_PRIMARY_PROPERTY		= "phonePrimary";
	public static final String	PHONE_SECONDARY_PROPERTY	= "phoneSecondary";
	public static final String	EMAIL_ADDRESS				= "emailAddress";

	public static final String	TRAINER_ID_PROPERTY			= "trainerId";
	public static final String	CLIENTS_PROPERTY			= "clients";
	public static final String	TO_TRAINER_PROPERTY			= "toTrainer";
	public static final String	TRAINERS_PROPERTY			= "trainers";
	
	@Id
	private Long id;
	
    private String locationName;

    private String mgrNameFirst;
    private String mgrNameLast;
    
    private String addressNum;
    private String addressStreet;
    private String addressApt;
    private String addressCity;
    private String addressState;
    private String addressZip;
    
    private Date openingDate;
    
    private String emailAddress;
    @Embedded private Phone phonePrimary;
    @Embedded private Phone phoneSecondary;
    
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getLocationName()
	{
		return locationName;
	}
	public void setLocationName(String locationName)
	{
		this.locationName = locationName;
	}
	public String getMgrNameFirst()
	{
		return mgrNameFirst;
	}
	public void setMgrNameFirst(String mgrNameFirst)
	{
		this.mgrNameFirst = mgrNameFirst;
	}
	public String getMgrNameLast()
	{
		return mgrNameLast;
	}
	public void setMgrNameLast(String mgrNameLast)
	{
		this.mgrNameLast = mgrNameLast;
	}
	public String getAddressNum()
	{
		return addressNum;
	}
	public void setAddressNum(String addressNum)
	{
		this.addressNum = addressNum;
	}
	public String getAddressStreet()
	{
		return addressStreet;
	}
	public void setAddressStreet(String addressStreet)
	{
		this.addressStreet = addressStreet;
	}
	public String getAddressApt()
	{
		return addressApt;
	}
	public void setAddressApt(String addressApt)
	{
		this.addressApt = addressApt;
	}
	public String getAddressCity()
	{
		return addressCity;
	}
	public void setAddressCity(String addressCity)
	{
		this.addressCity = addressCity;
	}
	public String getAddressState()
	{
		return addressState;
	}
	public void setAddressState(String addressState)
	{
		this.addressState = addressState;
	}
	public String getAddressZip()
	{
		return addressZip;
	}
	public void setAddressZip(String addressZip)
	{
		this.addressZip = addressZip;
	}
	public Date getOpeningDate()
	{
		return openingDate;
	}
	public void setOpeningDate(Date openingDate)
	{
		this.openingDate = openingDate;
	}
	public String getEmailAddress()
	{
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}
	public Phone getPhonePrimary()
	{
		return phonePrimary;
	}
	public void setPhonePrimary(Phone phonePrimary)
	{
		this.phonePrimary = phonePrimary;
	}
	public Phone getPhoneSecondary()
	{
		return phoneSecondary;
	}
	public void setPhoneSecondary(Phone phoneSecondary)
	{
		this.phoneSecondary = phoneSecondary;
	}
}
