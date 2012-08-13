package com.igadmin.panels.table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.igadmin.auth.AppSession;
import com.igadmin.data.Client;
import com.igadmin.data.Location;
import com.igadmin.data.utils.StorageUtils;
import com.igadmin.models.ReloadingClientModel;

public class SearchClient extends Panel
{
	private static final long	serialVersionUID	= 2594398770205873773L;

	public SearchClient(String id)
	{
		super(id);
		initComponents();
	}

	private void initComponents()
	{
		ISortableDataProvider<Client> dataProvider = new SortableDataProvider<Client>()
		{
			private static final long	serialVersionUID	= 7723194267973185458L;

			@Override
			public Iterator<? extends Client> iterator(int first, int count)
			{
				Location loc = ((AppSession) getSession()).getSessionLocation();

				if (loc != null)
					return StorageUtils.getClientListForLocation(loc.getId(), first, count).iterator();
				else
					return StorageUtils.getClientListForLocation(null, first, count).iterator();
			}

			@Override
			public int size()
			{
				// TODO Optimize this call, need just the count not the whole
				// list of objects
				return StorageUtils.getLocationList().size();
			}

			@Override
			public IModel<Client> model(Client object)
			{
				return new ReloadingClientModel(object);
			}
		};

		List<IColumn<Client>> columns = new ArrayList<IColumn<Client>>();
		columns.add(new PropertyColumn<Client>(new Model<String>("First Name"), "nameFirst"));
		columns.add(new PropertyColumn<Client>(new Model<String>("Last Name"), "nameLast"));
		columns.add(new PropertyColumn<Client>(new Model<String>("Email"), "emailAddress"));
		columns.add(new AbstractColumn<Client>(new Model<String>("Edit"), "edit")
		{
			private static final long	serialVersionUID	= -3618555427333914107L;

			@Override
			public void populateItem(Item<ICellPopulator<Client>> cellItem, String componentId, IModel<Client> rawModel)
			{
				cellItem.add(new SearchClientEditPanel(componentId, rawModel));
			}
		});

		DefaultDataTable<Client> dt = new DefaultDataTable<Client>("searchClientDataTable", columns, dataProvider, 10);
		add(dt);
	}
}
