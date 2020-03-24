/*
 * 读者实体类
 */
package com.xw.lmis.model;

import java.util.Date;

public class Reader extends AbstractModel{
	private int rdID;
	private String rdName;
	private String rdSex;
	private int rdType;
	private String rdDept;
	private String rdPhone;
	private String rdEmail;
	private Date rdDateReg;
	private byte[] rdPhoto;
	private String rdStatus;
	private int rdBorrowQty;
	private String rdPwd;
	private int rdAdminRoles;
	
	public Reader() {
		
	}
	
	public int getRdID() {
		return rdID;
	}
	
	public void setRdID(int rdID) {
		this.rdID=rdID;
	}
	
	public String getRdName() {
		return rdName;
	}
	
	public void setRdName(String rdName) {
		this.rdName=rdName;
	}
	
	public String getRdSex() {
		return rdSex;
	}
	
	public void setRdSex(String rdSex) {
		this.rdSex=rdSex;
	}
	
	public int getRdType() {
		return rdType;
	}
	
	public void setRdType(int rdType) {
		this.rdType=rdType;
	}
	
	public String getRdDept() {
		return rdDept;
	}
	
	public void setRdDept(String rdDept) {
		this.rdDept=rdDept;
	}
	
	public String getRdPhone() {
		return rdPhone;
	}
	
	public void setRdPhone(String rdPhone) {
		this.rdPhone=rdPhone;
	}
	
	public String getRdEmail() {
		return rdEmail;
	}
	
	public void setRdEmail(String rdEmail) {
		this.rdEmail=rdEmail;
	}
	
	public Date getRdDateReg() {
		System.out.println(rdDateReg);
		return rdDateReg;
	}
	
	public void setRdDateReg(Date rdDateReg) {
		this.rdDateReg=rdDateReg;
	}
	
	public byte[] getRdPhoto() {
		return rdPhoto;
	}
	
	public void setRdPhoto(byte[] rdPhoto) {
		this.rdPhoto=rdPhoto;
	}
	
	public String getRdStatus() {
		return rdStatus;
	}
	
	public void setRdStatus(String rdStatus) {
		this.rdStatus=rdStatus;
	}
	
	public int getRdBorrowQty() {
		return rdBorrowQty;
	}
	
	public void setRdBorrowQty(int rdBorrowQty) {
		this.rdBorrowQty=rdBorrowQty;
	}
	
	public String getRdPwd() {
		return rdPwd;
	}
	
	public void setRdPwd(String rdPwd) {
		this.rdPwd=rdPwd;
	}
	
	public int getRdAdminRoles() {
		return rdAdminRoles;
	}
	
	public void setRdAdminRoles(int rdAdminRoles) {
		this.rdAdminRoles=rdAdminRoles;
	}
	
	public boolean isReaderAdmin() {
		return (this.rdAdminRoles & 1)>0;
	}//第二栏
	
	public boolean isBookAdmin() {
		return (this.rdAdminRoles & 2)>0;
	}//第一栏
	
	public boolean isBorrowAdmin() {
		return (this.rdAdminRoles & 4)>0;
	}//第三栏
	
	public boolean isSysAdmin() {
		return (this.rdAdminRoles & 8)>0;
	}//第四栏
}
