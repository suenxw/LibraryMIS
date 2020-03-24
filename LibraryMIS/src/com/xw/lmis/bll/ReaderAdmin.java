package com.xw.lmis.bll;

import java.sql.SQLException;

import javax.swing.ComboBoxModel;

import com.xw.lmis.dal.BookDAL;
import com.xw.lmis.dal.ReaderDAL;
import com.xw.lmis.model.Authority;
import com.xw.lmis.model.DepartmentType;
import com.xw.lmis.model.Reader;
import com.xw.lmis.model.ReaderType;;

public class ReaderAdmin extends LibraryBLL {
	
	//private ReaderDAL dal = new ReaderDAL();
	
	public ReaderAdmin() {
		dal = new ReaderDAL();
	}
	
	private ReaderDAL dall = new ReaderDAL();
	public Reader getReader(int rdID) {
		try {
			return (Reader) dall.getObjectByID(rdID);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Reader[] retrieveReaders(ReaderType rdType,DepartmentType deptType,
			String userName){
		try{
			return dall.getReadersBy(rdType,deptType,userName);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public Reader[] getReaderSex() {
		try {
			return (Reader[]) dall.getAllObjects();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//更新读者信息
	public void updateReader(Reader reader) {
		try {
			dal.update(reader);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int newReader(Reader reader){
		try {
			return dal.add(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void deleteReader(Reader reader) {
		try {
			dal.delete(reader);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updatePwd(Reader reader) {
		try {
			dal.update(reader);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Reader[] getReaderbyID(int rdID) {
		try{
			return dall.getReaderByID1(rdID);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

}
