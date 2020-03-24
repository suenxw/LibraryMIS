package com.xw.lmis.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.xw.lmis.model.AbstractModel;
import com.xw.lmis.model.DepartmentType;

public class DepartmentTypeDAL extends AbstractDAL{
	
	public DepartmentType getObjectByID(String rdDept) throws SQLException{
		DepartmentType rt =null;
		ResultSet rs = SQLHelper.getResultSet("select * from TB_DepartmentType where deptType="+"'"+rdDept+"'");
		if(rs != null) {
			if(rs.next()) {
				rt = initDepartmentType(rs);
			}
			rs.close();
		}
		return rt;
	}


	public AbstractModel[] getAllObjects() throws Exception {
		// TODO Auto-generated method stub
		ArrayList<DepartmentType> objects = new ArrayList<DepartmentType>();
		ResultSet rs = SQLHelper.getResultSet("select * from TB_DepartmentType");
		if(rs != null) {
			while(rs.next()) {
				DepartmentType rt = initDepartmentType(rs);
				objects.add(rt);
			}
			rs.close();
		}
		DepartmentType[] types = new DepartmentType[objects.size()];
		objects.toArray(types);
		return types;
	}

	private DepartmentType initDepartmentType(ResultSet rs) throws SQLException{
		// TODO Auto-generated method stub
		DepartmentType rt = new DepartmentType();
		rt.setDeptType(rs.getString("deptType"));
		return rt;
	}

	public int add(AbstractModel object) throws Exception {
		// TODO Auto-generated method stub
		if(object instanceof DepartmentType == false) {
			throw new Exception("Can only handle DepartmentType");
		}
		DepartmentType rt = (DepartmentType) object;
		String sql = "insert into TB_DepartmentType(deptType,deID)"
					+ "VALUES(?,?)";
		Object[] params = new Object[2];
		params[0] = rt.getDeptType();
		params[1] = rt.getdpID();
		return SQLHelper.ExecSql(sql,params);
	}

	public int delete(AbstractModel object) throws Exception {
		// TODO Auto-generated method stub
		if(object instanceof DepartmentType == false) {
			throw new Exception("Can only handle DepartmentType");
		}
		DepartmentType rt = (DepartmentType) object;
		String sql = "delete from TB_DepartmentType where dpID = ?";
		Object[] params = new Object[] {rt.getdpID()};
		int rows = SQLHelper.ExecSql(sql,params);
		return rows;
	}

	public int update(AbstractModel object)throws Exception {
		if(object instanceof DepartmentType) {
			throw new Exception("Can only handle DepartmentType");
		}
		DepartmentType rt = (DepartmentType) object;
		String sql = "updata TB_DepartmentType set deptType = ?,"
					+ "deptID = ? where deptID = ?";
		Object[] params = new Object[] {
				rt.getDeptType(),
				rt.getdpID()
		};
		return SQLHelper.ExecSql(sql,params);
	}

	public String[] getPrettyColumnNames() {
		return null;
	}

	public String[] getMetthodNames() {
		return null;
	}

	public String[] getMethodNames() {
		return null;
	}


	@Override
	public AbstractModel getObjectByID(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
