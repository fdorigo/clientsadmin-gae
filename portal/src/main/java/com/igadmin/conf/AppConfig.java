package com.igadmin.conf;

import java.util.NoSuchElementException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

public class AppConfig
{
	private static final Logger	LOG					= Logger.getLogger(AppConfig.class);
	private Configuration		conf				= null;
	public static final String	CREATE_TEST_VALUES	= "create-test-values";

	public boolean useTestValues()
	{
		boolean retVal;

		try
		{
			retVal = conf.getBoolean(CREATE_TEST_VALUES);
		}
		catch (NoSuchElementException e)
		{
			LOG.warn("Property: " + CREATE_TEST_VALUES + " is not configured");
			retVal = false;
		}
		
		return retVal;
	}

	private void loadConfiguration()
	{
		try
		{
			conf = new XMLConfiguration(System.getProperty("igadmin.properties"));
		}
		catch (ConfigurationException e)
		{
			LOG.error(e.getMessage());
		}
	}

	/**
	 * Private default constructor to implement singleton pattern.
	 */
	private AppConfig()
	{
		loadConfiguration();
	}

	private static class ConfigurationSingleton
	{
		public static final AppConfig	INSTANCE	= new AppConfig();
	}

	public static AppConfig getInstance()
	{
		return ConfigurationSingleton.INSTANCE;
	}

}
