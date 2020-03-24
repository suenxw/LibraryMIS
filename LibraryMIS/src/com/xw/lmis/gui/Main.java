package com.xw.lmis.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Main extends JFrame {

	private JMenuBar menuBar;
	private JMenu MN_BookMgt;
	private JMenuItem MI_NewBook;
	private JMenuItem MI_BookSearch;
	private JMenu MN_ReaderMgt;
	private JMenuItem MI_NewReader;
	private JMenuItem MI_ReaderTypeMgt;
	private JMenu MN_BorrowMgt;
	private JMenuItem MI_Borrow;
	private JMenu MN_UserMgt;
	private JMenuItem MI_PermissionMgt;
	private JMenuItem MI_UpdatePassword;
	
	public final static String ReaderPanelName  = "Reader";
	public final static String ReaderTypePanelName = "ReaderType";
	public final static String NewBookPanelName = "NewBook";
	public final static String BookSearchName = "BookSearch";
	public final static String BorrowBookName = "BorrowBook";
	public final static String BlankPanelName = "Blank";
	public final static String AuthorityPanelName = "Ahthority";
	public final static String PwdChangePanelName = "PwdChange";
	public static JPanel cards;
	
	
	private BlankPanel blankPanel;
	private ReaderPanel readerPanel;
	private ReaderTypePanel readerTypePanel;
	private NewBookPanel newBookPanel;
	private BookSearchPanel bookSearchPanel;
	private BorrowBookPanel borrowBookPanel;
	private AuthorityPanel authorityPanel;
	private PwdChangePanel pwdChangePanel;
	private JLabel lblNewLabel;
	private JLabel label;
	private JTextField ReaderID;
	private JTextField ReaderName;
	
	/**
	 * Create the frame.
	 */
	public Main() {
		
		setSize(new Dimension(1000,600));
		setTitle("长江大学图书管理系统");
		
		initComponents();
		
		initCardPanels();
		initMenu();
	}

	/**
	 * 菜单跳转
	 */
	private void initComponents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 500);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		MN_BookMgt = new JMenu("图书管理");
		menuBar.add(MN_BookMgt);
		
		MI_NewBook = new JMenuItem("新书入库");
		MI_NewBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)cards.getLayout();
				cl.show(cards, NewBookPanelName);
			}
		});
		MN_BookMgt.add(MI_NewBook);
		
		MI_BookSearch = new JMenuItem("图书信息查询");
		MI_BookSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c1 = (CardLayout)(cards.getLayout());
				c1.show(cards, BookSearchName);
			}
		});
		MN_BookMgt.add(MI_BookSearch);
		
		MN_ReaderMgt = new JMenu("读者管理");
		menuBar.add(MN_ReaderMgt);
		
		MI_NewReader = new JMenuItem("\u529E\u7406\u501F\u4E66\u8BC1");
		
		MI_NewReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c1 = (CardLayout)(cards.getLayout());
				c1.show(cards, ReaderPanelName);
			}
		});
		MN_ReaderMgt.add(MI_NewReader);
		
		MI_ReaderTypeMgt = new JMenuItem("读者类别管理");
		MI_ReaderTypeMgt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)cards.getLayout();
				cl.show(cards, ReaderTypePanelName);
			}
		});
		MN_ReaderMgt.add(MI_ReaderTypeMgt);
		
		MN_BorrowMgt = new JMenu("借阅管理");
		menuBar.add(MN_BorrowMgt);
		
		MI_Borrow = new JMenuItem("\u501F\u9605\u7BA1\u7406");
		MI_Borrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)cards.getLayout();
				cl.show(cards, BorrowBookName);
			}
		});
		MN_BorrowMgt.add(MI_Borrow);
		
		MN_UserMgt = new JMenu("\u7528\u6237\u7BA1\u7406");
		menuBar.add(MN_UserMgt);
		
		MI_PermissionMgt = new JMenuItem("\u6743\u9650\u7BA1\u7406");
		MI_PermissionMgt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)cards.getLayout();
				cl.show(cards, AuthorityPanelName);
			}
		});
		MN_UserMgt.add(MI_PermissionMgt);
		
		MI_UpdatePassword = new JMenuItem("\u5BC6\u7801\u4FEE\u6539");
		MI_UpdatePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)cards.getLayout();
				cl.show(cards, PwdChangePanelName);
			}
		});
		MN_UserMgt.add(MI_UpdatePassword);
		
	}
	
	private void initMenu() {
		MN_ReaderMgt.setEnabled(Login.reader.isReaderAdmin());//设置单元格是否可用
		MN_BookMgt.setEnabled(Login.reader.isBookAdmin());
		MN_BorrowMgt.setEnabled(Login.reader.isBorrowAdmin());
		MN_UserMgt.setEnabled(Login.reader.isSysAdmin());
	}

	private void initCardPanels() {
		blankPanel = new BlankPanel();
		blankPanel.setVisible(true);
		
		readerPanel = new ReaderPanel();
		readerPanel.setVisible(false);
		
		readerTypePanel = new ReaderTypePanel();
		readerTypePanel.setVisible(false);
		
		newBookPanel = new NewBookPanel();
		newBookPanel.setVisible(false);
		
		bookSearchPanel = new BookSearchPanel();
		bookSearchPanel.setVisible(false);
		
		borrowBookPanel = new BorrowBookPanel();
		borrowBookPanel.setVisible(false);
		
		authorityPanel = new AuthorityPanel();
		authorityPanel.setVisible(false);
		
		pwdChangePanel = new PwdChangePanel();
		pwdChangePanel.setVisible(false);
		
		cards = new JPanel(new CardLayout());
		cards.add(blankPanel,BlankPanelName);
		
		lblNewLabel = new JLabel("\u7528\u6237\u7F16\u53F7\uFF1A");
		lblNewLabel.setBounds(10, 10, 65, 22);
		blankPanel.add(lblNewLabel);
		
		label = new JLabel("\u7528\u6237\u59D3\u540D\uFF1A");
		label.setBounds(10, 42, 65, 22);
		blankPanel.add(label);
		
		ReaderID = new JTextField();
		ReaderID.setEditable(false);
		ReaderID.setBounds(76, 11, 98, 21);
		blankPanel.add(ReaderID);
		ReaderID.setColumns(10);
		ReaderID.setText(String.valueOf(Login.reader.getRdID()));
		
		ReaderName = new JTextField();
		ReaderName.setEditable(false);
		ReaderName.setColumns(10);
		ReaderName.setBounds(76, 42, 98, 21);
		blankPanel.add(ReaderName);
		ReaderName.setText(Login.reader.getRdName());
		
		cards.add(readerPanel,ReaderPanelName);
		cards.add(readerTypePanel,ReaderTypePanelName);
		cards.add(newBookPanel,NewBookPanelName);
		cards.add(bookSearchPanel,BookSearchName);
		cards.add(borrowBookPanel,BorrowBookName);
		cards.add(authorityPanel,AuthorityPanelName);
		cards.add(pwdChangePanel,PwdChangePanelName);
		getContentPane().add(cards);
	}

}
