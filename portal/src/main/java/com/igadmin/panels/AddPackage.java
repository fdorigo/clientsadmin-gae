package com.igadmin.panels;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import com.igadmin.data.Client;
import com.igadmin.data.GymPackage;
import com.igadmin.data.Trainer;
import com.igadmin.form.SelectOption;

public class AddPackage extends Panel
{
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(AddClient.class);

	private static final long serialVersionUID = -2525099796767054006L;

	private GymPackage packageModel;
	
	private SelectOption selectedClient;
	private SelectOption selectedTrainer;
	
	@SuppressWarnings("unused")
	private List<Client> listOfClients = new ArrayList<Client>();
	private List<Trainer> listOfTrainers = new ArrayList<Trainer>();

	public AddPackage(String id)
	{
		super(id);
		packageModel = new GymPackage();
		initComponents();
	}

	private void initComponents()
	{
		add(new FeedbackPanel("feedback"));

		Form<GymPackage> form = new Form<GymPackage>("form", new CompoundPropertyModel<GymPackage>(packageModel)) 
		{
			private static final long serialVersionUID = 1617572834759513718L;

			@Override
			protected void onSubmit()
			{
				//TODO add package to DB
			}
		};

		add(form);

		
	}

	public SelectOption getSelectedClient()
	{
		return selectedClient;
	}

	public void setSelectedClient(SelectOption selectedClient)
	{
		this.selectedClient = selectedClient;
	}

	public SelectOption getSelectedTrainer()
	{
		return selectedTrainer;
	}

	public void setSelectedTrainert(SelectOption selectedTrainer)
	{
		this.selectedTrainer = selectedTrainer;
	}

}
