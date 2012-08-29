package com.igadmin.panels;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import com.igadmin.auth.AppSession;
import com.igadmin.data.Client;
import com.igadmin.data.Location;
import com.igadmin.data.Trainer;
import com.igadmin.data.TrainingPackage;
import com.igadmin.data.utils.StorageUtils;
import com.igadmin.form.SelectOption;

public class PackagePanel extends Panel
{
	@SuppressWarnings("unused")
	private static final Logger	LOG					= Logger.getLogger(ClientPanel.class);

	private static final long	serialVersionUID	= -2525099796767054006L;

	private TrainingPackage		packageModel;

	private Client				selectedClient;
	private Trainer				selectedTrainer;

	@SuppressWarnings("unused")
	private List<Client>		listOfClients		= new ArrayList<Client>();
	private List<Trainer>		listOfTrainers		= new ArrayList<Trainer>();

	private final Location		location;

	public PackagePanel(String id)
	{
		super(id);
		location = ((AppSession) Session.get()).getSessionLocation();
		packageModel = new TrainingPackage();
		initComponents();
	}

	private void initComponents()
	{
		add(new FeedbackPanel("feedback"));

		Form<TrainingPackage> form = new Form<TrainingPackage>("form", new CompoundPropertyModel<TrainingPackage>(packageModel))
		{
			private static final long	serialVersionUID	= 1617572834759513718L;

			@Override
			protected void onSubmit()
			{
				// TODO add package to DB
			}
		};

		add(form);

		listOfClients = StorageUtils.get().getClientListForLocation(location == null ? null : location.getId(), new SortParam(Client.NAME_LAST_PROPERTY, true));
		listOfTrainers = StorageUtils.get().getTrainerListForLocation(location == null ? null : location.getId(), new SortParam(Trainer.NAME_LAST_PROPERTY, true));
		
		ChoiceRenderer<Trainer> choiceTrainerRenderer = new ChoiceRenderer<Trainer>("trainerDisplayName", "id");
		DropDownChoice<Trainer> fieldTrainer = new DropDownChoice<Trainer>(TrainingPackage.TRAINER_PROPERTY, new PropertyModel<Trainer>(this, "selectedTrainer"), listOfTrainers, choiceTrainerRenderer);
		fieldTrainer.setRequired(true);
		form.add(fieldTrainer);

		ChoiceRenderer<Client> choiceClientRenderer = new ChoiceRenderer<Client>("clientDisplayName", "id");
		DropDownChoice<Client> fieldClient = new DropDownChoice<Client>(TrainingPackage.CLIENT_PROPERTY, new PropertyModel<Client>(this, "selectedClient"), listOfClients, choiceClientRenderer);
		fieldClient.setRequired(true);
		form.add(fieldClient);
		
		//TODO:
		// date picker
		// rate picker
		// payment type
		// ...
	}

	
	
	public Client getSelectedClient()
	{
		return selectedClient;
	}

	public void setSelectedClient(Client selectedClient)
	{
		this.selectedClient = selectedClient;
	}

	public Trainer getSelectedTrainer()
	{
		return selectedTrainer;
	}

	public void setSelectedTrainert(Trainer selectedTrainer)
	{
		this.selectedTrainer = selectedTrainer;
	}

}
