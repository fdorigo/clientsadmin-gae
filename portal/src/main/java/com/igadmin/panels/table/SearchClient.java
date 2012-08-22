package com.igadmin.panels.table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.igadmin.HomePage;
import com.igadmin.TabPanelIndex;
import com.igadmin.auth.AppSession;
import com.igadmin.data.Client;
import com.igadmin.data.Location;
import com.igadmin.data.utils.StorageUtils;
import com.igadmin.models.ReloadingClientModel;

public class SearchClient extends Panel
{
	private static final long	serialVersionUID	= 2594398770205873773L;

	public SearchClient(final String id)
	{
		super(id);
		initComponents();
	}

	private void initComponents()
	{
		final ISortableDataProvider<Client> dataProvider = new SortableDataProvider<Client>()
		{
			private static final long	serialVersionUID	= 7723194267973185458L;

			@Override
			public Iterator<? extends Client> iterator(final int first, final int count)
			{
				if (getSort() == null)
				{
					setSort("nameLast", SortOrder.ASCENDING);
				}
				
				final SortParam sort = getSort();
				
				final Location loc = ((AppSession) getSession()).getSessionLocation();

				if (loc != null)
					return StorageUtils.get().getClientListForLocation(loc.getId(), sort, first, count).iterator();
				else
					return StorageUtils.get().getClientListForLocation(null, sort, first, count).iterator();
			}

			@Override
			public int size()
			{
				return StorageUtils.get().getClientListSize();
			}

			@Override
			public IModel<Client> model(final Client object)
			{
				return new ReloadingClientModel(object);
			}
		};

		final List<IColumn<Client>> columns = new ArrayList<IColumn<Client>>();
		columns.add(new PropertyColumn<Client>(new Model<String>("First Name"), "nameFirst", "nameFirst"));
		columns.add(new PropertyColumn<Client>(new Model<String>("Last Name"), "nameLast", "nameLast"));
		columns.add(new PropertyColumn<Client>(new Model<String>("Email"), "emailAddress"));
		columns.add(new AbstractColumn<Client>(new Model<String>("Edit"))
		{
			private static final long	serialVersionUID	= -3618555427333914107L;

			@Override
			public void populateItem(final Item<ICellPopulator<Client>> cellItem, final String componentId, final IModel<Client> rawModel)
			{
				cellItem.add(makeProductLinkFragment(componentId, rawModel));
			}
		});

		final DefaultDataTable<Client> clientsDataTable = new DefaultDataTable<Client>("searchClientDataTable", columns, dataProvider, 20);
		add(clientsDataTable);
	}

	private Fragment makeProductLinkFragment(final String componentId, final IModel<Client> rowModel)
	{
		final Fragment productLinkFragment = new Fragment(componentId, "f1", SearchClient.this);
		final Link<Void> l = new Link<Void>("l")
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public void onClick()
			{
				final Client client = rowModel.getObject();
				final PageParameters params = new PageParameters();
				params.add("newTabId", TabPanelIndex.CLIENT.getIndex());
				params.add("clientId", client.getId());
				setResponsePage(HomePage.class, params);
			}
		};
		productLinkFragment.add(l);
		l.add(new Label("label", new Model<String>("Edit")));
		return productLinkFragment;
	}
}
