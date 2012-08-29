package com.igadmin.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.google.appengine.api.datastore.PhoneNumber;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.NotSaved;
import com.googlecode.objectify.annotation.Unindexed;

@Entity
@Unindexed
public class Client implements Serializable, GymComparable
{
	private static final long	serialVersionUID			= 7573328143738817326L;

	public static final String	NAME_FIRST_PROPERTY			= "nameFirst";
	public static final String	NAME_LAST_PROPERTY			= "nameLast";
	public static final String	NAME_MIDDLE_PROPERTY		= "nameMiddle";

	public static final String	ADDRESS_APT_PROPERTY		= "addressApt";
	public static final String	ADDRESS_CITY_PROPERTY		= "addressCity";
	public static final String	ADDRESS_NUM_PROPERTY		= "addressNum";
	public static final String	ADDRESS_STATE_PROPERTY		= "addressState";
	public static final String	ADDRESS_STREET_PROPERTY		= "addressStreet";
	public static final String	ADDRESS_ZIP_PROPERTY		= "addressZip";

	public static final String	DATE_BIRTH_PROPERTY			= "dateBirth";
	public static final String	DATE_JOINED_PROPERTY		= "dateJoined";

	public static final String	EMAIL_ADDRESS_PROPERTY		= "emailAddress";
	public static final String	PHONE_PRIMARY_PROPERTY		= "phonePrimary";
	public static final String	PHONE_SECONDARY_PROPERTY	= "phoneSecondary";

	public static final String	NAME_EMERGENCY_PROPERTY		= "emergencyName";
	public static final String	PHONE_EMERGENCY_PROPERTY	= "emergencyPhone";

	public static final String	LOCATION_PROPERTY			= "locationKey";
	public static final String	TRAINER_PROPERTY			= "trainerKey";
	public static final String	PACKAGES_PROPERTY			= "packageKeys";
	public static final String	SESSIONS_PROPERTY			= "sessionKeys";

	public static final String	NOTES_PROPERTY				= "notes";
	public static final String	GENDER_PROPERTY				= "gender";

	@Indexed
	private String				nameFirst;
	private String				nameMiddle;

	@Indexed
	private String				nameLast;

	private String				addressNum;
	private String				addressStreet;
	private String				addressApt;
	private String				addressCity;
	private String				addressState;
	private String				addressZip;

	private Date				dateBirth;
	private Date				dateJoined;

	@Indexed
	private String				emailAddress;
	private PhoneNumber			phonePrimary;
	private PhoneNumber			phoneSecondary;

	private String				emergencyName;
	private PhoneNumber			emergencyPhone;

	@Indexed
	private String				gender;
	private String				notes;

	@Indexed
	private Key<Location>		locationKey;
	@Indexed
	private Key<Trainer>		trainerKey;

	@NotSaved
	private String				clientDisplayName;

	@Id
	private Long				id;

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
		final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(this);

		reflectionToStringBuilder.setExcludeFieldNames(new String[] { "emergencyPhone", "phonePrimary", "phoneSecondary", "nameMiddle", "clientKeys", "trainingSessionKeys", "trainingPackageKeys",
				"trainingSessionKeys" });

		return reflectionToStringBuilder.toString();
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

	public PhoneNumber getPhonePrimary()
	{
		return phonePrimary;
	}

	public void setPhonePrimary(PhoneNumber phonePrimary)
	{
		this.phonePrimary = phonePrimary;
	}

	public PhoneNumber getPhoneSecondary()
	{
		return phoneSecondary;
	}

	public void setPhoneSecondary(PhoneNumber phoneSecondary)
	{
		this.phoneSecondary = phoneSecondary;
	}

	public Key<Location> getLocationKey()
	{
		return locationKey;
	}

	public void setLocationKey(Key<Location> location)
	{
		this.locationKey = location;
	}

	public String getName()
	{
		return this.nameFirst + " " + this.nameLast;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getEmergencyName()
	{
		return emergencyName;
	}

	public void setEmergencyName(String emergencyName)
	{
		this.emergencyName = emergencyName;
	}

	public PhoneNumber getEmergencyPhone()
	{
		return emergencyPhone;
	}

	public void setEmergencyPhone(PhoneNumber emergencyPhone)
	{
		this.emergencyPhone = emergencyPhone;
	}

	public String getNotes()
	{
		return notes;
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	public Key<Trainer> getTrainerKey()
	{
		return trainerKey;
	}

	public void setTrainerKey(Key<Trainer> trainerKey)
	{
		this.trainerKey = trainerKey;
	}

	public String getClientDisplayName()
	{
		this.clientDisplayName = this.nameFirst + " " + this.nameLast;
		return this.clientDisplayName;
	}
}
