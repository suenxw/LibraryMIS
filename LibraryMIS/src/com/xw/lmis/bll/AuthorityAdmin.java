package com.xw.lmis.bll;

import java.sql.SQLException;

import com.xw.lmis.dal.AuthorityDAL;
import com.xw.lmis.dal.BookDAL;
import com.xw.lmis.dal.ReaderDAL;
import com.xw.lmis.model.Authority;

public class AuthorityAdmin extends LibraryBLL{
	public AuthorityAdmin() {
		dal = new AuthorityDAL();
	}

	public Authority[] getAuthorityBy(String value, String content) {
		try {
			return ((AuthorityDAL)dal).getObjectBy(value, content);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateReader(Authority authority) {
		try {
			dal.update(authority);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
