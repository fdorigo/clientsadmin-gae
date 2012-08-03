package com.igadmin.form;

import java.util.ArrayList;
import java.util.List;

import com.igadmin.data.Location;
import com.igadmin.data.Trainer;
import com.igadmin.data.utils.StorageUtils;

public class FormUtils
{
	public static List<SelectOption> initTrainerOptionList(final Location location)
	{
		final List<Trainer> trainers = StorageUtils.getTrainerListForLocation(location);
		final List<SelectOption> options = new ArrayList<SelectOption>(trainers.size());
		
		for (Trainer t : trainers)
		{
			options.add(new SelectOption(t.getId().toString(), t.getName()));
		}
		
		return options;
	}

	public static List<SelectOption> initLocationOptionList()
	{
		final List<SelectOption> options = new ArrayList<SelectOption>();
		final List<Location> locations = StorageUtils.getLocationList();
		
		for (Location l : locations)
		{
			options.add(new SelectOption(l.getId().toString(), l.getLocationName()));
		}
		
		return options;
	}
	
	public static List<SelectOption> initClientOptionList(Location location)
	{
		//TODO get list from DB
		return new ArrayList<SelectOption>();
	}
	
	public static List<SelectOption> initStateOptionList()
	{
		final List<SelectOption> states = new ArrayList<SelectOption>();
		
		states.add(new SelectOption("AL", "Alabama"));
		states.add(new SelectOption("AK", "Alaska"));
		states.add(new SelectOption("AZ", "Arizona"));
		states.add(new SelectOption("AR", "Arkansas"));
		states.add(new SelectOption("CA", "California"));
		states.add(new SelectOption("CO", "Colorado"));
		states.add(new SelectOption("CT", "Connecticut"));
		states.add(new SelectOption("DE", "Delaware"));
		states.add(new SelectOption("DC", "District of Columbia"));
		states.add(new SelectOption("FL", "Florida"));
		states.add(new SelectOption("GA", "Georgia"));
		states.add(new SelectOption("HI", "Hawaii"));
		states.add(new SelectOption("ID", "Idaho"));
		states.add(new SelectOption("IL", "Illinois"));
		states.add(new SelectOption("IN", "Indiana"));
		states.add(new SelectOption("IA", "Iowa"));
		states.add(new SelectOption("KS", "Kansas"));
		states.add(new SelectOption("KY", "Kentucky"));
		states.add(new SelectOption("LA", "Louisiana"));
		states.add(new SelectOption("ME", "Maine"));
		states.add(new SelectOption("MD", "Maryland"));
		states.add(new SelectOption("MA", "Massachusetts"));
		states.add(new SelectOption("MI", "Michigan"));
		states.add(new SelectOption("MN", "Minnesota"));
		states.add(new SelectOption("MS", "Mississippi"));
		states.add(new SelectOption("MO", "Missouri"));
		states.add(new SelectOption("MT", "Montana"));
		states.add(new SelectOption("NE", "Nebraska"));
		states.add(new SelectOption("NV", "Nevada"));
		states.add(new SelectOption("NH", "New Hempshire"));
		states.add(new SelectOption("NJ", "New Jersey"));
		states.add(new SelectOption("NM", "New Mexico"));
		states.add(new SelectOption("NY", "New York"));
		states.add(new SelectOption("NC", "North Carolina"));
		states.add(new SelectOption("ND", "North Dakota"));
		states.add(new SelectOption("OH", "Ohio"));
		states.add(new SelectOption("OK", "Oklahoma"));
		states.add(new SelectOption("OR", "Oregon"));
		states.add(new SelectOption("PA", "Pennsylvania"));
		states.add(new SelectOption("RI", "Rhode Island"));
		states.add(new SelectOption("SC", "South Carolina"));
		states.add(new SelectOption("SD", "South Dakota"));
		states.add(new SelectOption("TN", "Tennessee"));
		states.add(new SelectOption("TX", "Texas"));
		states.add(new SelectOption("UT", "Utah"));
		states.add(new SelectOption("VT", "Vermont"));
		states.add(new SelectOption("VA", "Virginia"));
		states.add(new SelectOption("WA", "Washington"));
		states.add(new SelectOption("WV", "West Virginia"));
		states.add(new SelectOption("WI", "Wisconsin"));
		states.add(new SelectOption("WY", "Wyoming"));
		
		return states;
	}
}
