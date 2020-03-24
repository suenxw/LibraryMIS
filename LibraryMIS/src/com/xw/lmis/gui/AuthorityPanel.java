package com.xw.lmis.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;

import com.xw.lmis.bll.AuthorityAdmin;
import com.xw.lmis.bll.ReaderAdmin;
import com.xw.lmis.gui.commons.CustomizedTableModel;
import com.xw.lmis.model.Authority;
import com.xw.lmis.model.Book;
import com.xw.lmis.model.Reader;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;

public class AuthorityPanel extends JPanel {
	private JTable SearchResultTable;
	private JTextField SearchContent;
	private JTextField RdID;
	private JTextField RdName;
	private JTextField RdAuthority;
	private JTextField RdPwd;
	
	private JPanel SearchPanel;
	private JPanel functionCtrlPanel ;
	private JPanel InformationPanel;
	private JPanel editCtrlPanel;
	private JScrollPane SearchResultPanel;
	
	private JComboBox SearchKey;
	private JButton SearchBtn;
	private JButton Update;
	private JButton Return;
	private JButton Enter;
	private JButton Canel;
	
	private AuthorityAdmin authorityBll = new AuthorityAdmin();
	private ReaderAdmin readerBll = new ReaderAdmin();
	
	public static Map<String,String> AuthorityKey;
	
    private OpStatus ops = null;
    private JLabel lblY;
    private JLabel label_4;
    private JLabel label_5;
  
	
	
	private enum OpStatus{
		inSelect,inChange
	}
	
	
	/**
	 * Create the panel.
	 */
	public AuthorityPanel() {
		setBounds(100, 100, 895, 432);
		setLayout(null);
		
		initMapSearch();
		initSearchPanel();
		initSearchResultPanel();
		initfunctionCtrlPanel();
		initInformationPanel();
		initeditCtrlPanel();
		
		//设置初始操作状态
		setStatus(OpStatus.inSelect);
		
	}
	
	
	private void initMapSearch(){
		AuthorityKey = new HashMap<String,String>();
		AuthorityKey.put("用户编号", "rdID");
		AuthorityKey.put("用户姓名", "rdName");
		AuthorityKey.put("用户姓名", "rdPwd");
		AuthorityKey.put("用户权限", "rdAdminRoles");
	}
	
	private void initSearchPanel() {
		SearchPanel = new JPanel();
		SearchPanel.setBounds(10, 10, 606, 49);
		add(SearchPanel);
		SearchPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u68C0\u7D22\u5B57\u6BB5\uFF1A");
		lblNewLabel.setBounds(10, 10, 77, 29);
		SearchPanel.add(lblNewLabel);
		
		SearchKey = new JComboBox();
		SearchKey.setBounds(73, 14, 96, 21);
		Set set = AuthorityKey.keySet();
		Iterator e = set.iterator();
        while(e.hasNext()){
        	SearchKey.addItem(e.next());
        }
		SearchPanel.add(SearchKey);
		
		SearchContent = new JTextField();
		SearchContent.setBounds(216, 14, 256, 21);
		SearchPanel.add(SearchContent);
		SearchContent.setColumns(10);
		
		SearchBtn = new JButton("\u67E5\u8BE2");
		SearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String value = AuthorityKey.get(SearchKey.getSelectedItem());
				String content = SearchContent.getText().trim();
				try{
					updateResultTable(authorityBll.getAuthorityBy(value, content));
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		SearchBtn.setBounds(507, 13, 77, 23);
		SearchPanel.add(SearchBtn);
	}
	
	protected void updateResultTable(Authority[] authority) {
		if(authority == null){
			JOptionPane.showMessageDialog(null, "没有符合要求的记录！");
			return;
		}
		CustomizedTableModel<Authority> tableModel = (CustomizedTableModel<Authority>) SearchResultTable.getModel();
		tableModel.setRecords(authority);
		tableModel.fireTableDataChanged();
	}

	private void initSearchResultPanel() {
		CustomizedTableModel<Authority> tableModel = new CustomizedTableModel<>(
				authorityBll.getDisplayColumnNames(),authorityBll.getMethodNames());
		SearchResultTable = new JTable(tableModel);
		SearchResultPanel = new JScrollPane(SearchResultTable);
		SearchResultPanel.setBorder(new TitledBorder(null, "\u67E5\u8BE2\u7ED3\u679C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SearchResultPanel.setBounds(10, 72, 606, 255);
		add(SearchResultPanel);
		
		SearchResultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	private void initfunctionCtrlPanel() {
		functionCtrlPanel = new JPanel();
		functionCtrlPanel.setBounds(10, 338, 606, 84);
		add(functionCtrlPanel);
		functionCtrlPanel.setLayout(null);
		
		Update = new JButton("\u4FEE\u6539");
		Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = SearchResultTable.getSelectedRow();
				if(selectedRow<0) {
					JOptionPane.showMessageDialog(null, "请选择其中一条数据");
					return;	
				}
				setStatus(OpStatus.inChange);
				setReaderToText(((CustomizedTableModel<Authority>)SearchResultTable
						.getModel()).getObjectAt(selectedRow));
			}
		});
		Update.setBounds(100, 23, 118, 35);
		functionCtrlPanel.add(Update);
		
		Return = new JButton("\u8FD4\u56DE");
		Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		Return.setBounds(397, 23, 118, 35);
		functionCtrlPanel.add(Return);
	}


	private void initInformationPanel() {
		InformationPanel = new JPanel();
		InformationPanel.setBorder(new TitledBorder(null, "\u7528\u6237\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		InformationPanel.setBounds(626, 10, 259, 317);
		add(InformationPanel);
		InformationPanel.setLayout(null);
		
		JLabel label = new JLabel("\u7528\u6237\u7F16\u53F7\uFF1A");
		label.setBounds(10, 26, 77, 29);
		InformationPanel.add(label);
		
		JLabel label_1 = new JLabel("\u7528\u6237\u59D3\u540D\uFF1A");
		label_1.setBounds(10, 65, 77, 29);
		InformationPanel.add(label_1);
		
		JLabel label_2 = new JLabel("\u7528\u6237\u6743\u9650\uFF1A");
		label_2.setBounds(10, 148, 77, 29);
		InformationPanel.add(label_2);
		
		RdID = new JTextField();
		RdID.setBounds(84, 30, 107, 21);
		RdID.setEditable(false);
		InformationPanel.add(RdID);
		RdID.setColumns(10);
		
		RdName = new JTextField();
		RdName.setBounds(84, 69, 107, 21);
		RdName.setEditable(false);
		InformationPanel.add(RdName);
		RdName.setColumns(10);
		
		RdAuthority = new JTextField();
		RdAuthority.setBounds(84, 152, 107, 21);
		RdAuthority.setColumns(10);
		InformationPanel.add(RdAuthority);
		
		lblY = new JLabel("\u7528\u6237\u6743\u9650: 0-\u8BFB\u8005\u30011-\u501F\u4E66\u8BC1\u7BA1\u7406");
		lblY.setBounds(10, 191, 181, 21);
		InformationPanel.add(lblY);
		
		label_4 = new JLabel("          2-\u56FE\u4E66\u7BA1\u7406\u30014-\u501F\u9605\u7BA1\u7406");
		label_4.setBounds(10, 213, 213, 21);
		InformationPanel.add(label_4);
		
		label_5 = new JLabel("          8-\u7CFB\u7EDF\u7BA1\u7406\uFF0C\u53EF\u7EC4\u5408");
		label_5.setBounds(10, 234, 181, 21);
		InformationPanel.add(label_5);
		
		JLabel label_3 = new JLabel("\u7528\u6237\u5BC6\u7801\uFF1A");
		label_3.setBounds(10, 104, 77, 29);
		InformationPanel.add(label_3);
		
		RdPwd = new JTextField();
		RdPwd.setColumns(10);
		RdPwd.setBounds(84, 110, 107, 21);
		InformationPanel.add(RdPwd);
	}
	private void initeditCtrlPanel() {
		editCtrlPanel = new JPanel();
		editCtrlPanel.setBounds(626, 338, 259, 84);
		add(editCtrlPanel);
		editCtrlPanel.setLayout(null);
		
		Enter = new JButton("\u786E\u5B9A");
		Enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((RdAuthority.getText().trim().length() == 0)
						||(RdPwd.getText().trim().length() == 0)) {
					JOptionPane.showMessageDialog(null, "请正确输入信息");
					return;
				}
				try {
					authorityBll.updateReader(getReaderFromText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "信息变更成功");
			}
		});
		Enter.setBounds(10, 24, 93, 29);
		editCtrlPanel.add(Enter);
		
		Canel = new JButton("\u53D6\u6D88");
		Canel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setStatus(OpStatus.inSelect);
			}
		});
		Canel.setBounds(156, 24, 93, 29);
		editCtrlPanel.add(Canel);
	}

	protected Authority getReaderFromText() throws IOException{
		Authority authority=new Authority();
		authority.setRdID(Integer.valueOf(RdID.getText().trim()));
		authority.setRdName(RdName.getText().trim());
		authority.setRdPwd(RdPwd.getText().trim());
		authority.setRdAdminRoles(Integer.valueOf(RdAuthority.getText().trim()));
		return authority;
	}


	private void setStatus(OpStatus opst){
		ops=opst;
		switch (ops){
		case inSelect:
			SearchPanel.setEnabled(true);
			SearchResultPanel.setEnabled(true);
			functionCtrlPanel.setEnabled(true);
			//更改Pane1中但件的状态
			setComponentStatusInPanel(functionCtrlPanel,true);
			InformationPanel.setEnabled(false);
			InformationPanel.setVisible(false);
			editCtrlPanel.setEnabled(false);
			editCtrlPanel.setVisible(false);
			setComponentStatusInPanel(editCtrlPanel,false);
			break;
		case inChange:
			SearchPanel.setEnabled(false);
			SearchResultPanel.setEnabled(false);
			functionCtrlPanel.setEnabled(false);
			setComponentStatusInPanel(functionCtrlPanel,false);
			InformationPanel.setEnabled(true);
			InformationPanel.setVisible(true);
			editCtrlPanel.setEnabled(true);
			editCtrlPanel.setVisible(true);
			setComponentStatusInPanel(editCtrlPanel,true);
			Enter.setEnabled(true);
			break;
		}
	}
	
	private void setComponentStatusInPanel(JPanel panel, boolean status) {
		for(Component comp:panel.getComponents()) {
			comp.setEnabled(status);
		}
	}
	
	protected void setReaderToText(Authority authority) {
		RdID.setText(String.valueOf(authority.getRdID()));
		RdName.setText(authority.getRdName());
		RdPwd.setText(authority.getRdPwd());
		RdAuthority.setText(String.valueOf(authority.getRdAdminRoles()));
	}
}
