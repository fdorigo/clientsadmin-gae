package com.igadmin.auth;

import org.apache.log4j.Logger;
import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.igadmin.pages.BasePage;

public class LoginPage extends BasePage
{
	private static final long serialVersionUID = 7656261851135396404L;
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(LoginPage.class);

	public LoginPage()
	{
		this(null);
	}

	public LoginPage(final PageParameters parameters)
	{
      add(new SignInPanel("signInPanel"));
	}
}
