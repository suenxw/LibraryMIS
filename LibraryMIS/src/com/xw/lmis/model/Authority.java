package com.xw.lmis.model;

public class Authority extends AbstractModel{
	private int rdID;
	private String rdName;
	private String rdPwd;
	private int rdAdminRoles;
	
	public Authority() {
		
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
}
