package com.xw.lmis.bll;

import java.sql.SQLException;

import com.xw.lmis.dal.DepartmentTypeDAL;
import com.xw.lmis.model.DepartmentType;
import com.xw.lmis.model.ReaderType;

public class DepartmentTypeAdmin extends LibraryBLL{
	DepartmentTypeDAL dal = new DepartmentTypeDAL();
	
	public DepartmentTypeAdmin() {
		dal = new DepartmentTypeDAL();
	}
	
	public DepartmentType[] getDepartmentTypes() {
		try {
			return (DepartmentType[]) dal.getAllObjects();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public DepartmentType getobjectById(String rdDept) {
		try {
			return (DepartmentType) dal.getObjectByID(rdDept);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
