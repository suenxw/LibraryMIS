package com.xw.lmis.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.xw.lmis.model.AbstractModel;
import com.xw.lmis.model.Authority;
import com.xw.lmis.model.Book;
import com.xw.lmis.model.Reader;

public class AuthorityDAL extends AbstractDAL{
	private String[] disColNames = new String[]{
			"用户ID","用户姓名","用户密码","用户权限"
		};
	private String[] methodNames= new String[]{
			"getRdID","getRdName","getRdPwd","getRdAdminRoles"
				
	};
	@Override
	public AbstractModel[] getAllObjects() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int add(AbstractModel object) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(AbstractModel object) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(AbstractModel object) throws Exception {
		if(object instanceof Authority == false){
			throw new Exception("Can only handle Reader");
		}
		Authority rt=(Authority) object;
		String sql="update TB_Reader set rdName=?,"
				+"rdPwd=?,rdAdminRoles=? "
				+"where rdID=?";
		Object[]params=new Object[]{
				rt.getRdName(),rt.getRdPwd(),rt.getRdAdminRoles(),
				rt.getRdID()};
		return SQLHelper.ExecSql(sql,params);
	}

	@Override
	public AbstractModel getObjectByID(int id) throws SQLException {
		return null;
	}

	@Override
	public String[] getPrettyColumnNames() {
		return this.disColNames;
	}

	@Override
	public String[] getMethodNames() {
		return this.methodNames;
	}

	public Authority[] getObjectBy(String value, String content) throws SQLException{
		ArrayList<Authority> authorities = new ArrayList<Authority>();
		String sql = "Select rdID,rdName,rdPwd,rdAdminRoles from TB_Reader where "
				+ value +" like ?";
		Object[] params = new Object[]{"%"+ content + "%"};
		ResultSet rs = SQLHelper.getResultSet(sql,params);
		if(rs != null){
			while(rs.next()){
				Authority authority = initauthority(rs);
				authorities.add(authority);
			}
			rs.close();
		}
		if(authorities.size() > 0){
			Authority[] array = new Authority[authorities.size()];
			authorities.toArray(array);
			return array;
		}
		return null;
	}

	private Authority initauthority(ResultSet rs) throws SQLException{
		Authority authority = new Authority();
		authority.setRdID(rs.getInt("rdID"));
		authority.setRdName(rs.getString("rdName"));
		authority.setRdPwd(rs.getString("rdPwd"));
		authority.setRdAdminRoles(rs.getInt("rdAdminRoles"));
		return authority;
	}
}
