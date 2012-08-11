package com.igadmin.data;

import java.io.Serializable;

import javax.persistence.Id;

import org.apache.log4j.Logger;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;

@Entity
@Unindexed
public class Phone implements Serializable
{
	private static final long serialVersionUID = -7227906431599297832L;
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(Phone.class);
	
	private String number;
	private String extension;

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

	public Phone()
	{
		this.number = "";
		this.extension = "";
	}

	public Phone(String number)
	{
		this.number = number;
		this.extension = "";
	}

	public Phone(String number, String extension)
	{
		this.number = number;
		this.extension = extension;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append(this.number);

		if (this.extension != null && this.extension.length() > 0)
		{
			sb.append(" ext: ");
			sb.append(this.extension);
		}

		return sb.toString();
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getExtension()
	{
		return extension;
	}

	public void setExtension(String extension)
	{
		this.extension = extension;
	}
}
