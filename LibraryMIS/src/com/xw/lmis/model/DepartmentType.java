package com.xw.lmis.model;

public class DepartmentType extends AbstractModel{
	private int dpID;
	private String deptType;
	
	public DepartmentType(DepartmentType rt) {
		setDeptType(rt.getDeptType());
		setdpID(rt.getdpID());
	}
	
	public DepartmentType() {
		
	}
	
	public String toString() {
			return this.deptType;
	}
	public int getdpID() {
		return dpID;
	}
	
	public void setdpID(int dpID) {
		this.dpID=dpID;
	}
	
	public String getDeptType() {
		return deptType;
	}
	
	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

}
