package com.igadmin.panels;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.igadmin.data.DAO;
import com.igadmin.data.Location;
import com.igadmin.data.Phone;
import com.igadmin.form.FormUtils;
import com.igadmin.form.SelectOption;

//@AuthorizeInstantiation("ADMIN")
public class AddLocation extends Panel
{
	private static final long	serialVersionUID	= 3695907416548135080L;
	private static final Logger	LOG					= Logger.getLogger(AddLocation.class);

	private SelectOption		selectedState;

	public AddLocation(String panelId)
	{
		super(panelId);
		init();
	}

	private void init()
	{
		final Model<String> modelName = new Model<String>("Location Name");

		final Model<String> modelNum = new Model<String>("Street #");
		final Model<String> modelStreet = new Model<String>("Street Name");
		final Model<String> modelApt = new Model<String>("Unit #");
		final Model<String> modelCity = new Model<String>("City");
		final Model<String> modelZip = new Model<String>("ZIP");

		final Model<String> modelMgrNameFirst = new Model<String>("First Name");
		final Model<String> modelMgrNameLast = new Model<String>("Last Name");

		final Model<String> modelPriPhNumber = new Model<String>("123 456 7890");
		final Model<String> modelPriPhExtension = new Model<String>("ext");

		final Model<String> modelSecPhNumber = new Model<String>("123 456 7890");
		final Model<String> modelSecPhExtension = new Model<String>("ext");

		final Model<String> modelEmailAddress = new Model<String>("manager@example.com");

		final List<SelectOption> listOfStates = FormUtils.initStateOptionList();

		final IModel<List<SelectOption>> stateChoiceModel = new AbstractReadOnlyModel<List<SelectOption>>()
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public List<SelectOption> getObject()
			{
				return listOfStates;
			}
		};

		Form<Void> form = new Form<Void>("form")
		{
			private static final long	serialVersionUID	= 709544921769281327L;

			@Override
			protected void onSubmit()
			{
				LOG.debug("AddLocation: " + modelName.getObject());

				DAO dao = new DAO();

				LOG.debug("Saving objcet");

				Location location = new Location();
				
				location.setLocationName(modelName.getObject());
				
				location.setAddressApt(modelApt.getObject());
				location.setAddressStreet(modelStreet.getObject());
				location.setAddressNum(modelNum.getObject());
				location.setAddressCity(modelCity.getObject());
				location.setAddressState(selectedState.getKey());
				location.setAddressZip(modelZip.getObject());
				
				location.setEmailAddress(modelEmailAddress.getObject());
				location.setMgrNameFirst(modelMgrNameFirst.getObject());
				location.setMgrNameLast(modelMgrNameLast.getObject());
				
				Phone primary = new Phone(modelPriPhNumber.getObject(),modelPriPhExtension.getObject());
				location.setPhonePrimary(primary);
				
				Phone secondary = new Phone(modelSecPhNumber.getObject(),modelSecPhExtension.getObject());
				location.setPhoneSecondary(secondary);
				
				//TODO add form field to set opening date.
				location.setOpeningDate(new Date());
				
				dao.ofy().put(location);
				assert location.getId() != null;

				LOG.debug("Done saving object: " + location.getId());
			}
		};
		add(form);

		// Location Name
		TextField<String> field = new TextField<String>("locName", modelName);
		form.add(field.add(new AttributeModifier("onFocus", "clearFormField(this);")));

		// Contact Information
		TextField<String> fieldMgrFirst = new TextField<String>("mgrNameFirst", modelMgrNameFirst);
		form.add(fieldMgrFirst.add(new AttributeModifier("onFocus", "clearFormField(this);")));

		TextField<String> fieldMgrLast = new TextField<String>("mgrNameLast", modelMgrNameLast);
		form.add(fieldMgrLast.add(new AttributeModifier("onFocus", "clearFormField(this);")));

		TextField<String> priPhNumber = new TextField<String>("priPhNumber", modelPriPhNumber);
		form.add(priPhNumber.add(new AttributeModifier("onFocus", "clearFormField(this);")));

		TextField<String> priPhExtension = new TextField<String>("priPhExtension", modelPriPhExtension);
		form.add(priPhExtension.add(new AttributeModifier("onFocus", "clearFormField(this);")));

		TextField<String> secPhNumber = new TextField<String>("secPhNumber", modelSecPhNumber);
		form.add(secPhNumber.add(new AttributeModifier("onFocus", "clearFormField(this);")));

		TextField<String> secPhExtension = new TextField<String>("secPhExtension", modelSecPhExtension);
		form.add(secPhExtension.add(new AttributeModifier("onFocus", "clearFormField(this);")));

		// Address
		TextField<String> fieldNum = new TextField<String>("locAddrNum", modelNum);
		form.add(fieldNum.add(new AttributeModifier("onFocus", "clearFormField(this);")));

		TextField<String> fieldStreet = new TextField<String>("locAddrStreet", modelStreet);
		form.add(fieldStreet.add(new AttributeModifier("onFocus", "clearFormField(this);")));

		TextField<String> fieldApt = new TextField<String>("locAddrApt", modelApt);
		form.add(fieldApt.add(new AttributeModifier("onFocus", "clearMe(this);")));

		TextField<String> fieldCity = new TextField<String>("locAddrCity", modelCity);
		form.add(fieldCity.add(new AttributeModifier("onFocus", "clearFormField(this);")));

		EmailTextField fieldEmail = new EmailTextField("emailAddr", modelEmailAddress);
		form.add(fieldEmail.add(new AttributeModifier("onFocus", "clearFormField(this);")));

		ChoiceRenderer<SelectOption> choiceRenderer = new ChoiceRenderer<SelectOption>("value", "key");
		DropDownChoice<SelectOption> fieldState = new DropDownChoice<SelectOption>("locAddrState", new PropertyModel<SelectOption>(this, "selectedState"), stateChoiceModel, choiceRenderer);
		form.add(fieldState.add(new AttributeModifier("onFocus", "clearFormField(this);")));

		TextField<String> fieldZip = new TextField<String>("locAddrZip", modelZip);
		form.add(fieldZip.add(new AttributeModifier("onFocus", "clearFormField(this);")));

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
