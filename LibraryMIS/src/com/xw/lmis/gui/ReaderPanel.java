package com.xw.lmis.gui;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

import com.xw.lmis.bll.DepartmentTypeAdmin;
import com.xw.lmis.bll.ReaderAdmin;
import com.xw.lmis.bll.ReaderTypeAdmin;
import com.xw.lmis.gui.commons.CustomizedTableModel;
import com.xw.lmis.gui.commons.ImageFilter;
import com.xw.lmis.model.DepartmentType;
import com.xw.lmis.model.Reader;
import com.xw.lmis.model.ReaderType;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.border.LineBorder;

public class ReaderPanel extends JPanel {
	private JTextField tfUserName;
	private JTable searchResultTable;
	private JTextField tfReaderID;
	private JTextField tfReaderName;
	private JPasswordField passwordField;
	private JTextField tfNumBorrowed;
	private JTextField tfStatus;
	private JTextField tfReaderRole;
	private JTextField tfReaderPhone;
	private JTextField tfEmail;
	private JTextField tfDate;
	private JComboBox cbGender;
	private JComboBox cbReaderType;
	private JComboBox cbDeptType;
	
	private JPanel searchPanel;
	private JComboBox rdTypeComboBox;
	private JComboBox deptTypeComboBox;
	private JButton btnQuery;
	private JButton btnToExcel;
	private JPanel searchResultPanel;
	private JScrollPane scrollPane;
	private JPanel readerInfoPanel;
	private JButton btnLoadPictureFile;
	private JLabel lblPhoto;
	private JPanel functionCtrlPanel;
	private JButton btnNewReader;
	private JButton btnUpdateReader;
	private JButton btnLost;
	private JButton btnFound;
	private JButton btnCancelReader;
	private JButton btnClose;
	private JPanel editCtrlPanel;
	private JButton btnAddReader;
	private JButton btnSubmitUpdate;
	private JButton btnCancelEdit;
	
	private OpStatus ops;
	
	private ReaderTypeAdmin readerTypeBll = new ReaderTypeAdmin();
	private DepartmentTypeAdmin deptTypeBll = new DepartmentTypeAdmin();
	private ReaderAdmin readerBll = new ReaderAdmin();
	
	private int ReaderTypeIndex;
	private int deptTypeIndex;
	
	/**
	 * Create the panel.
	 */
	public ReaderPanel() {
		setLayout(null);
		setBounds(100, 100, 895, 432);

		//初始化各个Panel
		initSearchPanel();
		initSearchResultPanel();
		initReaderInfoPanel();
		initFunctionControlsPanel();
		initEditControlsPanel();
				
		//设置初始操作状态
		setStatus(OpStatus.inSelect);
		//添加动作监听器
		//addActionListeners();
	}

	//1
	private void initSearchPanel() {
		searchPanel = new JPanel();
		searchPanel.setBounds(0, 0, 885, 30);
		add(searchPanel);
		searchPanel.setLayout(null);
		
		JLabel newlabel = new JLabel("\u8BFB\u8005\u7C7B\u522B\uFF1A");
		newlabel.setBounds(10, 0, 73, 30);
		searchPanel.add(newlabel);
		
		JLabel label = new JLabel("\u5355\u4F4D\uFF1A");
		label.setBounds(185, 0, 47, 30);
		searchPanel.add(label);
		
		JLabel label_1 = new JLabel("\u59D3\u540D\uFF1A");
		label_1.setBounds(337, 0, 47, 30);
		searchPanel.add(label_1);
		
		tfUserName = new JTextField();
		tfUserName.setBounds(381, 5, 86, 21);
		searchPanel.add(tfUserName);
		tfUserName.setColumns(10);
		
		//查询
		btnQuery = new JButton("\u67E5\u8BE2");
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReaderType rdType=(ReaderType)rdTypeComboBox.getSelectedItem();
				DepartmentType deptType=(DepartmentType)deptTypeComboBox.getSelectedItem();
				String userName=tfUserName.getText().trim();
//				ReaderTypeIndex = rdTypeComboBox.getSelectedIndex();
//				deptTypeIndex = deptTypeComboBox.getSelectedIndex();
				Reader[] hits=readerBll.retrieveReaders(rdType,deptType,userName);
				//更新查询结果列表
				updateResultTable(hits);

			}
		});
		btnQuery.setBounds(537, 4, 93, 23);
		searchPanel.add(btnQuery);
		
		btnToExcel = new JButton("Excel");
		btnToExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
	                   exportTable(searchResultTable, new File("Reader.xls"));
	               } catch (IOException ex) {
	            	   System.out.println(ex.getMessage());
	                   ex.printStackTrace();
	               }
			}
		});
		btnToExcel.setBounds(652, 4, 93, 23);
		searchPanel.add(btnToExcel);
		
		rdTypeComboBox = new JComboBox(readerTypeBll.getReaderTypes());
		rdTypeComboBox.setBounds(75, 5, 86, 21);
		searchPanel.add(rdTypeComboBox);
		
		deptTypeComboBox = new JComboBox(deptTypeBll.getDepartmentTypes());
		deptTypeComboBox.setBounds(227, 5, 86, 21);
		searchPanel.add(deptTypeComboBox);
	}

	//2
	private void initSearchResultPanel() {
		searchResultPanel = new JPanel();
		searchResultPanel.setBorder(new TitledBorder(null, "\u67E5\u8BE2\u7ED3\u679C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		searchResultPanel.setToolTipText("");
		searchResultPanel.setBounds(0, 34, 569, 346);
		add(searchResultPanel);
		searchResultPanel.setLayout(null);
		

		
		CustomizedTableModel<Reader>tableModel = new CustomizedTableModel<Reader>(
				readerBll.getDisplayColumnNames(),readerBll.getMethodNames());
		searchResultTable = new JTable(tableModel);
		searchResultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//searchResultTable.setBounds(479, 341, -470, -336);
		
		scrollPane = new JScrollPane(searchResultTable);
		scrollPane.setBounds(10, 21, 549, 315);
		searchResultPanel.add(scrollPane);
	}

	//3
	private void initReaderInfoPanel() {
		readerInfoPanel = new JPanel();
		readerInfoPanel.setBorder(new TitledBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u8BFB\u8005\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "\u8BFB\u8005\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		readerInfoPanel.setBounds(580, 33, 305, 346);
		add(readerInfoPanel);
		readerInfoPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u501F\u4E66\u8BC1\u53F7\uFF1A");
		lblNewLabel.setBounds(10, 20, 82, 21);
		readerInfoPanel.add(lblNewLabel);
		
		JLabel label_2 = new JLabel("\u59D3\u540D\uFF1A");
		label_2.setBounds(10, 45, 82, 21);
		readerInfoPanel.add(label_2);
		
		JLabel label_3 = new JLabel("\u5BC6\u7801\uFF1A");
		label_3.setBounds(10, 70, 82, 21);
		readerInfoPanel.add(label_3);
		
		JLabel label_4 = new JLabel("\u6027\u522B\uFF1A");
		label_4.setBounds(10, 95, 82, 21);
		readerInfoPanel.add(label_4);
		
		JLabel label_5 = new JLabel("\u5DF2\u501F\u6570\u91CF\uFF1A");
		label_5.setBounds(10, 120, 82, 21);
		readerInfoPanel.add(label_5);
		
		JLabel label_6 = new JLabel("\u8BC1\u4EF6\u72B6\u6001\uFF1A");
		label_6.setBounds(10, 145, 82, 21);
		readerInfoPanel.add(label_6);
		
		JLabel label_7 = new JLabel("\u8BFB\u8005\u89D2\u8272\uFF1A");
		label_7.setBounds(10, 170, 82, 21);
		readerInfoPanel.add(label_7);
		
		JLabel label_8 = new JLabel("\u8BFB\u8005\u7C7B\u522B\uFF1A");
		label_8.setBounds(10, 195, 82, 21);
		readerInfoPanel.add(label_8);
		
		JLabel label_9 = new JLabel("\u5355\u4F4D\uFF1A");
		label_9.setBounds(10, 220, 82, 21);
		readerInfoPanel.add(label_9);
		
		JLabel label_10 = new JLabel("\u7535\u8BDD\u53F7\u7801\uFF1A");
		label_10.setBounds(10, 248, 82, 21);
		readerInfoPanel.add(label_10);
		
		JLabel label_11 = new JLabel("\u7535\u5B50\u90AE\u4EF6\uFF1A");
		label_11.setBounds(10, 276, 82, 21);
		readerInfoPanel.add(label_11);
		
		JLabel label_12 = new JLabel("\u529E\u8BC1\u65E5\u671F\uFF1A");
		label_12.setBounds(10, 300, 82, 21);
		readerInfoPanel.add(label_12);
		
		tfReaderID = new JTextField();
		tfReaderID.setEditable(false);
		tfReaderID.setBounds(75, 20, 82, 21);
		readerInfoPanel.add(tfReaderID);
		tfReaderID.setColumns(10);
		
		tfReaderName = new JTextField();
		tfReaderName.setColumns(10);
		tfReaderName.setBounds(75, 45, 82, 21);
		readerInfoPanel.add(tfReaderName);
		
		passwordField = new JPasswordField();//密码密文
		passwordField.setColumns(10);
		passwordField.setBounds(75, 70, 82, 21);
		readerInfoPanel.add(passwordField);
		
		String[] sex = {"男","女"};
		cbGender = new JComboBox(sex);
		cbGender.setBounds(75, 95, 82, 21);
		readerInfoPanel.add(cbGender);
		
		tfNumBorrowed = new JTextField();
		tfNumBorrowed.setEditable(false);
		tfNumBorrowed.setColumns(10);
		tfNumBorrowed.setBounds(75, 120, 82, 21);
		readerInfoPanel.add(tfNumBorrowed);
		
		tfStatus = new JTextField();
		tfStatus.setEditable(false);
		tfStatus.setColumns(10);
		tfStatus.setBounds(75, 145, 82, 21);
		readerInfoPanel.add(tfStatus);
		
		tfReaderRole = new JTextField();
		tfReaderRole.setEditable(false);
		tfReaderRole.setColumns(10);
		tfReaderRole.setBounds(75, 170, 82, 21);
		readerInfoPanel.add(tfReaderRole);
		
		cbReaderType = new JComboBox((readerTypeBll.getReaderTypes()));
		cbReaderType.setFocusable(false);
		cbReaderType.setEditable(true);
	
		cbReaderType.setBounds(75, 195, 82, 21);
		readerInfoPanel.add(cbReaderType);
		
		//载入图片
		btnLoadPictureFile = new JButton("\u56FE\u7247\u6587\u4EF6");
		btnLoadPictureFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.addChoosableFileFilter(new ImageFilter());
				int returnVal = fc.showOpenDialog(ReaderPanel.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						BufferedImage img = ImageIO.read(file);
						Image dimg = img.getScaledInstance(lblPhoto.getWidth(),
								lblNewLabel.getHeight(),Image.SCALE_SMOOTH);
						ImageIcon icon = new ImageIcon(dimg);
						lblPhoto.setIcon(icon);
					}catch(IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnLoadPictureFile.setBounds(184, 194, 93, 23);
		readerInfoPanel.add(btnLoadPictureFile);
		
		lblPhoto = new JLabel("");
		lblPhoto.setBorder(new LineBorder(Color.GRAY));
		lblPhoto.setBounds(179, 23, 116, 168);
		readerInfoPanel.add(lblPhoto);
		
		cbDeptType = new JComboBox(deptTypeBll.getDepartmentTypes());
		cbDeptType.setFocusable(false);
		cbDeptType.setEditable(true);
		cbDeptType.setBounds(75, 220, 128, 21);
		readerInfoPanel.add(cbDeptType);
		
		tfReaderPhone = new JTextField();
		tfReaderPhone.setColumns(10);
		tfReaderPhone.setBounds(75, 248, 128, 21);
		readerInfoPanel.add(tfReaderPhone);
		
		tfEmail = new JTextField();
		tfEmail.setColumns(10);
		tfEmail.setBounds(75, 276, 128, 21);
		readerInfoPanel.add(tfEmail);
		
		tfDate = new JTextField();
		tfDate.setEditable(false);
		tfDate.setColumns(10);
		tfDate.setBounds(75, 300, 128, 21);
		readerInfoPanel.add(tfDate);
	}	

	//4
	private void initFunctionControlsPanel() {
		functionCtrlPanel = new JPanel();
		functionCtrlPanel.setBounds(0, 379, 569, 36);
		add(functionCtrlPanel);
		functionCtrlPanel.setLayout(null);
		
		//办理证件
		btnNewReader = new JButton("\u529E\u7406\u8BC1\u4EF6");
		btnNewReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setStatus(OpStatus.inNew);
				SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
				String ReaderID = sdf.format(new java.util.Date());
				//根据当前时间的月份+时+分+秒来确定读者ID
				tfReaderID.setText(ReaderID);
				tfReaderName.setText("");
				passwordField.setText("");
				tfReaderRole.setText("");
				tfReaderPhone.setText("");
//				tfReaderID.setEditable(true);
				tfNumBorrowed.setText("0");
				tfStatus.setText("有效");
				tfEmail.setText("");
				tfReaderRole.setEditable(true);
				cbReaderType.setEnabled(true);
				java.util.Date day = new java.util.Date();
				SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
				tfDate.setText(dFormat.format(day));
				lblPhoto.setIcon(null);
			}
		});
		btnNewReader.setBounds(1, 5, 88, 23);
		functionCtrlPanel.add(btnNewReader);
		
		//信息变更
		btnUpdateReader = new JButton("\u4FE1\u606F\u66F4\u53D8");
		btnUpdateReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = searchResultTable.getSelectedRow();
				if(selectedRow<0) {
					JOptionPane.showMessageDialog(null, "请选择其中一条数据");
					return;	
				}
				setStatus(OpStatus.inChange);
				setReaderToText(((CustomizedTableModel<Reader>)searchResultTable
						.getModel()).getObjectAt(selectedRow));
				
			}
		});
		btnUpdateReader.setBounds(97, 5, 88, 23);
		functionCtrlPanel.add(btnUpdateReader);
		
		btnLost = new JButton("\u6302\u5931");
		btnLost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = searchResultTable.getSelectedRow();
				if(selectedRow<0){
					JOptionPane.showMessageDialog(null, "请选中一条数据！");
					return;
				}
				Reader rd = ((CustomizedTableModel<Reader>)searchResultTable.getModel()).getObjectAt(selectedRow);
				if (rd.getRdStatus().equals("挂失")) {
					JOptionPane.showMessageDialog(null, "该用户已经挂失！");
					return;
				}
				rd.setRdStatus("挂失");
				readerBll.updateReader(rd);
				ReaderType rdType=(ReaderType)rdTypeComboBox.getSelectedItem();
				DepartmentType deptType=(DepartmentType)deptTypeComboBox.getSelectedItem();
				String userName=tfUserName.getText().trim();
				Reader[] hits=readerBll.retrieveReaders(rdType,deptType,userName);
				//更新查询结果列表
				updateResultTable(hits);
				JOptionPane.showMessageDialog(null, "挂失成功！");
			}
		});
		btnLost.setBounds(190, 5, 88, 23);
		functionCtrlPanel.add(btnLost);
		
		btnFound = new JButton("\u89E3\u9664\u6302\u5931");
		btnFound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = searchResultTable.getSelectedRow();
				if(selectedRow<0){
					JOptionPane.showMessageDialog(null, "请选中一条数据！");
					return;
				}
				Reader rd = ((CustomizedTableModel<Reader>)searchResultTable.getModel()).getObjectAt(selectedRow);
				
				if (rd.getRdStatus().equals("有效")) {
					JOptionPane.showMessageDialog(null, "该用户已经为有效状态！");
					return;
				}
				rd.setRdStatus("有效");
				readerBll.updateReader(rd);
				Reader[] hits=readerBll.getReaderbyID(rd.getRdID());
				//更新查询结果列表
				updateResultTable(hits);
				
				JOptionPane.showMessageDialog(null, "解除成功！");
			}
		});
		btnFound.setBounds(287, 5, 88, 23);
		functionCtrlPanel.add(btnFound);
		
		//注销
		btnCancelReader = new JButton("\u6CE8\u9500");
		btnCancelReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = searchResultTable.getSelectedRow();
				if(selectedRow<0){
					JOptionPane.showMessageDialog(null, "请选中一条数据！");
					return;
				}
				
				int n = JOptionPane.showConfirmDialog(null, "是否注销", "提示",JOptionPane.YES_NO_OPTION);//i=0/1
				
				if(n == 0) {
					//点击确定
					Reader rd = ((CustomizedTableModel<Reader>)searchResultTable.getModel()).getObjectAt(selectedRow);

					readerBll.deleteReader(rd);
					JOptionPane.showMessageDialog(null, "注销成功");
					setStatus(OpStatus.inSelect);
				}else {
					return;
				}
				
				
			}
		});
		
		btnCancelReader.setBounds(385, 5, 88, 23);
		functionCtrlPanel.add(btnCancelReader);
		
		//返回
		btnClose = new JButton("返回");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			   /* Main mainGUI = new Main();
				mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//有用滴！
				mainGUI.setLocationRelativeTo(null);//设置窗口位置
				mainGUI.setVisible(true);//显示*/
				/*System.exit(0);*/
			}
		});
		btnClose.setBounds(481, 5, 88, 23);
		functionCtrlPanel.add(btnClose);
	}

	//5
	private void initEditControlsPanel() {
		editCtrlPanel = new JPanel();
		editCtrlPanel.setBounds(580, 379, 305, 36);
		add(editCtrlPanel);
		editCtrlPanel.setLayout(null);
		
		btnAddReader = new JButton("\u786E\u8BA4\u529E\u8BC1");
		btnAddReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((tfReaderID.getText().trim().length() == 0)
						||(tfReaderName.getText().trim().length() == 0)) {
					//让输入的读者ID和读者姓名不得为空
					JOptionPane.showMessageDialog(null, "请正确输入读者信息！");
					return;
				}
				if((passwordField.getPassword().length == 0)
						||(tfReaderRole.getText().trim().length() == 0)){
					//让输入的登录密码和读者角色不得为空
					JOptionPane.showMessageDialog(null, "请正确输入读者信息！");
					return;
				}
				try {
					readerBll.newReader(getReaderFromText());
				} catch (IOException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "办理借书证成功！");
				setStatus(OpStatus.inSelect);
			}
		});
		btnAddReader.setBounds(10, 5, 90, 23);
		editCtrlPanel.add(btnAddReader);
		
		btnSubmitUpdate = new JButton("\u786E\u8BA4\u53D8\u66F4");
		btnSubmitUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((tfReaderRole.getText().trim().length() == 0)
						||(tfReaderName.getText().trim().length() == 0)) {
					//让输入的读者类型和读者姓名不得为空
					JOptionPane.showMessageDialog(null, "请正确输入读者信息！");
					return;
				}
				try {
					readerBll.updateReader(getReaderFromText());
				} catch (IOException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "信息变更成功");
			}
		});
		btnSubmitUpdate.setBounds(107, 5, 90, 23);
		editCtrlPanel.add(btnSubmitUpdate);
		
		//取消
		btnCancelEdit = new JButton("\u53D6\u6D88");
		btnCancelEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setStatus(OpStatus.inSelect);
			}
		});
		btnCancelEdit.setBounds(205, 5, 80, 23);
		editCtrlPanel.add(btnCancelEdit);
		
	}


	private enum OpStatus{
		inSelect,inNew,inChange
	}
	
	private void setStatus(OpStatus opst){
		ops=opst;
		switch (ops){
		case inSelect:
			searchPanel.setEnabled(true);
			searchResultPanel.setEnabled(true);
			functionCtrlPanel.setEnabled(true);
			//更改Panel中但件的状态
			setComponentStatusInPanel(functionCtrlPanel,true);
			readerInfoPanel.setEnabled(false);
			readerInfoPanel.setVisible(false);
			editCtrlPanel.setEnabled(false);
			editCtrlPanel.setVisible(false);
			setComponentStatusInPanel(editCtrlPanel,false);
			break;
		case inNew:
			searchPanel.setEnabled(false);
			searchResultPanel.setEnabled(false);
			functionCtrlPanel.setEnabled(false);
			setComponentStatusInPanel(functionCtrlPanel,false);
			readerInfoPanel.setEnabled(true);
			readerInfoPanel.setVisible(true);
			editCtrlPanel.setVisible(true);
			editCtrlPanel.setEnabled(true);
			setComponentStatusInPanel(editCtrlPanel,true);
			btnSubmitUpdate.setEnabled(false);
			break;
		case inChange:
			searchPanel.setEnabled(false);
			searchResultPanel.setEnabled(false);
			functionCtrlPanel.setEnabled(false);
			setComponentStatusInPanel(functionCtrlPanel,false);
			readerInfoPanel.setEnabled(true);
			readerInfoPanel.setVisible(true);
			editCtrlPanel.setEnabled(true);
			editCtrlPanel.setVisible(true);
			setComponentStatusInPanel(editCtrlPanel,true);
			btnAddReader.setEnabled(false);
			break;
		}
	}
	
	private void setComponentStatusInPanel(JPanel panel, boolean status) {
		for(Component comp:panel.getComponents()) {
			comp.setEnabled(status);
		}
	}

	private void updateResultTable(Reader[] readers){
		if(readers==null){
			JOptionPane.showMessageDialog(null,"没有找到符合要求的证示：");
			return;
		}
		CustomizedTableModel<Reader> tableModel=(CustomizedTableModel<Reader>) searchResultTable
				.getModel();
		tableModel.setRecords(readers);
		//更新表单
		tableModel.fireTableDataChanged();
	}
	
	private void setReaderToText(Reader reader){
		tfReaderID.setText(String.valueOf(reader.getRdID()));
		tfReaderName.setText(reader.getRdName());
		passwordField.setText(reader.getRdPwd());
		tfNumBorrowed.setText(String.valueOf(reader.getRdBorrowQty()));
		tfStatus.setText(reader.getRdStatus());
		tfReaderRole.setText(String.valueOf(reader.getRdAdminRoles()));
		tfReaderPhone.setText(reader.getRdPhone());
		tfEmail.setText(reader.getRdEmail());
		tfDate.setText(String.valueOf(reader.getRdDateReg()));
		cbGender.setSelectedItem(reader.getRdSex());
		cbReaderType.setSelectedItem(readerTypeBll.getReaderTypeById(reader.getRdType()));
		cbDeptType.setSelectedItem(deptTypeBll.getobjectById(reader.getRdDept()));
//		cbReaderType.setSelectedIndex(ReaderTypeIndex);
//		cbDeptType.setSelectedIndex(deptTypeIndex);
		
		if(reader.getRdPhoto()!=null){
			Image image=null;
			try{
				InputStream in = new ByteArrayInputStream(reader.getRdPhoto());
				image=ImageIO.read(in);
			}catch(IOException e){
				e.printStackTrace();
			}
			lblPhoto.setIcon(new ImageIcon(image));
		}
	}

	private Reader getReaderFromText() throws IOException{
//		Reader reader=new Reader(Integer.valueOf(tfReaderID.getText()));
		Reader reader=new Reader();
		reader.setRdID(Integer.valueOf(tfReaderID.getText().trim()));
		reader.setRdName(tfReaderName.getText().trim());
		reader.setRdPwd(String.valueOf(passwordField.getPassword()));
		reader.setRdSex(cbGender.getSelectedItem().toString());
		reader.setRdBorrowQty(Integer.valueOf(tfNumBorrowed.getText().trim()));
		reader.setRdStatus(String.valueOf(tfStatus.getText().trim()));
		reader.setRdAdminRoles(Integer.valueOf(tfReaderRole.getText().trim()));
		reader.setRdType(((ReaderType)cbReaderType.getSelectedItem()).getRdType());
		reader.setRdDept(((DepartmentType)cbDeptType.getSelectedItem()).getDeptType());
		reader.setRdPhone(tfReaderPhone.getText().trim());
		reader.setRdEmail(tfEmail.getText().trim());
		reader.setRdDateReg(strToDate(tfDate.getText().trim()));
		if(lblPhoto.getIcon()!=null){
			Image image=((ImageIcon)lblPhoto.getIcon()).getImage();
			BufferedImage bi=new BufferedImage(image.getWidth(null),
					image.getHeight(null),BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2=bi.createGraphics();
			g2.drawImage(image,0,0,null);
			g2.dispose();
			ByteArrayOutputStream os=new ByteArrayOutputStream();
			try{
				ImageIO.write(bi,"png",os);
			}catch(IOException e){
				e.printStackTrace();
			}
			InputStream fis=new ByteArrayInputStream(os.toByteArray());
			byte[] byt = new byte[4096];
			byt=InputStreamTOByte(fis);
			reader.setRdPhoto(byt);
			}
		return reader;
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

    /**
	 * 日期装换
	 * @param strDate 字符串格式日期
	 * @return
	 */
	
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
	
	//用于导出Excel
	public void exportTable(JTable table, File file) throws IOException {
        TableModel model = table.getModel();
        FileWriter out = new FileWriter(file);
        
        for(int i=0; i < model.getColumnCount(); i++) {
            out.write(model.getColumnName(i) + "\t");
        }
        out.write("\n");
        for(int i=0; i< model.getRowCount(); i++) {
            for(int j=0; j < model.getColumnCount(); j++) {
                out.write(model.getValueAt(i,j).toString()+"\t");
            }
            out.write("\n");
        }
        out.close();
        System.out.println("write out to: " + file);
    }
}
