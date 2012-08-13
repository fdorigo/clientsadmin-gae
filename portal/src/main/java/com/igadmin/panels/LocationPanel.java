package com.igadmin.panels;

import java.util.Date;
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

import com.google.appengine.api.datastore.PhoneNumber;
import com.igadmin.data.DAO;
import com.igadmin.data.Location;
import com.igadmin.data.Phone;
import com.igadmin.form.FormUtils;
import com.igadmin.form.SelectOption;

/**
 * Class used to manipulate stored locations or create new ones. 
 * 
 * @author fdorigo
 */
public class LocationPanel extends Panel
{
	private static final long	serialVersionUID		= 3695907416548135080L;
	private static final Logger	LOG						= Logger.getLogger(LocationPanel.class);

	private SelectOption		selectedState;
	private Integer				selectedLocationIndex	= 0;
	private Location			selectedLocation;

	/**
	 * Builds a component used to create and edit locations. 
	 * 
	 * @param panelId
	 * @param params
	 */
	public LocationPanel(final String panelId, final PageParameters params)
	{
		super(panelId);
		
		if (!params.getValues("locationId").isEmpty())
		{
			try
			{
				String locId = params.get("locationId").toString();
				selectedLocationIndex = Integer.parseInt(locId);
				LOG.debug("Location index from params: " + selectedLocationIndex);
			}
			catch (Exception e)
			{
				LOG.warn("Invalid parameter received: " + e.getMessage());
			}
		}
		
		init();
	}

	private void init()
	{
		add(new FeedbackPanel("feedback"));

		final List<SelectOption> listOfStates = FormUtils.initStateOptionList();

		if (selectedLocationIndex > 0)
		{
			DAO dao = new DAO();
			selectedLocation = dao.getOrCreateLocation(selectedLocationIndex);
			
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
			private static final long	serialVersionUID	= 7914671765520901518L;

			@Override
			public List<SelectOption> getObject()
			{
				return listOfStates;
			}
		};

		if (selectedLocation == null)
		{
			LOG.debug("Selected Location null");
			selectedLocation = new Location();
		}

		CompoundPropertyModel<Location> locationModel = new CompoundPropertyModel<Location>(selectedLocation);

		Form<Location> form = new Form<Location>("form", locationModel)
		{
			private static final long	serialVersionUID	= 2022074730405412606L;

			@Override
			protected void onSubmit()
			{
				LOG.debug("Location Form " + getModelObject().getLocationName());
				DAO dao = new DAO();

				Location location;
				Location model = getModelObject();

				if (model.getId() != null)
				{
					location = dao.getOrCreateLocation(model.getId());
				}
				else
				{
					location = new Location();
				}

				location.setLocationName(model.getLocationName());

				location.setAddressApt(model.getAddressApt());
				location.setAddressStreet(model.getAddressStreet());
				location.setAddressNum(model.getAddressNum());
				location.setAddressCity(model.getAddressCity());
				location.setAddressState(selectedState.getValue());
				location.setAddressZip(model.getAddressZip());

				location.setEmailAddress(model.getEmailAddress());
				location.setMgrNameFirst(model.getMgrNameFirst());
				location.setMgrNameLast(model.getMgrNameLast());

				if (model.getPhonePrimary() != null)
				{
					Phone primary = new Phone(model.getPhonePrimary().getNumber());
					location.setPhonePrimary(primary);
				}
				
				if (model.getPhoneSecondary() != null)
				{
					Phone secondary = new Phone(model.getPhoneSecondary().getNumber());
					location.setPhoneSecondary(secondary);
				}

				// TODO add form field to set opening date.
				location.setOpeningDate(new Date());

				dao.ofy().put(location);
				assert location.getId() != null;
				LOG.debug("Model State: " + model.getAddressState());
				LOG.debug("Location State: " + location.getAddressState());

				LOG.debug("Done saving object: " + location.getLocationName());
			}
		};
		add(form);

//		AttributeModifier jsModifier = new AttributeModifier("onFocus", "clearFormField(this);");
		AttributeModifier jsModifier = new AttributeModifier("onFocus", "darkenFormField(this);");

		form.add(new RequiredTextField<String>(Location.NAME_PROPERTY).add(jsModifier));
		form.add(new RequiredTextField<String>(Location.MGR_NAME_FIRST_PROPERTY).add(jsModifier));
		form.add(new RequiredTextField<String>(Location.MGR_NAME_LAST_PROPERTY).add(jsModifier));

		form.add(new RequiredTextField<String>(Location.ADDRESS_NUM_PROPERTY).add(jsModifier));
		form.add(new RequiredTextField<String>(Location.ADDRESS_STREET_PROPERTY).add(jsModifier));
		form.add(new TextField<String>(Location.ADDRESS_APT_PROPERTY).add(jsModifier));
		form.add(new RequiredTextField<String>(Location.ADDRESS_CITY_PROPERTY).add(jsModifier));
		ChoiceRenderer<SelectOption> choiceRenderer = new ChoiceRenderer<SelectOption>("value", "key");
		DropDownChoice<SelectOption> fieldState = new DropDownChoice<SelectOption>(Location.ADDRESS_STATE_PROPERTY, new PropertyModel<SelectOption>(this, "selectedState"), stateChoiceModel,
				choiceRenderer);
		fieldState.setRequired(true);
		form.add(fieldState.add(jsModifier));
		form.add(new RequiredTextField<String>(Location.ADDRESS_ZIP_PROPERTY).add(jsModifier));
		
		form.add(new RequiredTextField<String>(Location.EMAIL_ADDRESS).add(jsModifier));
		form.add(new RequiredTextField<PhoneNumber>("phonePrimary.number").add(jsModifier));
		form.add(new TextField<PhoneNumber>("phoneSecondary.number").add(jsModifier));

		// TODO add date picker
		// form.add(new
		// RequiredTextField<Date>(Location.OPENING_DATE_PROPERTY).add(jsModifier));
	}

	public SelectOption getSelectedState()
	{
		return selectedState;
	}

	public void setSelectedState(SelectOption selectedState)
	{
		this.selectedState = selectedState;
	}
}
