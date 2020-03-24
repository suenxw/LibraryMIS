/*
 * ���ļ�¼ʵ����
 */
package com.xw.lmis.model;

import java.sql.Date;
import java.sql.ResultSet;

import com.xw.lmis.dal.SQLHelper;


public class Borrow extends AbstractModel{
	private String borrowID; //����˳���
	private int rdID;	 //�������
	private int bkID; 	 //ͼ�����
	private String bkName; //����
	private String bkAuthor; //����
	private int continueTimes; //�����������һ�ν�ʱ����Ϊ0��
	private Date dateOut; //��������
	private Date dateRetPlan; //Ӧ������
	private Date dateRetAct; //ʵ�ʻ�������
	private int overDay; //��������
	private float punishMoney; //������
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
			return "δ�黹";
		}else {
			return "�ѹ黹";
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

