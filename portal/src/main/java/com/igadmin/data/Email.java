package com.igadmin.data;

import java.io.Serializable;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.Id;

import org.apache.log4j.Logger;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;
import com.igadmin.exceptions.InvalidEmailException;

@Entity
@Unindexed
public class Email implements Serializable
{
	private static final long	serialVersionUID	= -4438536682546580324L;
	private static final Logger	LOG					= Logger.getLogger(Email.class);

	@Indexed
	private String				email				= "";

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

	public Email()
	{

	}

	public Email(String em)
	{
		if (em != null && isValid(em))
		{
			this.email = em;
		}
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String em) throws InvalidEmailException
	{
		if (!isValid(em))
		{
			throw new InvalidEmailException();
		}

		this.email = em;
	}

	private boolean isValid(String em)
	{
		boolean result = true;

		try
		{
			InternetAddress emailAddr = new InternetAddress(em);
			emailAddr.validate();
		}
		catch (AddressException ex)
		{
			LOG.warn("Email is invalid: " + em);
			result = false;
		}

		return result;
	}
}
