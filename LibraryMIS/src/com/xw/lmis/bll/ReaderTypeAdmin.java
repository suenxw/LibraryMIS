package com.xw.lmis.bll;

import java.sql.SQLException;

import javax.swing.ComboBoxModel;

import com.xw.lmis.dal.DepartmentTypeDAL;
import com.xw.lmis.dal.ReaderTypeDAL;
import com.xw.lmis.model.Reader;
import com.xw.lmis.model.ReaderType;

public class ReaderTypeAdmin extends LibraryBLL{
	
	public ReaderTypeAdmin() {
		dal = new ReaderTypeDAL();
	}
	
	public ReaderType[] getReaderTypes() {
		try {
			return (ReaderType[]) dal.getAllObjects();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ReaderType getReaderTypeById(int rdType) {
		try {
			return  (ReaderType) dal.getObjectByID(rdType);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int deleteReaderType(ReaderType readertype) {
		try {
			return dal.delete(readertype);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public int addReaderType(ReaderType readertype) {
		try {
			return dal.add(readertype);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public int updateReaderType(ReaderType readertype) {
		try {
			return dal.update(readertype);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
