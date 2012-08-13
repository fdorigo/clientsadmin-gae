package com.igadmin.panels;

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
import com.igadmin.data.Location;
import com.igadmin.data.Trainer;
import com.igadmin.data.utils.StorageUtils;
import com.igadmin.models.ReloadingTrainerModel;

public class SearchTrainer extends Panel
{
	private static final long	serialVersionUID	= -3401769395236719002L;

	public SearchTrainer(String id)
	{
		super(id);
		initComponents();
	}

	private void initComponents()
	{
		ISortableDataProvider<Trainer> dataProvider = new SortableDataProvider<Trainer>()
		{
			private static final long	serialVersionUID	= 7723194267973185458L;

			@Override
			public Iterator<? extends Trainer> iterator(int first, int count)
			{
				Location loc = ((AppSession) getSession()).getSessionLocation();

				if (loc != null)
					return StorageUtils.getTrainerListForLocation(loc.getId(), first, count).iterator();
				else
					return StorageUtils.getTrainerListForLocation(null, first, count).iterator();
			}

			@Override
			public int size()
			{
				// TODO Optimize this call, need just the count not the whole
				// list of objects
				return StorageUtils.getLocationList().size();
			}

			@Override
			public IModel<Trainer> model(Trainer object)
			{
				return new ReloadingTrainerModel(object);
			}
		};

		List<IColumn<Trainer>> columns = new ArrayList<IColumn<Trainer>>();
		columns.add(new PropertyColumn<Trainer>(new Model<String>("First Name"), "nameFirst"));
		columns.add(new PropertyColumn<Trainer>(new Model<String>("Last Name"), "nameLast"));
		columns.add(new PropertyColumn<Trainer>(new Model<String>("Email"), "emailAddress"));
		columns.add(new AbstractColumn<Trainer>(new Model<String>("Edit"), "edit")
		{
			private static final long	serialVersionUID	= -3618555427333914107L;

			@Override
			public void populateItem(Item<ICellPopulator<Trainer>> cellItem, String componentId, IModel<Trainer> rawModel)
			{
				cellItem.add(new SearchTrainerEditPanel(componentId, rawModel));
			}
		});

		DefaultDataTable<Trainer> dt = new DefaultDataTable<Trainer>("searchTrainerDataTable", columns, dataProvider, 10);
		add(dt);
	}
}
