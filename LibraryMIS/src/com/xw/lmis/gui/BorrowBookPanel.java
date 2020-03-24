package com.xw.lmis.gui;

import java.awt.Dimension;
import java.sql.Date;
import java.text.ParseException;
//import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import com.xw.lmis.bll.BookAdmin;
import com.xw.lmis.bll.BorrowAdmin;
import com.xw.lmis.bll.ReaderAdmin;
import com.xw.lmis.bll.ReaderTypeAdmin;
import com.xw.lmis.gui.commons.CustomizedTableModel;
import com.xw.lmis.model.Book;
import com.xw.lmis.model.Borrow;
import com.xw.lmis.model.Reader;
import com.xw.lmis.model.ReaderType;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BorrowBookPanel extends JPanel {
	private JTextField tfRdID;
	private JTextField tfRdName;
	private JTextField tfRdDept;
	private JTextField tfRdType;
	private JTextField tfCanLendQty;
	private JTextField tfCanLendDay;
	private JTextField tfBorrowQty;
	private JTable BorrowedBookTable;
	private JTable SearchBookTable;
	private JTextField tfBkID;
	private JTextField tfBkName;
	private JPanel ReaderInfoPanel;
	private JButton ReaderSearchBtn;
	private JScrollPane BorrowedBookPanel;
	private JScrollPane SearchResultPanel;
	private JPanel FunctionPanel;
	private JButton BorrowBookBtn;
	private JButton ReturnBookBtn;
	private JButton ContinueBookBtn;
	private JButton payFee;
	private JButton CancelBtn;
	private JPanel SearchBookPanel;
	private JButton BookSearchBtn;
	
	private BorrowAdmin borrowBll = new BorrowAdmin();
	private BookAdmin bookBll = new BookAdmin();
	private ReaderTypeAdmin readerTypeBll = new ReaderTypeAdmin();
	private ReaderAdmin readerBll = new ReaderAdmin();
	private Borrow[] Allrecords = null;
	private Reader reader = null;
	private ReaderType readerType = null;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String RetAct = sdf.format(new java.util.Date());
	
	public BorrowBookPanel() {
		setLayout(null);
		setBounds(100, 100, 895, 432);

		//初始化各个面板
		initReaderInfoPanel();
		initBorrowBookPanel();
		initSearchBookPanel();
		initSearchResultPanel();
		initCtrlPanel();
		
	}
	
	private void initSearchBookPanel(){
		SearchBookPanel = new JPanel();
		SearchBookPanel.setBorder(new TitledBorder(null, "\u56FE\u4E66\u67E5\u8BE2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SearchBookPanel.setBounds(0, 184, 885, 66);
		add(SearchBookPanel);
		SearchBookPanel.setLayout(null);
		
		JLabel label_6 = new JLabel("\u56FE\u4E66\u5E8F\u53F7\uFF1A");
		label_6.setBounds(70, 24, 90, 21);
		SearchBookPanel.add(label_6);
		
		tfBkID = new JTextField();
		tfBkID.setBounds(144, 21, 96, 27);
		SearchBookPanel.add(tfBkID);
		tfBkID.setColumns(10);
		
		JLabel label_7 = new JLabel("\u56FE\u4E66\u540D\u79F0\uFF1A");
		label_7.setBounds(289, 24, 90, 21);
		SearchBookPanel.add(label_7);
		
		tfBkName = new JTextField();
		tfBkName.setBounds(365, 21, 96, 27);
		SearchBookPanel.add(tfBkName);
		tfBkName.setColumns(10);
		
		BookSearchBtn = new JButton("\u67E5\u8BE2");
		BookSearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					Book[] bks = bookBll.getBookByIdAndName(
							tfBkID.getText().trim(),tfBkName.getText().trim());
					if(bks != null){
						updateSearchResultTable(bks);
					}else{
						JOptionPane.showMessageDialog(null, "没有找到符合要求的记录");
					}
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		BookSearchBtn.setBounds(530, 20, 105, 29);
		SearchBookPanel.add(BookSearchBtn);
	}
	private void initCtrlPanel(){
		FunctionPanel = new JPanel();
		FunctionPanel.setBounds(0, 369, 885, 63);
		add(FunctionPanel);
		FunctionPanel.setLayout(null);
		
		//借书
		BorrowBookBtn = new JButton("\u501F\u9605");
		BorrowBookBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = SearchBookTable.getSelectedRow();
				if(selectedRow<0){
					JOptionPane.showMessageDialog(null, "请选中一条数据！");
					return;
				}else if(reader == null){
					JOptionPane.showMessageDialog(null, "找不到借阅读者！");
					return;
				}
				int BorrowedNums = Integer.valueOf(tfBorrowQty.getText().trim()) + 1;
				if(BorrowedNums > readerType.getCanLendQty()){
					JOptionPane.showMessageDialog(null, "超出借阅额度！");
					return;
				}else{
					Book book = ((CustomizedTableModel<Book>) SearchBookTable.getModel()).getObjectAt(selectedRow);
					if(book.getBkStatus().equals("外借")) {
						JOptionPane.showMessageDialog(null, "书籍被借出");
						return;
					}//判断书籍是否在馆

					reader.setRdBorrowQty(BorrowedNums);
					readerBll.updateReader(reader);
					
					Borrow borrowRecord = NewBorrowRecord(reader, book);
//					try{
//						//判断用户是否借过此书
//						if(borrowBll.isBorrowed(borrowRecord)){
//							JOptionPane.showMessageDialog(null, "该用户已经借阅过此书，请选择续借！");
//							return;
//						}
//						
//					}catch (Exception e3) {
//						e3.printStackTrace();
//					}
					if(borrowBll.addBorrowRecord(borrowRecord) > 0){
						JOptionPane.showMessageDialog(null, "借阅成功！");
						Allrecords = borrowBll.getBorrowByRdID(reader.getRdID());
						updateBorrowedBookTable(Allrecords);
						tfBorrowQty.setText(String.valueOf(BorrowedNums));//更新已借书数量
						bookBll.updateBookOut(book);//更新书籍为外借状态
						Book[] bks = bookBll.getBookByIdAndName(
								tfBkID.getText().trim(),tfBkName.getText().trim());
						if(bks != null){
							updateSearchResultTable(bks);
						}else{
							JOptionPane.showMessageDialog(null, "没有找到符合要求的记录");
						}//更新表格中书籍的状态
					}else{
						JOptionPane.showMessageDialog(null, "借阅失败");
						return;
					}
				}
			}
		});
		
		BorrowBookBtn.setBounds(37, 15, 123, 29);
		FunctionPanel.add(BorrowBookBtn);
		
		//还书
		ReturnBookBtn = new JButton("\u8FD8\u4E66");
		ReturnBookBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = BorrowedBookTable.getSelectedRow();
				if(selectedRow<0){
					JOptionPane.showMessageDialog(null, "请选择一条数据!");
					return;
				}else if(reader == null){
					JOptionPane.showMessageDialog(null, "找不到借阅者！");
					return;
				}
				
				Borrow borrowRecord = ((CustomizedTableModel<Borrow>) BorrowedBookTable.getModel()).getObjectAt(selectedRow);
				
				int timeIndex = String.valueOf(borrowRecord.getDateRetPlan()).compareTo(RetAct);
				//判断实际还书日期和应还书日期
				if(timeIndex > 0) {
					//没有超期
					borrowRecord.setDateRetAct(strToDate(RetAct));
					borrowRecord.setHasReturn(true);
					borrowBll.updateBorrowRecord(borrowRecord);//更新数据库中的Borrow表
				}else {
					//超期
					
					long Overday = dayDiff(RetAct, String.valueOf(borrowRecord.getDateRetPlan()));
					float punishMoney = readerType.getPunishRate()*Overday;
					borrowRecord.setOverDay((int)Overday);
					borrowRecord.setPunishMoney(punishMoney);
					borrowBll.updateBorrowRecord(borrowRecord);
					
					JOptionPane.showMessageDialog(null, "还书超期，需要缴纳罚金");
				}

				
				Allrecords = borrowBll.getBorrowByRdID(reader.getRdID());
				updateBorrowedBookTable(Allrecords);
				
				int BorrowedNums = Integer.valueOf(tfBorrowQty.getText().trim()) - 1;
				reader.setRdBorrowQty(BorrowedNums);
				readerBll.updateReader(reader);
				
				Book book =bookBll.getBkbyBkID(borrowRecord.getBkID());//用于更新书籍状态
				
				if(borrowRecord.getPunishMoney() == 0){
					Allrecords = borrowBll.getBorrowByRdID(reader.getRdID());
					updateBorrowedBookTable(Allrecords);
					
					tfBorrowQty.setText(String.valueOf(BorrowedNums));
					
					bookBll.updateBookIn(book);//更新被借书籍为在馆状态
					
					Book[] bks = bookBll.getBookByIdAndName(
							tfBkID.getText().trim(),tfBkName.getText().trim());
					if(bks != null){
						updateSearchResultTable(bks);//更新查询图书表
					}else{
						JOptionPane.showMessageDialog(null, "没有找到符合要求的记录");
					}//更新表格中书籍的状态
					JOptionPane.showMessageDialog(null, "还书成功！");
				}else{
					JOptionPane.showMessageDialog(null, "还书失败！");
				}
			}
		});
		
		ReturnBookBtn.setBounds(207, 15, 123, 29);
		FunctionPanel.add(ReturnBookBtn);
		
		ContinueBookBtn = new JButton("\u7EED\u501F");
		ContinueBookBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = BorrowedBookTable.getSelectedRow();
				if(selectedRow<0){
					JOptionPane.showMessageDialog(null, "请选中一条数据！");
					return;
				}else if(reader == null){
					JOptionPane.showMessageDialog(null, "找不到借阅读者！");
					return;
				}
				Borrow borrowRecord = ((CustomizedTableModel<Borrow>) BorrowedBookTable.getModel()).getObjectAt(selectedRow);
				int Continuetimes = borrowRecord.getContinueTimes() + 1;
				if(Continuetimes > readerType.getCanContinueTimes()){
					JOptionPane.showMessageDialog(null, "超出续借次数！");
				}else{
					Calendar c = Calendar.getInstance();

					int canBorrowDays = Integer.valueOf(tfCanLendDay.getText().trim());
					c.add(Calendar.DATE, canBorrowDays);
					String ContinueDay = c.get(Calendar.YEAR)+"-"+( c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE);
					borrowRecord.setContinueTimes(Continuetimes);
					borrowRecord.setDateRetPlan(strToDate(ContinueDay));
					borrowBll.updateBorrowRecord(borrowRecord);
					Allrecords = borrowBll.getBorrowByRdID(reader.getRdID());
					updateBorrowedBookTable(Allrecords);
					JOptionPane.showMessageDialog(null, "续借成功！");
				}
			}
		});
		
		ContinueBookBtn.setBounds(381, 15, 123, 29);
		FunctionPanel.add(ContinueBookBtn);
		
		CancelBtn = new JButton("\u8FD4\u56DE");
		CancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		CancelBtn.setBounds(717, 15, 123, 29);
		FunctionPanel.add(CancelBtn);
		
		payFee = new JButton("\u7F34\u7EB3\u7F5A\u91D1");
		payFee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = BorrowedBookTable.getSelectedRow();
				if(selectedRow<0){
					JOptionPane.showMessageDialog(null, "请选中一条数据！");
					return;
				}else if(reader == null){
					JOptionPane.showMessageDialog(null, "找不到借阅读者！");
					return;
				}
				Borrow borrowRecord = ((CustomizedTableModel<Borrow>) BorrowedBookTable.getModel()).getObjectAt(selectedRow);
				if(borrowRecord.getPunishMoney() > 0) {
					int BorrowedNums = Integer.valueOf(tfBorrowQty.getText().trim()) - 1;
					reader.setRdBorrowQty(BorrowedNums);
					readerBll.updateReader(reader);
					tfBorrowQty.setText(String.valueOf(BorrowedNums));
					
					borrowRecord.setDateRetAct(strToDate(RetAct));
					borrowRecord.setPunishMoney(0);
					borrowRecord.setHasReturn(true);
					borrowBll.updateBorrowRecord(borrowRecord);
					Allrecords = borrowBll.getBorrowByRdID(reader.getRdID());
					updateBorrowedBookTable(Allrecords);
					
					Book book =bookBll.getBkbyBkID(borrowRecord.getBkID());//用于更新书籍状态
					bookBll.updateBookIn(book);//更新被借书籍为在馆状态
					
					Book[] bks = bookBll.getBookByIdAndName(
							tfBkID.getText().trim(),tfBkName.getText().trim());
					if(bks != null){
						updateSearchResultTable(bks);//更新查询图书表
					}else{
						JOptionPane.showMessageDialog(null, "没有找到符合要求的记录");
					}//更新表格中书籍的状态
					
					JOptionPane.showMessageDialog(null, "缴费成功,书已归还");
				}else {
					JOptionPane.showMessageDialog(null, "无需缴费用");
					return;
				}
			}
		});
		payFee.setBounds(547, 15, 123, 29);
		FunctionPanel.add(payFee);
	}
	private void initSearchResultPanel(){
		CustomizedTableModel<Book> tableModel = new CustomizedTableModel<>(
				bookBll.getDisplayColumnNames(),bookBll.getMethodNames());
		SearchBookTable = new JTable(tableModel);
		SearchBookTable.setBounds(15, 26, 1140, 177);
		
		SearchResultPanel = new JScrollPane(SearchBookTable);
		SearchResultPanel.setBorder(new TitledBorder(null, "\u67E5\u8BE2\u7ED3\u679C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SearchResultPanel.setBounds(0, 249, 885, 114);
		add(SearchResultPanel);
	}
	private void initBorrowBookPanel(){
		CustomizedTableModel<Borrow> tableModel = new CustomizedTableModel<>(
				borrowBll.getDisplayColumnNames(),borrowBll.getMethodNames());
		BorrowedBookTable = new JTable(tableModel);
		BorrowedBookTable.setBounds(15, 24, 1140, 167);
		
		BorrowedBookPanel = new JScrollPane(BorrowedBookTable);
		BorrowedBookPanel.setBorder(new TitledBorder(null, "\u5DF2\u501F\u56FE\u4E66", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		BorrowedBookPanel.setBounds(0, 71, 885, 114);
		add(BorrowedBookPanel);
	}
	private void initReaderInfoPanel(){
		ReaderInfoPanel = new JPanel();
		ReaderInfoPanel.setBorder(new TitledBorder(null, "\u8BFB\u8005\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		ReaderInfoPanel.setBounds(0, 0, 895, 73);
		add(ReaderInfoPanel);
		ReaderInfoPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u8BFB\u8005\u7F16\u53F7\uFF1A");
		lblNewLabel.setBounds(10, 26, 96, 21);
		ReaderInfoPanel.add(lblNewLabel);
		
		tfRdID = new JTextField();
		tfRdID.setBounds(76, 26, 90, 21);
		ReaderInfoPanel.add(tfRdID);
		tfRdID.setColumns(10);
		
		ReaderSearchBtn = new JButton("\u67E5\u8BE2");
		ReaderSearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(tfRdID.getText().trim().length() == 0) {
						JOptionPane.showMessageDialog(null, "请输入有效的读者信息");
						return;
					}
					reader = readerBll.getReader(Integer.valueOf(tfRdID.getText().trim()));
					if(reader != null && ReaderisValid(reader)){
						//ReaderisValid(reader)判断用户是否在有效期
						readerType = readerTypeBll.getReaderTypeById(reader.getRdType());
						setReaderToText(reader);
						Allrecords = borrowBll.getBorrowByRdID(reader.getRdID());
						updateBorrowedBookTable(Allrecords);
						
					}else{
						JOptionPane.showMessageDialog(null, "不存在该用户");
						return;
					}
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		ReaderSearchBtn.setBounds(176, 26, 90, 21);
		ReaderInfoPanel.add(ReaderSearchBtn);
		
		JLabel label = new JLabel("\u8BFB\u8005\u59D3\u540D\uFF1A");
		label.setBounds(276, 15, 96, 21);
		ReaderInfoPanel.add(label);
		
		JLabel label_1 = new JLabel("\u8BFB\u8005\u5355\u4F4D\uFF1A");
		label_1.setBounds(503, 15, 96, 21);
		ReaderInfoPanel.add(label_1);
		
		JLabel label_2 = new JLabel("\u8BFB\u8005\u7C7B\u578B\uFF1A");
		label_2.setBounds(715, 15, 90, 21);
		ReaderInfoPanel.add(label_2);
		
		JLabel label_3 = new JLabel("\u53EF\u501F\u6570\u91CF\uFF1A");
		label_3.setBounds(276, 42, 96, 21);
		ReaderInfoPanel.add(label_3);
		
		JLabel label_4 = new JLabel("\u53EF\u501F\u5929\u6570\uFF1A");
		label_4.setBounds(503, 42, 90, 21);
		ReaderInfoPanel.add(label_4);
		
		JLabel label_5 = new JLabel("\u5DF2\u501F\u6570\u91CF\uFF1A");
		label_5.setBounds(715, 42, 96, 21);
		ReaderInfoPanel.add(label_5);
		
		tfRdName = new JTextField();
		tfRdName.setBounds(336, 15, 96, 21);
		ReaderInfoPanel.add(tfRdName);
		tfRdName.setColumns(10);
		tfRdName.setEnabled(false);
		
		tfRdDept = new JTextField();
		tfRdDept.setBounds(564, 15, 96, 21);
		ReaderInfoPanel.add(tfRdDept);
		tfRdDept.setColumns(10);
		tfRdDept.setEnabled(false);
		
		tfRdType = new JTextField();
		tfRdType.setBounds(776, 15, 96, 21);
		ReaderInfoPanel.add(tfRdType);
		tfRdType.setColumns(10);
		tfRdType.setEnabled(false);
		
		tfCanLendQty = new JTextField();
		tfCanLendQty.setBounds(336, 46, 96, 21);
		ReaderInfoPanel.add(tfCanLendQty);
		tfCanLendQty.setColumns(10);
		tfCanLendQty.setEnabled(false);
		
		tfCanLendDay = new JTextField();
		tfCanLendDay.setBounds(564, 46, 96, 21);
		ReaderInfoPanel.add(tfCanLendDay);
		tfCanLendDay.setColumns(10);
		tfCanLendDay.setEnabled(false);
		
		tfBorrowQty = new JTextField();
		tfBorrowQty.setBounds(776, 46, 96, 21);
		ReaderInfoPanel.add(tfBorrowQty);
		tfBorrowQty.setColumns(10);
		tfBorrowQty.setEnabled(false);
	}
	
	
	private boolean ReaderisValid(Reader reader){
		if(reader.getRdStatus().equals("有效")){
			return true;
		}
		return false;
	}
//	private boolean BookisValid(Book book){
//		if(book){
//			return true;
//		}
//		return false;
//	}
	private Borrow NewBorrowRecord(Reader reader, Book book){
		Borrow borrow = new Borrow();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmm");
		String borrowID = sdf.format(new java.util.Date())+reader.getRdID()+book.getBkID();
		borrow.setBorrowID(borrowID);//设置借书的ID，格式：时间+读者编号+书籍编号
		borrow.setRdID(reader.getRdID());
		borrow.setBkID(book.getBkID());
		borrow.setBkAuthor(book.getBkAuthor());
		Calendar c = Calendar.getInstance();
		String NowDay = c.get(Calendar.YEAR)+"-"+( c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE);
		c.add(Calendar.DATE, Integer.valueOf(tfCanLendDay.getText().trim()));
		String PlanDay = c.get(Calendar.YEAR)+"-"+( c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE);
		borrow.setDateOut(strToDate(NowDay));
		borrow.setDateRetPlan(strToDate(PlanDay));
		return borrow;
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
	
	private void setReaderToText(Reader reader){
		tfRdName.setText(reader.getRdName());
		tfRdDept.setText(reader.getRdDept());
		tfRdType.setText(readerType.getRdTypeName());
		tfCanLendQty.setText(String.valueOf(readerType.getCanLendQty()));
		tfCanLendDay.setText(String.valueOf(readerType.getCanLendDay()));
		tfBorrowQty.setText(String.valueOf(reader.getRdBorrowQty()));
		
	}
	
	private void updateBorrowedBookTable(Borrow[] borrows) {
		if(borrows == null){
			return;
		}
		CustomizedTableModel<Borrow> tableModel = (CustomizedTableModel<Borrow>) BorrowedBookTable.getModel();
		tableModel.setRecords(borrows);

		tableModel.fireTableDataChanged();
	}
	
	private void updateSearchResultTable(Book[] books) {
		if(books == null){
			JOptionPane.showMessageDialog(null, "没有找到符合要求的记录！");
			return;
		}
		CustomizedTableModel<Book> tableModel = (CustomizedTableModel<Book>) SearchBookTable.getModel();
		tableModel.setRecords(books);
	
		tableModel.fireTableDataChanged();
	}
	
	//用于计算两个String类型日期的差值
	private static long dayDiff(String date1, String date2) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		long diff=0l;
		try {
			long d1 = formater.parse(date1).getTime();
			long d2 = formater.parse(date2).getTime();
			diff=(d1-d2)/(1000 * 60 * 60 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diff;
	}
}
