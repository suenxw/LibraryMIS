package com.xw.lmis.bll;

import java.sql.SQLException;

import com.xw.lmis.dal.BorrowDAL;
import com.xw.lmis.model.Borrow;

public class BorrowAdmin extends LibraryBLL{
	public BorrowAdmin(){
		dal = new BorrowDAL();
	}
	
	public Borrow[] getBorrowByRdID(int rdID){
		try {
			return ((BorrowDAL)dal).getObjectByRdID(rdID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	public boolean isBorrowed(Borrow borrow){
//		try {
//			return ((BorrowDAL)dal).isBorrowed(borrow);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
	
	public int addBorrowRecord(Borrow borrow){
		try {
			return dal.add(borrow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public int updateBorrowRecord(Borrow borrow){
		try {
			return dal.update(borrow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
