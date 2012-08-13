package com.igadmin.panels;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
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
import org.apache.wicket.validation.validator.MaximumValidator;
import org.apache.wicket.validation.validator.MinimumValidator;

import com.google.appengine.api.datastore.PhoneNumber;
import com.googlecode.objectify.Key;
import com.igadmin.data.DAO;
import com.igadmin.data.Location;
import com.igadmin.data.Trainer;
import com.igadmin.data.utils.StorageUtils;
import com.igadmin.form.FormUtils;
import com.igadmin.form.SelectOption;

public class TrainerPanel extends Panel
{
	private static final long	serialVersionUID	= -2334745566004580326L;
	private static final Logger	LOG					= Logger.getLogger(TrainerPanel.class);
	private Trainer				trainerModel;
	private SelectOption		selectedState;
	private List<SelectOption>	listOfStates;
	private List<Location>		listOfLocations;
	private Location			selectedLocation;

	public TrainerPanel(String panelId)
	{
		super(panelId);
		trainerModel = new Trainer();
		initComponents();
	}

	public TrainerPanel(String panelId, PageParameters params)
	{
		super(panelId);

		if (!params.getValues("trainerId").isEmpty())
		{
			try
			{
				String trainerId = params.get("trainerId").toString();
				Integer id = Integer.parseInt(trainerId);
				LOG.debug("Trainer index from params: " + id);

				if (id > 0)
				{
					DAO dao = new DAO();
					trainerModel = dao.getOrCreateTrainer(id);
					selectedLocation = dao.getOrCreateLocation(trainerModel.getLocationKey());
				}
			}
			catch (Exception e)
			{
				LOG.warn("Invalid parameter received: " + e.getMessage());
			}
		}

		if (trainerModel == null)
		{
			trainerModel = new Trainer();
		}

		initComponents();
	}

	private void initComponents()
	{
		add(new FeedbackPanel("feedback"));

		Form<Trainer> form = new Form<Trainer>("form", new CompoundPropertyModel<Trainer>(trainerModel))
		{
			private static final long	serialVersionUID	= 1617572834759513718L;

			@Override
			protected void onSubmit()
			{
				final DAO dao = new DAO();

				Trainer trainer = getModelObject();
				trainer.setLocationKey(new Key<Location>(Location.class, selectedLocation.getId()));
				trainer.setAddressState(getSelectedState().getValue());

				dao.ofy().put(trainer);
				LOG.debug("Trainer from model: " + trainer);
			}
		};

		listOfLocations = StorageUtils.getLocationList();

		LOG.debug("Option list size:" + listOfLocations.size());

		add(form);

		listOfStates = FormUtils.initStateOptionList();

		if (selectedLocation != null)
		{
			for (SelectOption o : listOfStates)
			{
				if (o.getValue().equals(selectedLocation.getAddressState()))
				{
					selectedState = o;
					LOG.debug("Found state: " + o.getValue());
					break;
				}
				else if (o.getKey().equals(selectedLocation.getAddressState()))
				{
					selectedState = o;
					LOG.debug("Found legacy state: " + o.getValue());
					break;
				}
			}
		}

		final IModel<List<SelectOption>> stateChoiceModel = new AbstractReadOnlyModel<List<SelectOption>>()
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public List<SelectOption> getObject()
			{
				return listOfStates;
			}
		};

		AttributeModifier jsModifier = new AttributeModifier("onFocus", "darkenFormField(this);");

		form.add(new RequiredTextField<String>(Trainer.NAME_FIRST_PROPERTY).add(jsModifier));
		form.add(new TextField<String>(Trainer.NAME_MIDDLE_PROPERTY).add(jsModifier));
		form.add(new RequiredTextField<String>(Trainer.NAME_LAST_PROPERTY).add(jsModifier));

		form.add(new RequiredTextField<String>(Trainer.ADDRESS_NUM_PROPERTY).add(jsModifier));
		form.add(new RequiredTextField<String>(Trainer.ADDRESS_STREET_PROPERTY).add(jsModifier));
		form.add(new TextField<String>(Trainer.ADDRESS_APT_PROPERTY).add(jsModifier));
		form.add(new RequiredTextField<String>(Trainer.ADDRESS_CITY_PROPERTY).add(jsModifier));
		ChoiceRenderer<SelectOption> choiceRenderer = new ChoiceRenderer<SelectOption>("value", "key");
		DropDownChoice<SelectOption> fieldState = new DropDownChoice<SelectOption>(Trainer.ADDRESS_STATE_PROPERTY, new PropertyModel<SelectOption>(this, "selectedState"), stateChoiceModel,
				choiceRenderer);
		fieldState.setRequired(true);
		form.add(fieldState.add(jsModifier));
		form.add(new RequiredTextField<String>(Trainer.ADDRESS_ZIP_PROPERTY).add(jsModifier));

		form.add(new RequiredTextField<String>(Trainer.EMAIL_ADDRESS_PROPERTY).add(jsModifier));
		form.add(new RequiredTextField<PhoneNumber>(Trainer.PHONE_PRIMARY_PROPERTY + ".number").add(jsModifier));
		form.add(new TextField<PhoneNumber>(Trainer.PHONE_SECONDARY_PROPERTY + ".number").add(jsModifier));

		ChoiceRenderer<Location> choiceLocationRenderer = new ChoiceRenderer<Location>("locationName", "id");
		DropDownChoice<Location> fieldLocation = new DropDownChoice<Location>(Trainer.LOCATION_PROPERTY, new PropertyModel<Location>(this, "selectedLocation"), listOfLocations, choiceLocationRenderer);
		form.add(fieldLocation.add(new AttributeModifier("onFocus", "clearFormField(this);")));
		fieldLocation.setRequired(true);

		form.add(new RequiredTextField<Double>(Trainer.COMP_RATE_PROPERTY).add(new MinimumValidator<Double>(0.0)).add(new MaximumValidator<Double>(100.0)));
	}

	public SelectOption getSelectedState()
	{
		LOG.debug("Getting selected state");
		return selectedState;
	}

	public void setSelectedState(SelectOption selectedState)
	{
		LOG.debug("Setting selected state");
		this.selectedState = selectedState;
	}

	public Location getSelectedLocation()
	{
		return selectedLocation;
	}

	public void setSelectedLocation(Location selectedLocation)
	{
		this.selectedLocation = selectedLocation;
	}
}
