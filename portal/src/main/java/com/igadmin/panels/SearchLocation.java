package com.igadmin.panels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.igadmin.data.Location;
import com.igadmin.data.utils.StorageUtils;
import com.igadmin.models.ReloadingLocationModel;

public class SearchLocation extends Panel
{
	private static final long	serialVersionUID	= -3401769395236719002L;

	public SearchLocation(String id)
	{
		super(id);
		initComponents();
	}

	private void initComponents()
	{
		ISortableDataProvider<Location> dataProvider = new SortableDataProvider<Location>()
		{
			private static final long	serialVersionUID	= 7723194267973185458L;

			@Override
			public Iterator<? extends Location> iterator(int first, int count)
			{
				return StorageUtils.getLocationList(first, count).iterator();
			}

			@Override
			public int size()
			{
				// TODO Optimize this call, need just the count not the whole
				// list of objects
				return StorageUtils.getLocationList().size();
			}

			@Override
			public IModel<Location> model(Location object)
			{
				return new ReloadingLocationModel(object);
			}
		};

		List<IColumn<Location>> columns = new ArrayList<IColumn<Location>>();
		columns.add(new PropertyColumn<Location>(new Model<String>("Location Name"), "locationName"));
		columns.add(new PropertyColumn<Location>(new Model<String>("Email"), "emailAddress"));
		
		DefaultDataTable<Location> dt = new DefaultDataTable<Location>("searchLocationDataTable", columns, dataProvider, 10);
		add(dt);
	}
}