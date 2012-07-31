package com.igadmin.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Id;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.NotSaved;
import com.googlecode.objectify.annotation.Unindexed;

@Entity
@Unindexed
public class Trainer implements Serializable
{
	private static final long	serialVersionUID	= -7246007345082461830L;
	
	public static final String ADDRESS_APT_PROPERTY = "addressApt";
    public static final String ADDRESS_CITY_PROPERTY = "addressCity";
    public static final String ADDRESS_NUM_PROPERTY = "addressNum";
    public static final String ADDRESS_STATE_PROPERTY = "addressState";
    public static final String ADDRESS_STREET_PROPERTY = "addressStreet";
    public static final String ADDRESS_ZIP_PROPERTY = "addressZip";
    
    public static final String COMP_RATE_PROPERTY = "compRate";
    public static final String EMAIL_ADDRESS_PROPERTY = "emailAddress";
    public static final String NAME_FIRST_PROPERTY = "nameFirst";
    public static final String NAME_LAST_PROPERTY = "nameLast";
    public static final String NAME_MIDDLE_PROPERTY = "nameMiddle";
    public static final String PHONE_PRIMARY_PROPERTY = "phonePrimary";
    public static final String PHONE_SECONDARY_PROPERTY = "phoneSecondary";
    public static final String CLIENTS_PROPERTY = "clients";
    public static final String LOCATION_PROPERTY = "location";
    public static final String SESSION_ARRAY_PROPERTY = "sessionArray";
    
    @Indexed private String nameFirst;
    private String nameMiddle;
    @Indexed private String nameLast;
    
    private String addressNum;
    private String addressStreet;
    private String addressApt;
    private String addressCity;
    private String addressState;
    private String addressZip;
        
    @Indexed private String emailAddress;
    private String phonePrimary;
    private String phoneSecondary;
    
    private Double compRate;
    
    @Embedded private Location location;
    @NotSaved private List<Client> clients;
    @NotSaved private List<GymSession> sessions;
    
	@Id
	private Long id;
    
    
    @Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
	public String getName()
	{
		return this.nameFirst + " " + this.nameLast;
	}
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}

	public String getNameFirst()
	{
		return nameFirst;
	}
	public void setNameFirst(String nameFirst)
	{
		this.nameFirst = nameFirst;
	}
	public String getNameMiddle()
	{
		return nameMiddle;
	}
	public void setNameMiddle(String nameMiddle)
	{
		this.nameMiddle = nameMiddle;
	}
	public String getNameLast()
	{
		return nameLast;
	}
	public void setNameLast(String nameLast)
	{
		this.nameLast = nameLast;
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
	public String getEmailAddress()
	{
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}
	public String getPhonePrimary()
	{
		return phonePrimary;
	}
	public void setPhonePrimary(String phonePrimary)
	{
		this.phonePrimary = phonePrimary;
	}
	public String getPhoneSecondary()
	{
		return phoneSecondary;
	}
	public void setPhoneSecondary(String phoneSecondary)
	{
		this.phoneSecondary = phoneSecondary;
	}
	public Double getCompRate()
	{
		return compRate;
	}
	public void setCompRate(Double compRate)
	{
		this.compRate = compRate;
	}
	public Location getLocation()
	{
		return location;
	}
	public void setLocation(Location location)
	{
		this.location = location;
	}
	public List<Client> getClients()
	{
		return clients;
	}
	public void setClients(List<Client> clients)
	{
		this.clients = clients;
	}
	public List<GymSession> getSessions()
	{
		return sessions;
	}
	public void setSessions(List<GymSession> sessions)
	{
		this.sessions = sessions;
	}
}
