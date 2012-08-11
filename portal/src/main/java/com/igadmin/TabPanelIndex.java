package com.igadmin;

public enum TabPanelIndex
{
	LOCATION(0), TRAINER(1), CLIENT(2), UNKNOWN(3);
	
	private int index;
	
	private TabPanelIndex(final int idx)
	{
		this.setIndex(idx);
	}

	public int getIndex()
	{
		return index;
	}

	private void setIndex(int index)
	{
		this.index = index;
	}
}
