package com.igadmin.data.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class PhoneUtils
{
	private static final Logger LOG = LoggerFactory.getLogger(PhoneUtils.class);
	
	public static String parseNumber(String number)
	{
		String parsedNumber;
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		
		try
		{
			PhoneNumber numberProto = phoneUtil.parse(number, "US");
			parsedNumber = phoneUtil.format(numberProto, PhoneNumberFormat.INTERNATIONAL);
		}
		catch (NumberParseException e)
		{
			LOG.trace("NumberParseException was thrown: " + number);
			parsedNumber = number;
		}
		
		return parsedNumber;
	}
}
