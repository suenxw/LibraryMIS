package com.xw.lmis.bll;

import com.xw.lmis.dal.AbstractDAL;

public abstract class LibraryBLL {
	protected AbstractDAL dal;
	public String[] getDisplayColumnNames() {
		return dal.getPrettyColumnNames();
	}
	public String[] getMethodNames() {
		return dal.getMethodNames();
	}
}
