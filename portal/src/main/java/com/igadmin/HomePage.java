package com.igadmin;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.igadmin.auth.LoginPage;
import com.igadmin.auth.LogoutPage;
import com.igadmin.panels.AddClient;
import com.igadmin.panels.AddTrainer;
import com.igadmin.panels.LocationPanel;
import com.igadmin.panels.SearchLocation;

//@AuthorizeInstantiation("ADMIN")
public class HomePage extends BasePage
{
	private static final long		serialVersionUID	= 1L;
	private AjaxTabbedPanel<ITab>	tabbedPanel;
	private Integer					selectedTabIndex	= 0;
	private static final Logger		LOG					= LoggerFactory.getLogger(HomePage.class);

	public HomePage()
	{
		initComponents(getPageParameters());
	}

	public HomePage(final PageParameters parameters)
	{
		initComponents(parameters);
	}

	private void initComponents(final PageParameters params)
	{
		if (params.get("newTabId").isEmpty())
		{
			try
			{
				Integer index = Integer.parseInt(getPageParameters().get("newTabId").toString());
				selectedTabIndex = index;
			}
			catch (NumberFormatException e)
			{
				LOG.warn("Error parsing page parameters: " + e.getMessage());
			}
		}

		tabbedPanel = new AjaxTabbedPanel<ITab>("tabbedPanel", getTabList(params));
		add(tabbedPanel);
		
		tabbedPanel.setSelectedTab(selectedTabIndex);

		add(new Link<Void>("login")
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public void onClick()
			{
				setResponsePage(LoginPage.class);
			}
		});

		add(new Link<Void>("logout")
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public void onClick()
			{
				setResponsePage(LogoutPage.class);
			}
		});
	}

	private List<ITab> getTabList(final PageParameters p)
	{
		List<ITab> tabs = new ArrayList<ITab>();

		tabs.add(new AbstractTab(new Model<String>("Add Location"))
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public WebMarkupContainer getPanel(String panelId)
			{
				return new LocationPanel(panelId, p);
			}
		});

		tabs.add(new AbstractTab(new Model<String>("Add Trainer"))
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public WebMarkupContainer getPanel(String panelId)
			{
				return new AddTrainer(panelId);
			}
		});

		tabs.add(new AbstractTab(new Model<String>("Add Client"))
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public WebMarkupContainer getPanel(String panelId)
			{
				return new AddClient(panelId);
			}
		});

		tabs.add(new AbstractTab(new Model<String>("List Locations"))
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public WebMarkupContainer getPanel(String panelId)
			{
				return new SearchLocation(panelId);
			}
		});

		return tabs;
	}
}
