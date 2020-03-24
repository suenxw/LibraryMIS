package com.xw.lmis.dal;

import java.sql.SQLException;

import com.xw.lmis.model.AbstractModel;
public abstract class AbstractDAL {
	public abstract AbstractModel[] getAllObjects() throws Exception;
	public abstract int add(AbstractModel object) throws Exception;
	public abstract int delete(AbstractModel object) throws Exception;
	public abstract int update(AbstractModel object) throws Exception;
	public abstract AbstractModel getObjectByID(int id) throws SQLException;
	public abstract String[] getPrettyColumnNames();
	public abstract String[] getMethodNames();
	
}
