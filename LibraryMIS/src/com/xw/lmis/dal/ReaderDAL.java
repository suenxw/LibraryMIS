package com.xw.lmis.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.xw.lmis.model.AbstractModel;
import com.xw.lmis.model.Authority;
import com.xw.lmis.model.Book;
import com.xw.lmis.model.DepartmentType;
import com.xw.lmis.model.Reader;
import com.xw.lmis.model.ReaderType;

public class ReaderDAL extends AbstractDAL{
	private String[] disColNames = new String[] {"ID","姓名","性别","读者类型",
			"院系","电话","email","读者状态","已借书数量","注册日期"
			};
	
	private String[] methodNames = new String[] {
			"getRdID","getRdName","getRdSex",
			"getRdType","getRdDept","getRdPhone",
			"getRdEmail","getRdStatus","getRdBorrowQty","getRdDateReg"
			};

	public int add(AbstractModel object)throws Exception {
		if(object instanceof Reader == false){
			throw new Exception("Can only handle Reader");
		}
		Reader rt=(Reader)object;
		String sql="insert into TB_Reader (rdID,"
		+"rdName,rdSex,rdType,"
		+"rdDept,rdPhone,rdEmail,"
		+"rdDateReg,rdPhoto,rdStatus,"
		+"rdBorrowQty,rdPwd,rdAdminRoles)"
		+"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[]params=new Object[13];
		params[0]=rt.getRdID();
		params[1]=rt.getRdName();
		params[2]=rt.getRdSex();
		params[3]=rt.getRdType();
		params[4]=rt.getRdDept();
		params[5]=rt.getRdPhone();
		params[6]=rt.getRdEmail();
		params[7]=rt.getRdDateReg();
		params[8]=rt.getRdPhoto();
		params[9]=rt.getRdStatus();
		params[10]=rt.getRdBorrowQty();
		params[11]=rt.getRdPwd();
		params[12]=rt.getRdAdminRoles();
		return SQLHelper.ExecSql(sql,params);
	}

	public int delete(AbstractModel object) throws Exception {
		if(object instanceof Reader == false){
			throw new Exception("Can only handle Reader");
		}
		Reader rt=(Reader) object;
		String sql="delete from TB_Reader where rdID=?";
		Object[] params=new Object[]{rt.getRdID()};
		int rows=SQLHelper.ExecSql(sql,params);
		return rows;
	}
	
	public int update(AbstractModel object)throws Exception {
		if(object instanceof Reader == false){
			throw new Exception("Can only handle Reader");
		}
		Reader rt=(Reader) object;
		String sql="update TB_Reader set rdName=?,"
				+"rdSex=?,"
				+"rdType=?,rdDept=?,"
				+"rdPhone=?,rdEmail=?,"
				+"rdDateReg=?,rdPhoto=?,"
				+"rdStatus=?,rdBorrowQty=?,"
				+"rdPwd=?,rdAdminRoles=? "
				+"where rdID=?";
		Object[]params=new Object[]{
				rt.getRdName(),rt.getRdSex(),
				rt.getRdType(),rt.getRdDept(),
				rt.getRdPhone(),rt.getRdEmail(),
				rt.getRdDateReg(),rt.getRdPhoto(),
				rt.getRdStatus(),rt.getRdBorrowQty(),
				rt.getRdPwd(),rt.getRdAdminRoles(),
				rt.getRdID()};
		return SQLHelper.ExecSql(sql,params);
	}

	public Reader getObjectByID(int rdID) throws SQLException {
		Reader rt = null;
		ResultSet rs=SQLHelper
				.getResultSet("select rdID,rdName,rdSex"
						+",rdType,rdDept,rdPhone"
						+",rdEmail,rdDateReg,rdPhoto"
						+",rdStatus,rdBorrowQty,rdPwd"
						+",rdAdminRoles from TB_Reader where rdID="
						+rdID);
		if(rs!=null){
			if(rs.next()){
				rt= initReader(rs);
			}
			rs.close();
		}
		return rt;
	}

	private Reader initReader(ResultSet rs) throws SQLException{
		Reader rt = new Reader();
		rt.setRdID(rs.getInt("rdID"));
		rt.setRdSex(rs.getString("rdSex"));
		rt.setRdName(rs.getString("rdName"));
		rt.setRdType(rs.getInt("rdType"));
		rt.setRdDept(rs.getString("rdDept"));
		rt.setRdPhone(rs.getString("rdPhone"));
		rt.setRdEmail(rs.getString("rdEmail"));
		rt.setRdDateReg(rs.getDate("rdDateReg"));
		rt.setRdPhoto(rs.getBytes("rdPhoto"));
		rt.setRdStatus(rs.getString("rdStatus"));
		rt.setRdBorrowQty(rs.getInt("rdBorrowQty"));
		rt.setRdPwd(rs.getString("rdPwd"));
		rt.setRdAdminRoles(rs.getInt("rdAdminRoles"));
		return rt;
	}
	
	//供JTable初始化表头时调用，即初始化表格结构
	public String[] getPrettyColumnNames() {
		return disColNames;
	}
	
	//返回获取指定JTable列数据所需要调用的Reader实体类方法名称
	public String[] getMethodNames() {
		return methodNames;
	}
	
	
	public Reader[] getReadersBy(ReaderType rdType,DepartmentType rdDept,
			String userName)throws SQLException{
		ArrayList<Reader>readers=new ArrayList<Reader>();
		int nameNo= rdType.getRdType();
		String sql="select * from TB_Reader WHERE rdType=? and "
			+"rdDept=? and rdName like ?";
		Object[]params=new Object[]{ nameNo,rdDept.getDeptType(),"%"+userName +"%"};
		ResultSet rs=SQLHelper.getResultSet(sql,params);
		
		if(rs!=null){
			while(rs.next()){
				Reader reader=initReader(rs);
						readers.add(reader);
			}
			rs.close();
		}
		if(readers.size()>0){
			Reader[]array=new Reader[readers.size()];
			readers.toArray(array);
			return array;
		}
			return null;
	}

	public AbstractModel[] getAllObjects() throws Exception {
		ArrayList<Reader> objects = new ArrayList<Reader>();
		ResultSet rs = SQLHelper.getResultSet("select * from TB_Reader");
		if(rs != null) {
			while(rs.next()) {
				Reader rt = initReader(rs);
				objects.add(rt);
			}
			rs.close();
		}
		Reader[] types = new Reader[objects.size()];
		objects.toArray(types);
		return types;
	}

	public Reader[] getReaderByID1(int rdID) throws SQLException {
		ArrayList<Reader>readers=new ArrayList<Reader>();
		String sql="select * from TB_Reader WHERE rdID = ?";
		Object[]params=new Object[]{ rdID };
		ResultSet rs=SQLHelper.getResultSet(sql,params);
		
		if(rs!=null){
			while(rs.next()){
				Reader reader=initReader(rs);
						readers.add(reader);
			}
			rs.close();
		}
		if(readers.size()>0){
			Reader[]array=new Reader[readers.size()];
			readers.toArray(array);
			return array;
		}
			return null;
	}

	
	
}
