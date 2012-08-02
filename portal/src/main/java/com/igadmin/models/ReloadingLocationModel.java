package com.igadmin.models;

import org.apache.wicket.model.LoadableDetachableModel;

import com.igadmin.data.DAO;
import com.igadmin.data.Location;


public class ReloadingLocationModel extends LoadableDetachableModel<Location>
{
	private static final long	serialVersionUID	= 8427006177292831145L;
	private long id;
	
	public ReloadingLocationModel(Location l)
	{
		this.id = l.getId();
	}
	
	@Override
	protected Location load()
	{
		final DAO dao = new DAO();
		return dao.getOrCreateLocation(this.id);
	}
}
