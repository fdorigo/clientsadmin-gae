package com.igadmin.panels;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.IHeaderResponse;
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
import org.apache.wicket.request.resource.PackageResourceReference;

import com.igadmin.data.Client;
import com.igadmin.form.FormUtils;
import com.igadmin.form.SelectOption;

public class AddClient extends Panel
{
	private static final long serialVersionUID = 8452012594546761540L;
	private static final Logger LOG = Logger.getLogger(AddClient.class);
	private final Client clientModel;
	private SelectOption selectedState;
	private List<SelectOption> listOfStates;



	public AddClient(String panelId)
	{
		super(panelId);
		
		//TODO register object with DB
		clientModel = new Client();

//		clientModel.setNameFirst("First");
//		clientModel.setNameMiddle("Middle");
//		clientModel.setNameLast("Last");
//		clientModel.setAddressStreet("eg. 675 Sunset Blvd, #201");
//		clientModel.setAddressCity("City");
//		clientModel.setAddressZip("ZIP");

		initComponents();
	}
	
	private void initComponents()
	{
		add(new FeedbackPanel("feedback"));
		
		Form<Client> form = new Form<Client>("form", new CompoundPropertyModel<Client>(clientModel)) {
			private static final long serialVersionUID = 1617572834759513718L;

			@Override
			protected void onSubmit()
			{
//				clientModel.setAddressState(selectedState.getKey());

				LOG.debug(clientModel.toString());
				
				//TODO add client to DB
			}
		};

		add(form);

		initNameFields(form);
		initAddressFields(form);
		initPhoneFields(form);
	}

	private void initNameFields(final Form<Client> form)
	{
		final TextField<String> nameFirst = new TextField<String>(Client.NAME_FIRST_PROPERTY);
		form.add(nameFirst.add(new AttributeModifier("onFocus", "clearFormField(this);")));

		final TextField<String> nameMiddle = new TextField<String>(Client.NAME_MIDDLE_PROPERTY);
		form.add(nameMiddle.add(new AttributeModifier("onFocus", "clearFormField(this);")));
		
		final TextField<String> nameLast = new TextField<String>(Client.NAME_LAST_PROPERTY);
		form.add(nameLast.add(new AttributeModifier("onFocus", "clearFormField(this);")));
	}

	private void initPhoneFields(final Form<Client> form)
	{
		final TextField<String> priPhNubmer = new TextField<String>(Client.PHONE_PRIMARY_PROPERTY);
		form.add(priPhNubmer);
		priPhNubmer.add(new AttributeModifier("onFocus", "clearFormField(this);"));
		priPhNubmer.add(new AttributeModifier("onChange", "formatPhone(this);"));

		final TextField<String> secPhNumber = new TextField<String>(Client.PHONE_SECONDARY_PROPERTY);
		form.add(secPhNumber);
		secPhNumber.add(new AttributeModifier("onFocus", "clearFormField(this);"));
		secPhNumber.add(new AttributeModifier("onChange", "formatPhone(this);"));
	}

	private void initAddressFields(final Form<Client> form)
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

		TextField<String> addrStreet = new TextField<String>(Client.ADDRESS_STREET_PROPERTY);
		form.add(addrStreet.add(new AttributeModifier("onFocus", "clearFormField(this);")));
		TextField<String> addrCity = new TextField<String>(Client.ADDRESS_CITY_PROPERTY);
		form.add(addrCity.add(new AttributeModifier("onFocus", "clearFormField(this);")));

		ChoiceRenderer<SelectOption> choiceRenderer = new ChoiceRenderer<SelectOption>("value", "key");
		DropDownChoice<SelectOption> fieldState = new DropDownChoice<SelectOption>(Client.ADDRESS_STATE_PROPERTY,  new PropertyModel<SelectOption>(this, "selectedState"), stateChoiceModel, choiceRenderer);
		form.add(fieldState.add(new AttributeModifier("onFocus", "clearFormField(this);")));
		fieldState.setRequired(true);

		TextField<String> addrZip = new TextField<String>(Client.ADDRESS_ZIP_PROPERTY);
		form.add(addrZip.add(new AttributeModifier("onFocus", "clearFormField(this);")));

		EmailTextField email = new EmailTextField(Client.EMAIL_ADDRESS_PROPERTY);
		form.add(email.add(new AttributeModifier("onFocus", "clearFormField(this);")));
		
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
