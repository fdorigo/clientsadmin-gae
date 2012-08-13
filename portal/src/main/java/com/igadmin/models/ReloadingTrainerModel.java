package com.igadmin.models;

import org.apache.wicket.model.LoadableDetachableModel;

import com.igadmin.data.DAO;
import com.igadmin.data.Trainer;


public class ReloadingTrainerModel extends LoadableDetachableModel<Trainer>
{
	private static final long	serialVersionUID	= -7514991429836866249L;
	private long id;
	
	public ReloadingTrainerModel(Trainer t)
	{
		this.id = t.getId();
	}
	
	@Override
	protected Trainer load()
	{
		final DAO dao = new DAO();
		return dao.getOrCreateTrainer(this.id);
	}
}
