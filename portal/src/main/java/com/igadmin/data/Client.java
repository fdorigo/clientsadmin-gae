package com.igadmin.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Client implements Serializable
{
	private static final long	serialVersionUID	= 7573328143738817326L;
	
    public static final String NAME_FIRST_PROPERTY = "nameFirst";
    public static final String NAME_LAST_PROPERTY = "nameLast";
    public static final String NAME_MIDDLE_PROPERTY = "nameMiddle";

    public static final String ADDRESS_APT_PROPERTY = "addressApt";
    public static final String ADDRESS_CITY_PROPERTY = "addressCity";
    public static final String ADDRESS_NUM_PROPERTY = "addressNum";
    public static final String ADDRESS_STATE_PROPERTY = "addressState";
    public static final String ADDRESS_STREET_PROPERTY = "addressStreet";
    public static final String ADDRESS_ZIP_PROPERTY = "addressZip";
    
    public static final String DATE_BIRTH_PROPERTY = "dateBirth";
    public static final String DATE_JOINED_PROPERTY = "dateJoined";

    public static final String EMAIL_ADDRESS_PROPERTY = "emailAddress";
    public static final String PHONE_PRIMARY_PROPERTY = "phonePrimary";
    public static final String PHONE_SECONDARY_PROPERTY = "phoneSecondary";

    public static final String LOCATION_PROPERTY = "location";
    public static final String PACKAGES_PROPERTY = "packages";
    public static final String SESSIONS_PROPERTY = "sessions";
    public static final String TRAINER_PROPERTY = "trainer";

    private String nameFirst;
    private String nameMiddle;
    private String nameLast;
    
    private String addressNum;
    private String addressStreet;
    private String addressApt;
    private String addressCity;
    private String addressState;
    private String addressZip;
    
    private Date dateBirth;
    private Date dateJoined;
    
    private String emailAddress;
    private String phonePrimary;
    private String phoneSecondary;
    
    private Location location;
    private Trainer trainer;
    private List<GymPackage> packages;
    private List<GymSession> sessions;

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
	public Date getDateBirth()
	{
		return dateBirth;
	}
	public void setDateBirth(Date dateBirth)
	{
		this.dateBirth = dateBirth;
	}
	public Date getDateJoined()
	{
		return dateJoined;
	}
	public void setDateJoined(Date dateJoined)
	{
		this.dateJoined = dateJoined;
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
	public Location getLocation()
	{
		return location;
	}
	public void setLocation(Location location)
	{
		this.location = location;
	}
	public Trainer getTrainer()
	{
		return trainer;
	}
	public void setTrainer(Trainer trainer)
	{
		this.trainer = trainer;
	}
	public List<GymPackage> getPackages()
	{
		return packages;
	}
	public void setPackages(List<GymPackage> packages)
	{
		this.packages = packages;
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
