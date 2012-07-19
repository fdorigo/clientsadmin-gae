package com.igadmin;

import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.page.DefaultPageManagerContext;
import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.pageStore.memory.HttpSessionDataStore;
import org.apache.wicket.pageStore.memory.PageNumberEvictionStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.session.HttpSessionStore;
import org.apache.wicket.session.ISessionStore;
import org.apache.wicket.util.IProvider;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see com.igadmin.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<HomePage> getHomePage() {
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		this.setSessionStoreProvider(new IProvider() {

			public ISessionStore get() {
				// TODO Auto-generated method stub

				return new HttpSessionStore();
			}
		});
		
		this.setPageManagerProvider(new DefaultPageManagerProvider(this) {

			@Override
			protected IDataStore newDataStore() {
				// TODO Auto-generated method stub
				return new HttpSessionDataStore(
						new DefaultPageManagerContext(),
						new PageNumberEvictionStrategy(20));
			}

		});
	}
}
