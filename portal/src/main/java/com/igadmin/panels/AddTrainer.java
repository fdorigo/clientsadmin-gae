package com.igadmin.panels;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.MaximumValidator;
import org.apache.wicket.validation.validator.MinimumValidator;

import com.igadmin.data.DAO;
import com.igadmin.data.Location;
import com.igadmin.data.Trainer;
import com.igadmin.form.FormUtils;
import com.igadmin.form.SelectOption;

public class AddTrainer extends Panel
{
	private static final long serialVersionUID = -2334745566004580326L;
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(AddTrainer.class);
	private final Trainer trainerModel;
	private SelectOption selectedState;
	private SelectOption selectedLocation;
	private List<SelectOption> listOfStates;// = new ArrayList<SelectOption>();
	private List<SelectOption> listOfLocations;// = new ArrayList<SelectOption>();

	public AddTrainer(String panelId)
	{
		super(panelId);
		trainerModel = new Trainer();
		initComponents();
	}

	private void initComponents()
	{
		add(new FeedbackPanel("feedback"));
		
		Form<Trainer> form = new Form<Trainer>("form", new CompoundPropertyModel<Trainer>(trainerModel)) {
			private static final long serialVersionUID = 1617572834759513718L;

			@Override
			protected void onSubmit()
			{
				final DAO dao = new DAO();
				Long locKey = Long.parseLong(getSelectedLocation().getKey());
				LOG.debug("Selected location key: " + locKey);
				
				Trainer trainer = getModelObject();
				trainer.setLocation(dao.getOrCreateLocation(locKey));
				trainer.setAddressState(getSelectedState().getValue());
				
				dao.ofy().put(trainer);
				LOG.debug("Trainer from model: " + trainer);  
			}
		};
		
		listOfLocations = FormUtils.initLocationOptionList();
		
		LOG.debug("Option list size:" + listOfLocations.size());
		
		add(form);
		
		initNameFields(form);
		initPhoneFields(form);
		initAddressFields(form);

		TextField<Double> payRate = new TextField<Double>(Trainer.COMP_RATE_PROPERTY);
		form.add(payRate);
		payRate.setRequired(true);
		payRate.add(new AttributeModifier("onFocus", "clearFormField(this);"));
		payRate.add(new MinimumValidator<Double>(0.0));
		payRate.add(new MaximumValidator<Double>(100.0));
		
	}
	
	private void initNameFields(final Form<Trainer> form)
	{
		final TextField<String> nameFirst = new TextField<String>(Trainer.NAME_FIRST_PROPERTY);
		form.add(nameFirst.add(new AttributeModifier("onFocus", "clearFormField(this);")));

		final TextField<String> nameMiddle = new TextField<String>(Trainer.NAME_MIDDLE_PROPERTY);
		form.add(nameMiddle.add(new AttributeModifier("onFocus", "clearFormField(this);")));
		
		final TextField<String> nameLast = new TextField<String>(Trainer.NAME_LAST_PROPERTY);
		form.add(nameLast.add(new AttributeModifier("onFocus", "clearFormField(this);")));
	}
	
	private void initPhoneFields(final Form<Trainer> form)
	{
		final TextField<String> priPhNubmer = new TextField<String>(Trainer.PHONE_PRIMARY_PROPERTY);
		form.add(priPhNubmer);
		priPhNubmer.add(new AttributeModifier("onFocus", "clearFormField(this);"));
		priPhNubmer.add(new AttributeModifier("onChange", "formatPhone(this);"));

		final TextField<String> secPhNumber = new TextField<String>(Trainer.PHONE_SECONDARY_PROPERTY);
		form.add(secPhNumber);
		secPhNumber.add(new AttributeModifier("onFocus", "clearFormField(this);"));
		secPhNumber.add(new AttributeModifier("onChange", "formatPhone(this);"));
	}

	private void initAddressFields(final Form<Trainer> form)
	{
		listOfStates = FormUtils.initStateOptionList();

		final IModel<List<SelectOption>> stateChoiceModel = new AbstractReadOnlyModel<List<SelectOption>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<SelectOption> getObject()
			{
				return listOfStates;
			}
		};

		final IModel<List<SelectOption>> locationChoiceModel = new AbstractReadOnlyModel<List<SelectOption>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<SelectOption> getObject()
			{
				return listOfLocations;
			}
		};

		ChoiceRenderer<SelectOption> choiceLocationRenderer = new ChoiceRenderer<SelectOption>("value", "key");
		DropDownChoice<SelectOption> fieldLocation = new DropDownChoice<SelectOption>(Trainer.LOCATION_PROPERTY,  new PropertyModel<SelectOption>(this, "selectedLocation"), locationChoiceModel, choiceLocationRenderer);
		form.add(fieldLocation.add(new AttributeModifier("onFocus", "clearFormField(this);")));
		fieldLocation.setRequired(true);

		TextField<String> addrNum = new TextField<String>(Trainer.ADDRESS_NUM_PROPERTY);
		form.add(addrNum.add(new AttributeModifier("onFocus", "clearFormField(this);")));
		TextField<String> addrStreet = new TextField<String>(Trainer.ADDRESS_STREET_PROPERTY);
		form.add(addrStreet.add(new AttributeModifier("onFocus", "clearFormField(this);")));
		TextField<String> addrApt = new TextField<String>(Trainer.ADDRESS_APT_PROPERTY);
		form.add(addrApt.add(new AttributeModifier("onFocus", "clearFormField(this);")));
		TextField<String> addrCity = new TextField<String>(Trainer.ADDRESS_CITY_PROPERTY);
		form.add(addrCity.add(new AttributeModifier("onFocus", "clearFormField(this);")));
		ChoiceRenderer<SelectOption> choiceRenderer = new ChoiceRenderer<SelectOption>("value", "key");
		DropDownChoice<SelectOption> fieldState = new DropDownChoice<SelectOption>(Trainer.ADDRESS_STATE_PROPERTY,  new PropertyModel<SelectOption>(this, "selectedState"), stateChoiceModel, choiceRenderer);
		form.add(fieldState.add(new AttributeModifier("onFocus", "clearFormField(this);")));
		fieldState.setRequired(true);
		TextField<String> addrZip = new TextField<String>(Trainer.ADDRESS_ZIP_PROPERTY);
		form.add(addrZip.add(new AttributeModifier("onFocus", "clearFormField(this);")));

		EmailTextField email = new EmailTextField(Trainer.EMAIL_ADDRESS_PROPERTY);
		form.add(email.add(new AttributeModifier("onFocus", "clearFormField(this);")));
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

	public SelectOption getSelectedLocation()
	{
		LOG.debug("Getting selected location");
		return selectedLocation;
	}

	public void setSelectedLocation(SelectOption selectedLocation)
	{
		LOG.debug("Setting selected location");
		this.selectedLocation = selectedLocation;
	}
}
