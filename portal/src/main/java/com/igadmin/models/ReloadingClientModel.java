package com.igadmin.models;

import org.apache.wicket.model.LoadableDetachableModel;

import com.igadmin.data.Client;
import com.igadmin.data.DAO;


public class ReloadingClientModel extends LoadableDetachableModel<Client>
{
	private static final long	serialVersionUID	= -7514991429836866249L;
	private long id;
	
	public ReloadingClientModel(Client c)
	{
		this.id = c.getId();
	}
	
	@Override
	protected Client load()
	{
		final DAO dao = new DAO();
		return dao.getOrCreateClient(this.id);
	}
}
