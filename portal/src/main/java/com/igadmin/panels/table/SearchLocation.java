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
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.igadmin.HomePage;
import com.igadmin.TabPanelIndex;
import com.igadmin.data.Location;
import com.igadmin.data.utils.StorageUtils;
import com.igadmin.models.ReloadingLocationModel;

public class SearchLocation extends Panel
{
	private static final long	serialVersionUID	= -3401769395236719002L;

	public SearchLocation(final String id)
	{
		super(id);
		initComponents();
	}

	private void initComponents()
	{
		final ISortableDataProvider<Location> dataProvider = new SortableDataProvider<Location>()
		{
			private static final long	serialVersionUID	= 7723194267973185458L;

			@Override
			public Iterator<? extends Location> iterator(final int first, final int count)
			{
				return StorageUtils.get().getLocationList(first, count).iterator();
			}

			@Override
			public int size()
			{
				return StorageUtils.get().getLocationListSize();
			}

			@Override
			public IModel<Location> model(final Location object)
			{
				return new ReloadingLocationModel(object);
			}
		};

		final List<IColumn<Location>> columns = new ArrayList<IColumn<Location>>();
		columns.add(new PropertyColumn<Location>(new Model<String>("Location Name"), "locationName"));
		columns.add(new PropertyColumn<Location>(new Model<String>("Email"), "emailAddress"));
		columns.add(new AbstractColumn<Location>(new Model<String>("Edit"), "edit")
		{
			private static final long	serialVersionUID	= -3618555427333914107L;

			@Override
			public void populateItem(final Item<ICellPopulator<Location>> cellItem, final String componentId, final IModel<Location> rawModel)
			{
				cellItem.add(makeProductLinkFragment(componentId, rawModel));
			}
		});

		final DefaultDataTable<Location> dt = new DefaultDataTable<Location>("searchLocationDataTable", columns, dataProvider, 10);
		add(dt);
	}

	private Fragment makeProductLinkFragment(final String componentId, final IModel<Location> rowModel)
	{
		final Fragment productLinkFragment = new Fragment(componentId, "f1", SearchLocation.this);
		final Link<Void> l = new Link<Void>("l")
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public void onClick()
			{
				final Location l = rowModel.getObject();
				final PageParameters params = new PageParameters();
				params.add("newTabId", TabPanelIndex.LOCATION.getIndex());
				params.add("locationId", l.getId());
				setResponsePage(HomePage.class, params);
			}
		};
		productLinkFragment.add(l);
		l.add(new Label("label", new PropertyModel<String>(rowModel, Location.NAME_PROPERTY)));
		return productLinkFragment;
	}
}
