package com.igadmin.data.utils;

import java.util.Comparator;

import com.igadmin.data.Client;
import com.igadmin.data.GymComparable;

public class ComparatorFactory
{
	static final Comparator<GymComparable>	LAST_NAME_ASC	= new Comparator<GymComparable>()
															{
																public int compare(GymComparable e1, GymComparable e2)
																{
																	int lastCmp = e1.getNameLast().compareTo(e2.getNameLast());
																	return (lastCmp != 0 ? lastCmp : e1.getNameFirst().compareTo(e2.getNameFirst()));
																}
															};

	static final Comparator<GymComparable>	LAST_NAME_DES	= new Comparator<GymComparable>()
															{
																public int compare(GymComparable e1, GymComparable e2)
																{
																	int lastCmp = e2.getNameLast().compareTo(e1.getNameLast());
																	return (lastCmp != 0 ? lastCmp : e2.getNameFirst().compareTo(e1.getNameFirst()));
																}
															};

	static final Comparator<GymComparable>	FIRST_NAME_ASC	= new Comparator<GymComparable>()
															{
																public int compare(GymComparable e1, GymComparable e2)
																{
																	int lastCmp = e1.getNameFirst().compareTo(e2.getNameFirst());
																	return (lastCmp != 0 ? lastCmp : e1.getNameLast().compareTo(e2.getNameLast()));
																}
															};

	static final Comparator<GymComparable>	FIRST_NAME_DES	= new Comparator<GymComparable>()
															{
																public int compare(GymComparable e1, GymComparable e2)
																{
																	int lastCmp = e2.getNameFirst().compareTo(e1.getNameFirst());
																	return (lastCmp != 0 ? lastCmp : e2.getNameLast().compareTo(e1.getNameLast()));
																}
															};
}
