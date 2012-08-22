package com.igadmin.data.utils;

import java.util.Comparator;

import com.igadmin.data.Client;

public class ComparatorFactory
{
	static final Comparator<Client>	LAST_NAME_ASC	= new Comparator<Client>()
													{
														public int compare(Client e1, Client e2)
														{
															int lastCmp = e1.getNameLast().compareTo(e2.getNameLast());
															return (lastCmp != 0 ? lastCmp : e1.getNameFirst().compareTo(e2.getNameFirst()));
														}
													};

	static final Comparator<Client>	LAST_NAME_DES	= new Comparator<Client>()
													{
														public int compare(Client e1, Client e2)
														{
															int lastCmp = e2.getNameLast().compareTo(e1.getNameLast());
															return (lastCmp != 0 ? lastCmp : e2.getNameFirst().compareTo(e1.getNameFirst()));
														}
													};
													
	static final Comparator<Client>	FIRST_NAME_ASC	= new Comparator<Client>()
													{
														public int compare(Client e1, Client e2)
														{
															int lastCmp = e1.getNameFirst().compareTo(e2.getNameFirst());
															return (lastCmp != 0 ? lastCmp : e1.getNameLast().compareTo(e2.getNameLast()));
														}
													};

	static final Comparator<Client>	FIRST_NAME_DES	= new Comparator<Client>()
													{
														public int compare(Client e1, Client e2)
														{
															int lastCmp = e2.getNameFirst().compareTo(e1.getNameFirst());
															return (lastCmp != 0 ? lastCmp : e2.getNameLast().compareTo(e1.getNameLast()));
														}
													};
}
