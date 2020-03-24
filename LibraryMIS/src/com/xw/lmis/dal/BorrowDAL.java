package com.xw.lmis.dal;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.xw.lmis.model.AbstractModel;
import com.xw.lmis.model.Borrow;


public class BorrowDAL extends AbstractDAL {

	private String[] dispColNames = new String[]{
			"借书序号","图书序号","图书名称","续借次数","借阅日期","应还日期","实际还书日期","超期天数","超期金额"
			,"是否归还"
		};
	private String[] methodNames = new String[]{
			"getBorrowID","getBkID","getBkName","getContinueTimes","getDateOut",
			"getDateRetPlan","getDateRetAct","getOverDay","getPunishMoney","wheaterReturn"		
	};
	
	@Override
	public AbstractModel[] getAllObjects() throws Exception {
		ArrayList<Borrow> objects = new ArrayList<Borrow>();
		ResultSet rs = SQLHelper.getResultSet("select * from TB_Borrow");
		if(rs != null){
			while(rs.next()){
				Borrow bw = initBorrow(rs);
				objects.add(bw);
			}
			rs.close();
		}
		Borrow[] borrows = new Borrow[objects.size()];
		objects.toArray(borrows);
		return borrows;
	}

	@Override
	public int add(AbstractModel object) throws Exception {
		if(object instanceof Borrow == false){
			throw new Exception("Can only handle Borrow");
		}
		Borrow bw = (Borrow)object;
		String sql = "insert into TB_Borrow ("
				+ "BorrowID,rdID,bkID,ContinueTimes,"
				+ "DateOut,DateRetPlan,DateRetAct,"
				+ "OverDay,PunishMoney,HasReturn) "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = new Object[10];
		params[0]=bw.getBorrowID();
		params[1]=bw.getRdID();
		params[2]=bw.getBkID();
		params[3]=bw.getContinueTimes();
		params[4]=bw.getDateOut();
		params[5]=bw.getDateRetPlan();
		params[6]=bw.getDateRetAct();
		params[7]=bw.getOverDay();
		params[8]=bw.getPunishMoney();
		params[9]=bw.isHasReturn();
		return SQLHelper.ExecSql(sql,params);
	}

	@Override
	public int delete(AbstractModel object) throws Exception {
		if(object instanceof Borrow == false){
			throw new Exception("Can only handle Borrow");
		}
		Borrow bw = (Borrow)object;
		String sql = "delete from TB_Borrow where BorrowID = ?";
		Object[] params = new Object[]{bw.getBorrowID()};
		return SQLHelper.ExecSql(sql,params);
	}

	@Override
	public int update(AbstractModel object) throws Exception {
		if(object instanceof Borrow == false){
			throw new Exception("Can only handle Borrow");
		}
		Borrow bw = (Borrow)object;
		SimpleDateFormat sdf = new SimpleDateFormat();
		String sql = "update TB_Borrow set rdID = ?,bkID = ?,"
				+ "ContinueTimes = ?,DateOut = ?,DateRetPlan = ?,"
				+ "DateRetAct = ?,OverDay = ?,PunishMoney = ?,"
				+ "HasReturn = ? where BorrowID = ?";
		Object[] params = new Object[10];
		params[0]=bw.getRdID();
		params[1]=bw.getBkID();
		params[2]=bw.getContinueTimes();
		params[3]=bw.getDateOut();
		params[4]=bw.getDateRetPlan();
		params[5]=bw.getDateRetAct();
		params[6]=bw.getOverDay();
		params[7]=bw.getPunishMoney();
		params[8]=bw.isHasReturn();
		params[9]=bw.getBorrowID();
		return SQLHelper.ExecSql(sql,params);
	}

	@Override
	public AbstractModel getObjectByID(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Borrow[] getObjectByRdID(int rdID) throws SQLException{
		ArrayList<Borrow> objects = new ArrayList<Borrow>();
		ResultSet rs = SQLHelper.getResultSet("select * from TB_Borrow where rdID = " + rdID);
		if(rs != null){
			while(rs.next()){
				Borrow bw = initBorrow(rs);
				objects.add(bw);
			}
			rs.close();
		}
		Borrow[] borrows = new Borrow[objects.size()];
		objects.toArray(borrows);
		return borrows;
	}
	
	private Borrow initBorrow(ResultSet rs) throws SQLException{
		Borrow bw = new Borrow();
		bw.setBorrowID(rs.getString("BorrowID"));
		bw.setRdID(rs.getInt("rdID"));
		bw.setBkID(rs.getInt("bkID"));
		bw.setContinueTimes(rs.getInt("ContinueTimes"));
		bw.setDateOut(rs.getDate("DateOut"));
		bw.setDateRetPlan(rs.getDate("DateRetPlan"));
		bw.setDateRetAct(rs.getDate("DateRetAct"));
		bw.setOverDay(rs.getInt("OverDay"));
		bw.setPunishMoney(rs.getFloat("PunishMoney"));
		bw.setHasReturn(rs.getBoolean("HasReturn"));
		return bw;
	}
	
	public  boolean isBorrowed(Borrow borrow) throws SQLException {
		ResultSet rs  = SQLHelper.getResultSet("select rdID,bkID from TB_Borrow");
		if(rs !=null){
			while(rs.next()){
				int rdID = rs.getInt("rdID");
				int bkID = rs.getInt("bkID");
				if((borrow.getRdID()==rdID)&&borrow.getBkID()==bkID){
					return true;
				}
			}
			rs.close();
		}
		return false;
	}

	@Override
	public String[] getPrettyColumnNames() {
		return this.dispColNames;
	}

	@Override
	public String[] getMethodNames() {
		return this.methodNames;
	}
}

