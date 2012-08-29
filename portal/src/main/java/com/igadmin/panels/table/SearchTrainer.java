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
import com.igadmin.auth.AppSession;
import com.igadmin.data.Location;
import com.igadmin.data.Trainer;
import com.igadmin.data.utils.StorageUtils;
import com.igadmin.models.ReloadingTrainerModel;

public class SearchTrainer extends Panel
{
	private static final long	serialVersionUID	= -3401769395236719002L;

	public SearchTrainer(final String id)
	{
		super(id);
		initComponents();
	}

	private void initComponents()
	{
		final ISortableDataProvider<Trainer> dataProvider = new SortableDataProvider<Trainer>()
		{
			private static final long	serialVersionUID	= 7723194267973185458L;

			@Override
			public Iterator<? extends Trainer> iterator(final int first, final int count)
			{
				final Location loc = ((AppSession) getSession()).getSessionLocation();

				return StorageUtils.get().getTrainerListForLocation(loc == null ? null : loc.getId(), first, count).iterator();
			}

			@Override
			public int size()
			{
				return StorageUtils.get().getTrainerListSize();
			}

			@Override
			public IModel<Trainer> model(final Trainer object)
			{
				return new ReloadingTrainerModel(object);
			}
		};

		final List<IColumn<Trainer>> columns = new ArrayList<IColumn<Trainer>>();
		columns.add(new PropertyColumn<Trainer>(new Model<String>("First Name"), "nameFirst"));
		columns.add(new PropertyColumn<Trainer>(new Model<String>("Last Name"), "nameLast"));
		columns.add(new PropertyColumn<Trainer>(new Model<String>("Email"), "emailAddress"));
		columns.add(new AbstractColumn<Trainer>(new Model<String>("Edit"), "edit")
		{
			private static final long	serialVersionUID	= -3618555427333914107L;

			@Override
			public void populateItem(final Item<ICellPopulator<Trainer>> cellItem, final String componentId, final IModel<Trainer> rawModel)
			{
				cellItem.add(makeProductLinkFragment(componentId, rawModel));
			}
		});

		final DefaultDataTable<Trainer> dt = new DefaultDataTable<Trainer>("searchTrainerDataTable", columns, dataProvider, 10);
		add(dt);
	}

	private Fragment makeProductLinkFragment(final String componentId, final IModel<Trainer> rowModel)
	{
		final Fragment productLinkFragment = new Fragment(componentId, "f1", SearchTrainer.this);
		final Link<Void> l = new Link<Void>("l")
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public void onClick()
			{
				final Trainer trainer = rowModel.getObject();
				final PageParameters params = new PageParameters();
				params.add("newTabId", TabPanelIndex.TRAINER.getIndex());
				params.add("trainerId", trainer.getId());
				setResponsePage(HomePage.class, params);
			}
		};
		productLinkFragment.add(l);
		l.add(new Label("label", new PropertyModel<String>(rowModel, "trainerDisplayName")));
		return productLinkFragment;
	}

}
