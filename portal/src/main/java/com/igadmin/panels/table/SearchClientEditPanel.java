package com.igadmin.panels.table;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.igadmin.HomePage;
import com.igadmin.TabPanelIndex;
import com.igadmin.data.Client;

public class SearchClientEditPanel extends Panel
{
	private static final long	serialVersionUID	= 7330309210330735488L;

	public SearchClientEditPanel(String id, IModel<?> model)
	{
		super(id, model);
		add(new Link<Void>("l")
		{
			private static final long	serialVersionUID	= -3579744454973966817L;

			@Override
			public void onClick()
			{
				Client client = (Client) SearchClientEditPanel.this.getDefaultModelObject();
				PageParameters params = new PageParameters();
				params.add("newTabId", TabPanelIndex.CLIENT.getIndex());
				params.add("clientId", client.getId());
				setResponsePage(HomePage.class, params);
			}
		});
	}

}
