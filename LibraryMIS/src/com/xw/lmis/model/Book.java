/*
 * ͼ��ʵ����
 */
package com.xw.lmis.model;

import java.util.Date;

public class Book extends AbstractModel{
	private int bkID; //ͼ�����
	private String bkName; //����
	private String bkAuthor; //����
	private String bkPress; //������
	private Date bkDatePress; //��������
	private float bkPrice; //�۸�
	private Date bkDateIn; //�������
	private String bkBrief; //���ݼ��
	private byte[] bkCover; //ͼ�������Ƭ
	private String bkStatus; //ͼ��״̬
	
	public Book() {
		
	}
	public int getBkID() {
		return bkID;
	}
	
	public void setBkID(int bkID) {
		this.bkID=bkID;
	}
	
	public String getBkName() {
		return bkName;
	}
	
	public void setBkName(String bkName) {
		this.bkName=bkName;
	}
	
	public String getBkAuthor() {
		return bkAuthor;
	}
	
	public void setBkAuthor(String bkAuthor) {
		this.bkAuthor=bkAuthor;
	}
	
	public String getBkPress() {
		return bkPress;
	}
	
	public void setBkPress(String bkPress) {
		this.bkPress=bkPress;
	}
	
	public Date getBkDatePress() {
		return bkDatePress;
	}
	
	public void setBkDatePress(Date bkDatePress) {
		this.bkDatePress=bkDatePress;
	}
	
	public float getBkPrice() {
		return bkPrice;
	}
	
	public void setBkPrice(float bkPrice) {
		this.bkPrice=bkPrice;
	}
	
	public Date getBkDateIn() {
		return bkDateIn;
	}
	
	public void setBkDateIn(Date bkDateIn) {
		this.bkDateIn=bkDateIn;
	}
	
	public String getBkBrief() {
		return bkBrief;
	}
	
	public void setBkBrief(String bkBrief) {
		this.bkBrief=bkBrief;
	}
	
	public byte[] getBkCover() {
		return bkCover;
	}
	
	public void setBkCover(byte[] bkCover) {
		this.bkCover=bkCover;
	}
	
	public String getBkStatus() {
		return bkStatus;
	}
	
	public void setBkStatus(String bkStatus) {
		this.bkStatus=bkStatus;
	}
}
