package com.igadmin.panels;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.google.appengine.api.datastore.PhoneNumber;
import com.igadmin.auth.AppSession;
import com.igadmin.data.Client;
import com.igadmin.data.DAO;
import com.igadmin.data.Location;
import com.igadmin.data.Trainer;
import com.igadmin.data.utils.StorageUtils;
import com.igadmin.form.FormUtils;
import com.igadmin.form.SelectOption;

public class ClientPanel extends Panel
{
	private static final long	serialVersionUID	= 8452012594546761540L;
	private static final Logger	LOG					= Logger.getLogger(ClientPanel.class);
	private Client				clientModel;
	private SelectOption		selectedState;
	private List<SelectOption>	listOfStates;
	private Trainer				selectedTrainer;
	private List<Trainer>		listOfTrainers;
	private final Location		location;

	public ClientPanel(final String panelId)
	{
		super(panelId);

		location = ((AppSession) Session.get()).getSessionLocation();

		initComponents();
	}

	public ClientPanel(final String panelId, final PageParameters params)
	{
		super(panelId);

		location = ((AppSession) Session.get()).getSessionLocation();

		if (!params.get("clientId").isNull() && !params.get("clientId").isEmpty())
		{
			try
			{
				final DAO dao = new DAO();
				final Long clientId = Long.parseLong(params.get("clientId").toString());
				clientModel = dao.getOrCreateClient(clientId);
				selectedTrainer = dao.getOrCreateTrainer(clientModel.getTrainerKey());
			}
			catch (NumberFormatException e)
			{
				LOG.warn("Invalid client id from page params");
			}
		}

		initComponents();
	}

	private void initComponents()
	{
		add(new FeedbackPanel("feedback"));

		listOfStates = FormUtils.initStateOptionList();

		if (clientModel != null && !clientModel.getAddressState().isEmpty())
		{
			for (SelectOption o : listOfStates)
			{
				if (o.getValue().equalsIgnoreCase(clientModel.getAddressState()))
				{
					selectedState = o;
					LOG.debug("Found state: " + o.getValue());
					break;
				}
				else if (o.getKey().equalsIgnoreCase(clientModel.getAddressState()))
				{
					selectedState = o;
					LOG.debug("Found legacy state: " + o.getValue());
					break;
				}
			}

			LOG.debug("Client Slected State: " + clientModel.getAddressState());
		}

		final CompoundPropertyModel<Client> clientPropertyModel = new CompoundPropertyModel<Client>(clientModel);

		Form<Client> form = new Form<Client>("form", clientPropertyModel)
		{
			private static final long	serialVersionUID	= 1617572834759513718L;

			@Override
			protected void onSubmit()
			{
				final DAO dao = new DAO();

				final Client client = getModelObject();

				client.setAddressState(selectedState.getKey());

				dao.ofy().put(client);
				LOG.debug("Saving Client:  " + client);
			}
		};

		add(form);

		final IModel<List<SelectOption>> stateChoiceModel = new AbstractReadOnlyModel<List<SelectOption>>()
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public List<SelectOption> getObject()
			{
				return listOfStates;
			}
		};

		final AttributeModifier jsModifier = new AttributeModifier("onFocus", "darkenFormField(this);");

		form.add(new RequiredTextField<String>(Client.NAME_FIRST_PROPERTY).add(jsModifier));
		form.add(new TextField<String>(Client.NAME_MIDDLE_PROPERTY).add(jsModifier));
		form.add(new RequiredTextField<String>(Client.NAME_LAST_PROPERTY).add(jsModifier));

		form.add(new RequiredTextField<String>(Client.ADDRESS_NUM_PROPERTY).add(jsModifier));
		form.add(new RequiredTextField<String>(Client.ADDRESS_STREET_PROPERTY).add(jsModifier));
		form.add(new TextField<String>(Client.ADDRESS_APT_PROPERTY).add(jsModifier));
		form.add(new RequiredTextField<String>(Client.ADDRESS_CITY_PROPERTY).add(jsModifier));
		ChoiceRenderer<SelectOption> choiceRenderer = new ChoiceRenderer<SelectOption>("value", "key");
		DropDownChoice<SelectOption> fieldState = new DropDownChoice<SelectOption>(Client.ADDRESS_STATE_PROPERTY, new PropertyModel<SelectOption>(this, "selectedState"), stateChoiceModel,
				choiceRenderer);
		fieldState.setRequired(true);
		form.add(fieldState);
		form.add(new RequiredTextField<String>(Client.ADDRESS_ZIP_PROPERTY).add(jsModifier));

		form.add(new RequiredTextField<String>(Client.EMAIL_ADDRESS_PROPERTY).add(jsModifier));
		form.add(new RequiredTextField<PhoneNumber>(Client.PHONE_PRIMARY_PROPERTY + ".number").add(jsModifier));
		form.add(new TextField<PhoneNumber>(Client.PHONE_SECONDARY_PROPERTY + ".number").add(jsModifier));

		if (location != null)
		{
			listOfTrainers = StorageUtils.get().getTrainerListForLocation(location.getId());
		}
		else
		{
			listOfTrainers = StorageUtils.get().getTrainerList();
		}

		ChoiceRenderer<Trainer> choiceTrainerRenderer = new ChoiceRenderer<Trainer>("trainerDisplayName", "id");
		DropDownChoice<Trainer> fieldTrainer = new DropDownChoice<Trainer>(Client.TRAINER_PROPERTY, new PropertyModel<Trainer>(this, "selectedTrainer"), listOfTrainers, choiceTrainerRenderer);
		fieldTrainer.setRequired(true);
		form.add(fieldTrainer);

		DateTextField dateBirthField = new DateTextField(Client.DATE_BIRTH_PROPERTY);
		form.add(dateBirthField.add(jsModifier));

		DateTextField dateJoinedField = new DateTextField(Client.DATE_JOINED_PROPERTY);
		form.add(dateJoinedField.add(jsModifier));

		DatePicker dateBirthPicker = new DatePicker()
		{
			private static final long	serialVersionUID	= 6583026261479889537L;

			@Override
			protected String getAdditionalJavaScript()
			{
				return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render();";
			}
		};
		DatePicker dateJoinedPicker = new DatePicker()
		{
			private static final long	serialVersionUID	= 6583026261479889537L;

			@Override
			protected String getAdditionalJavaScript()
			{
				return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render();";
			}
		};

		dateBirthPicker.setShowOnFieldClick(true);
		dateBirthPicker.setAutoHide(true);
		dateJoinedPicker.setShowOnFieldClick(true);
		dateJoinedPicker.setAutoHide(true);
		dateBirthField.add(dateBirthPicker);
		dateJoinedField.add(dateJoinedPicker);
	}

	public SelectOption getSelectedState()
	{
		return selectedState;
	}

	public void setSelectedState(SelectOption selectedState)
	{
		this.selectedState = selectedState;
	}

	public Trainer getSelectedTrainer()
	{
		return selectedTrainer;
	}

	public void setSelectedTrainer(Trainer selectedTrainer)
	{
		this.selectedTrainer = selectedTrainer;
	}
}
