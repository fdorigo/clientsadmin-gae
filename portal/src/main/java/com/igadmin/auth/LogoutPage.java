package com.igadmin.auth;

import com.igadmin.pages.BasePage;

public class LogoutPage extends BasePage
{
	private static final long	serialVersionUID	= 2926990292274187038L;

	public LogoutPage()
	{
		getSession().invalidate();
	}
}
