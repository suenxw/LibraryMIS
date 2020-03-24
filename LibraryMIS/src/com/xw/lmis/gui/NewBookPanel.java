package com.xw.lmis.gui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.xw.lmis.bll.BookAdmin;
import com.xw.lmis.dal.BookDAL;
import com.xw.lmis.gui.Main;
import com.xw.lmis.gui.commons.ImageFilter;
import com.xw.lmis.model.Book;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.Color;

public class NewBookPanel extends JPanel {
	
	
	private JTextField tfbkID;
	private JTextField tfbkName;
	private JTextField tfbkAuthor;
	private JTextField tfbkPress;
	private JTextField tfbkDatePress;
	private JTextField tfbkPrice;
	private JTextField tfbkDateIn;
	private JTextField tfbkStatus;
	private JTextField tfbkBrief;
	private JPanel BaseInfoPanel;
	private JPanel IntroducePanel;
	private JPanel PhotoPanel;
	private JPanel FuctionPanel;
	private JButton add;
	private JButton Enter;
	private JButton Back;
	private JButton addPhoto;
	private JLabel bkCover;
	private JButton clear;
	
	private BookAdmin bookBll = new BookAdmin();
	private OpStatus ops = null;
	private String initStr="格式如：2016-9-14";
	
	private enum OpStatus{
		inSelect,inNew,inChange
	}
	
	public NewBookPanel() {
		setLayout(null);
		setBounds(100, 100, 895, 432);

		//初始化各个Panel
		initBaseInfoPanel();
		initIntroducePanel();
		initPhotoPanel();
		initFuctionPanel();
		
		//设置初始操作状态
		setStatus(ops.inSelect);
		
	}
	
	//用于BookSearchPanel的修改按钮
	public NewBookPanel(Book book){
		this();
		setBookToText(book);
		setStatus(ops.inChange);
	}

	private void initIntroducePanel(){
		IntroducePanel = new JPanel();
		IntroducePanel.setBorder(new TitledBorder(null, "\u5185\u5BB9\u7B80\u4ECB", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		IntroducePanel.setBounds(244, 6, 345, 333);
		add(IntroducePanel);
		IntroducePanel.setLayout(null);
		
		tfbkBrief = new JTextField();
		tfbkBrief.setBounds(15, 27, 313, 263);
		IntroducePanel.add(tfbkBrief);
		tfbkBrief.setColumns(10);
	}
	
	private void initBaseInfoPanel(){
		BaseInfoPanel = new JPanel();
		BaseInfoPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u56FE\u4E66\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		BaseInfoPanel.setBounds(9, 6, 225, 333);
		add(BaseInfoPanel);
		BaseInfoPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u56FE\u4E66\u5E8F\u53F7\uFF1A");
		lblNewLabel.setBounds(14, 17, 109, 20);
		BaseInfoPanel.add(lblNewLabel);
		
		JLabel label = new JLabel("\u4E66\u540D\uFF1A");
		label.setBounds(14, 47, 81, 20);
		BaseInfoPanel.add(label);
		
		JLabel label_1 = new JLabel("\u4F5C\u8005\uFF1A");
		label_1.setBounds(14, 77, 81, 20);
		BaseInfoPanel.add(label_1);
		
		JLabel label_2 = new JLabel("\u51FA\u7248\u793E\uFF1A");
		label_2.setBounds(14, 107, 81, 20);
		BaseInfoPanel.add(label_2);
		
		JLabel label_3 = new JLabel("\u51FA\u7248\u65E5\u671F\uFF1A");
		label_3.setBounds(14, 137, 109, 20);
		BaseInfoPanel.add(label_3);
		
		JLabel label_4 = new JLabel("\u4EF7\u683C\uFF1A");
		label_4.setBounds(14, 167, 81, 20);
		BaseInfoPanel.add(label_4);
		
		JLabel label_5 = new JLabel("\u5165\u7BA1\u65E5\u671F\uFF1A");
		label_5.setBounds(14, 197, 81, 20);
		BaseInfoPanel.add(label_5);
		
		JLabel label_6 = new JLabel("\u56FE\u4E66\u72B6\u6001\uFF1A");
		label_6.setBounds(14, 227, 109, 20);
		BaseInfoPanel.add(label_6);
		
		tfbkID = new JTextField();
		tfbkID.setBounds(85, 17, 113, 20);
		BaseInfoPanel.add(tfbkID);
		tfbkID.setColumns(10);
		
		tfbkName = new JTextField();
		tfbkName.setBounds(85, 47, 113, 20);
		BaseInfoPanel.add(tfbkName);
		tfbkName.setColumns(10);
		
		tfbkAuthor = new JTextField();
		tfbkAuthor.setBounds(85, 77, 113, 20);
		BaseInfoPanel.add(tfbkAuthor);
		tfbkAuthor.setColumns(10);
		
		tfbkPress = new JTextField();
		tfbkPress.setBounds(85, 107, 113, 20);
		BaseInfoPanel.add(tfbkPress);
		tfbkPress.setColumns(10);
		
		tfbkDatePress = new JTextField();
		tfbkDatePress.setBounds(85, 137, 113, 20);
		BaseInfoPanel.add(tfbkDatePress);
		tfbkDatePress.setColumns(10);
		
		tfbkPrice = new JTextField();
		tfbkPrice.setBounds(85, 167, 113, 20);
		BaseInfoPanel.add(tfbkPrice);
		tfbkPrice.setColumns(10);
		
		tfbkDateIn = new JTextField();
		tfbkDateIn.setBounds(85, 197, 113, 20);
		tfbkDateIn.setText("");
		BaseInfoPanel.add(tfbkDateIn);
		tfbkDateIn.setColumns(10);
		tfbkDateIn.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                JTextField src = (JTextField) e.getSource();
                src.setForeground(Color.LIGHT_GRAY);
                if (src.getText().equals(initStr)) {
                    src.setText(initStr);
                }
            }
            public void focusLost(FocusEvent e) {
                JTextField src = (JTextField) e.getSource();
                if (src.getText().trim().isEmpty()) {
                    src.setText(initStr);
                    src.setForeground(Color.LIGHT_GRAY);
                }
            }
        });
		
		tfbkStatus = new JTextField();
		tfbkStatus.setBounds(85, 227, 113, 20);
		BaseInfoPanel.add(tfbkStatus);
		tfbkStatus.setColumns(10);
	}

	private void initPhotoPanel(){
		PhotoPanel = new JPanel();
		PhotoPanel.setBorder(new TitledBorder(null, "\u5C01\u9762", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		PhotoPanel.setBounds(601, 6, 285, 333);
		add(PhotoPanel);
		PhotoPanel.setLayout(null);
		
		bkCover = new JLabel();
		bkCover.setBounds(15, 27, 260, 255);
		PhotoPanel.add(bkCover);
		
		addPhoto = new JButton("\u4E0A\u4F20\u56FE\u7247");
		addPhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.addChoosableFileFilter(new ImageFilter());
				int returnVal = fc.showSaveDialog(NewBookPanel.this);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					try{
						BufferedImage image = ImageIO.read(file);
						
						Image dImage  = image.getScaledInstance(bkCover.getWidth(),bkCover.getHeight(), Image.SCALE_SMOOTH);
						ImageIcon icon = new ImageIcon(dImage);
						bkCover.setIcon(icon);
					}catch (Exception ex) {
						// TODO: handle exception
						ex.printStackTrace();
					}
				}
			}
		});
		addPhoto.setBounds(85, 293, 123, 29);
		PhotoPanel.add(addPhoto);
	}
	
	private void initFuctionPanel(){
		FuctionPanel = new JPanel();
		FuctionPanel.setBounds(9, 347, 874, 74);
		add(FuctionPanel);
		FuctionPanel.setLayout(null);
		
		add = new JButton("\u6DFB\u52A0");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setStatus(ops.inNew);
				clear();
				tfbkDateIn.setText("格式如：2016-9-14");  //默认直接显示
				tfbkDateIn.setForeground(Color.GRAY);
				tfbkDatePress.setText("格式如：2016-9-14");  //默认直接显示
				tfbkDatePress.setForeground(Color.GRAY);
			}
		});
		
		add.setBounds(275, 26, 123, 29);
		FuctionPanel.add(add);
		
		Enter = new JButton("\u786E\u5B9A");
		Enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((tfbkID.getText().trim().length() == 0)
						||(tfbkName.getText().trim().length() == 0)) {
					//让图书的ID和名称不能为为空
					JOptionPane.showMessageDialog(null, "请正确输入图书信息");
					return;
				}else if((tfbkDateIn.getText().trim().length() == 0)
						||(tfbkStatus.getText().trim().length() == 0)) {
					//让图书的状态和入管日期不能为空
					JOptionPane.showMessageDialog(null, "请正确输入图书信息");
					return;
				}else if((tfbkDatePress.getText().trim().length() == 0)
						||(tfbkPrice.getText().trim().length() == 0)){
					//让图书的价格和出版日期不能为空
					JOptionPane.showMessageDialog(null, "请正确输入图书信息");
					return;
				}
				
				if(ops == OpStatus.inNew){
					try {
						if(bookBll.addBook(getBookFromText()) > 0){
							JOptionPane.showMessageDialog(null, "添加成功");
							clear();
							setStatus(ops.inSelect);
						}else{
							JOptionPane.showMessageDialog(null, "添加失败");
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}else if(ops == OpStatus.inChange){
					try {
						if(bookBll.updateBook(getBookFromText()) > 0){
							JOptionPane.showMessageDialog(null, "修改成功");
						}else{
							JOptionPane.showMessageDialog(null, "修改失败");
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					CardLayout cl = (CardLayout)Main.cards.getLayout();
					cl.show(Main.cards, Main.BookSearchName);
				}
			}
		});
		
		Enter.setBounds(420, 26, 123, 29);
		FuctionPanel.add(Enter);
		
		Back = new JButton("\u8FD4\u56DE");
		Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)Main.cards.getLayout();
				cl.show(Main.cards, Main.BookSearchName);
			}
		});
		
		Back.setBounds(650, 26, 123, 29);
		FuctionPanel.add(Back);
		
		clear = new JButton("\u6E05\u7A7A");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		
		clear.setBounds(18, 26, 123, 29);
		FuctionPanel.add(clear);
	}

	private void setStatus(OpStatus opst){
		ops = opst;
		switch(ops){
		case inSelect:
			setComponentStatusInPanel(BaseInfoPanel,false);
			setComponentStatusInPanel(IntroducePanel,false);
			setComponentStatusInPanel(PhotoPanel,false);
			add.setEnabled(true);
			clear.setEnabled(false);
			Enter.setEnabled(false);
			Back.setEnabled(false);
			addPhoto.setEnabled(false);
			break;
		case inNew:
			setComponentStatusInPanel(BaseInfoPanel,true);
			setComponentStatusInPanel(IntroducePanel,true);
			setComponentStatusInPanel(PhotoPanel,true);
			add.setEnabled(false);
			clear.setEnabled(true);
			Enter.setEnabled(true);
			Back.setEnabled(false);
			addPhoto.setEnabled(true);
			break;
		case inChange:
			setComponentStatusInPanel(BaseInfoPanel,true);
			tfbkID.setEnabled(false);
			setComponentStatusInPanel(IntroducePanel,true);
			setComponentStatusInPanel(PhotoPanel,true);
			clear.setEnabled(false);
			add.setEnabled(false);
			Enter.setEnabled(true);
			Back.setEnabled(true);
			addPhoto.setEnabled(true);
			break;
		}
	}
	
	private void setComponentStatusInPanel(JPanel panel,boolean status){

		for(Component comp : panel.getComponents()){
			comp.setEnabled(status);
		}
	}
	
	private void setBookToText(Book book){
		tfbkID.setText(String.valueOf(book.getBkID()));
		tfbkName.setText(book.getBkName());
		tfbkAuthor.setText(book.getBkAuthor());
		tfbkPress.setText(book.getBkPress());
		tfbkDatePress.setText(String.valueOf(book.getBkDatePress()));
		tfbkPrice.setText(String.valueOf(book.getBkPrice()));
		tfbkDateIn.setText(String.valueOf(book.getBkDateIn()));
		tfbkStatus.setText(book.getBkStatus());
		tfbkBrief.setText(book.getBkBrief());
		if(book.getBkCover() != null){
			Image image = null;
			try{
				InputStream in = new ByteArrayInputStream(book.getBkCover());
				image = ImageIO.read(in);
			}catch (IOException e) {
				e.printStackTrace();
			}
			bkCover.setIcon(new ImageIcon(image));
		}
	}
	
	private Book getBookFromText() throws IOException{
		Book book = new Book();
		book.setBkID(Integer.valueOf(tfbkID.getText()));
		book.setBkName(tfbkName.getText().trim());
		book.setBkAuthor(tfbkAuthor.getText().trim());
		book.setBkPress(tfbkPress.getText().trim());
		book.setBkDatePress(strToDate(tfbkDatePress.getText().trim()));
		book.setBkPrice(Float.valueOf(tfbkPrice.getText().trim()));
		book.setBkDateIn(strToDate(tfbkDateIn.getText().trim()));
		book.setBkBrief(tfbkBrief.getText().trim());
		book.setBkStatus("在馆");
		if(bkCover.getIcon() != null){
			Image image = ((ImageIcon)bkCover.getIcon()).getImage();
			BufferedImage bi = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2 = bi.createGraphics();
			g2.drawImage(image, 0, 0, null);
			g2.dispose();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try{
				ImageIO.write(bi, "png", os);
			}catch (Exception e) {
				e.printStackTrace();
			}
			InputStream fis = new ByteArrayInputStream(os.toByteArray());
			byte[] byt = new byte[4096];
			byt=InputStreamTOByte(fis);
			book.setBkCover(byt);
		}
		return book;
	}
	
	/**
     * 将InputStream转换成byte数组
     * @param in InputStream
     * @return byte[]
     * @throws IOException
     */
    public byte[] InputStreamTOByte(InputStream in) throws IOException{
        
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int count = -1;
        while((count = in.read(data,0,4096)) != -1)
            outStream.write(data, 0, count);
        
        data = null;
        return outStream.toByteArray();
    }
	
	private  Date strToDate(String strDate) {  
        String str = strDate;  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        java.util.Date d = null;  
        try {  
            d = format.parse(str);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        java.sql.Date date = new java.sql.Date(d.getTime());  
        return date;  
    } 

	private void clear(){
		tfbkID.setText("");
		tfbkName.setText("");
		tfbkAuthor.setText("");
		tfbkPress.setText("");
		tfbkDatePress.setText("");
		tfbkPrice.setText("");
		tfbkDateIn.setText("");
		tfbkStatus.setText("");
		tfbkBrief.setText("");
		bkCover.setIcon(null);
	}
}
