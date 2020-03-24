/*
 * 借阅记录实体类
 */
package com.xw.lmis.model;

import java.sql.Date;
import java.sql.ResultSet;

import com.xw.lmis.dal.SQLHelper;


public class Borrow extends AbstractModel{
	private String borrowID; //借书顺序号
	private int rdID;	 //读者序号
	private int bkID; 	 //图书序号
	private String bkName; //书名
	private String bkAuthor; //作者
	private int continueTimes; //续借次数（第一次借时，记为0）
	private Date dateOut; //借书日期
	private Date dateRetPlan; //应还日期
	private Date dateRetAct; //实际还书日期
	private int overDay; //超期天数
	private float punishMoney; //罚款金额
	private boolean hasReturn = false;
	
	public Borrow() {
		super();
	}

	public String getBorrowID() {
		return borrowID;
	}

	public void setBorrowID(String borrowID) {
		this.borrowID = borrowID;
	}

	public int getRdID() {
		return rdID;
	}

	public void setRdID(int rdID) {
		this.rdID = rdID;
	}

	public int getBkID() {
		return bkID;
	}

	public void setBkID(int bkID) {
		this.bkID = bkID;
	}

	public String getBkName() throws Exception{
		String bkName = null;
		if (bkID!=0) {
			ResultSet rs = SQLHelper.getResultSet("select bkName from TB_Book where bkID = "+bkID);
			try {
				if(rs != null){
					while(rs.next()){
						bkName = rs.getString("bkName");
					}
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return bkName;
		
	}

	public String getBkAuthor() {
		String bkAuthor = null;
		if (bkID!=0) {
			ResultSet rs = SQLHelper.getResultSet("select bkAuthor from TB_Book where bkAuthor = "+bkAuthor);
			try {
				if(rs != null){
					while(rs.next()){
						bkAuthor = rs.getString("bkAuthor");
					}
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return bkAuthor;
	}
	public void setBkAuthor(String bkAuthor) {
		this.bkAuthor = bkAuthor;
	}
	
	public int getContinueTimes() {
		return continueTimes;
	}

	public void setContinueTimes(int continueTimes) {
		this.continueTimes = continueTimes;
	}

	public Date getDateOut() {
		return dateOut;
	}

	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}

	public Date getDateRetPlan() {
		return dateRetPlan;
	}

	public void setDateRetPlan(Date dateRetPlan) {
		this.dateRetPlan = dateRetPlan;
	}

	public Date getDateRetAct() {
		return dateRetAct;
	}

	public void setDateRetAct(Date dateRetAct) {
		this.dateRetAct = dateRetAct;
	}

	public int getOverDay() {
		return overDay;
	}

	public void setOverDay(int overDay) {
		this.overDay = overDay;
	}

	public float getPunishMoney() {
		return punishMoney;
	}

	public void setPunishMoney(float punishMoney) {
		this.punishMoney = punishMoney;
	}

	public boolean isHasReturn() {
		return hasReturn;
	}
	
	public String wheaterReturn() {
		if(hasReturn == false) {
			return "未归还";
		}else {
			return "已归还";
		}
	}
	
	public void setHasReturn(boolean hasReturn) {
		this.hasReturn = hasReturn;
	}

	@Override
	public String toString() {
		return "Borrow [borrowID=" + borrowID + ", rdID=" + rdID + ", bkID=" + bkID + ", continueTimes=" + continueTimes
				+ ", dateOut=" + dateOut + ", dateRetPlan=" + dateRetPlan + ", dateRetAct=" + dateRetAct + ", overDay="
				+ overDay + ", punishMoney=" + punishMoney + ", hasReturn=" + hasReturn + "]";
	}
}

