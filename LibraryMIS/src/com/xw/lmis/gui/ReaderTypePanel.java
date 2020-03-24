package com.xw.lmis.gui;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.xw.lmis.bll.ReaderTypeAdmin;
import com.xw.lmis.gui.commons.CustomizedTableModel;
import com.xw.lmis.model.Reader;
import com.xw.lmis.model.ReaderType;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReaderTypePanel extends JPanel {
	private JTextField tfRdTypeName;
	private JTextField tfRdType;
	private JTextField tfCanLendQty;
	private JTextField tfCanContinueTimes;
	private JTextField tfPunishRate;
	private JTextField tfDateValid;
	private JPanel InfoPanel;
	private JScrollPane ResultPanel;
/*	private JScrollPane scrollPane;*/
	private JPanel FunctionPanel;
	private JButton addType;
	private JButton UpdateType;
	private JButton DeleteType;
	private JButton Cancel;
	private JButton Enter;
	private JButton button;
	private JTable searchResultTable;
	private JTextField tfCanLendDay;
	
	private ReaderType[] hits = null;
	private ReaderTypeAdmin readerTypeBll = new ReaderTypeAdmin();
	

	private OpStatus ops;

	/**
	 * Create the panel.
	 */
	public ReaderTypePanel() {
		setBounds(100, 100, 895, 432);
		setLayout(null);
		
		initInfoPanel();
		initResultPanel();
		initFunctionPanel();
		setStatus(ops.inSelect);

	}
	


	private void initFunctionPanel() {
		FunctionPanel = new JPanel();
		FunctionPanel.setBounds(10, 327, 875, 54);
		add(FunctionPanel);
		FunctionPanel.setLayout(null);
		
		addType = new JButton("\u6DFB\u52A0");
		addType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setStatus(ops.inNew);
			}
		});
		addType.setBounds(36, 17, 93, 23);
		FunctionPanel.add(addType);
		
		UpdateType = new JButton("\u4FEE\u6539");
		UpdateType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = searchResultTable.getSelectedRow();
				if(selectedRow < 0){
					JOptionPane.showMessageDialog(null, "请选中一条数据");
					return;
				}
				
				setReaderTypeToText(((CustomizedTableModel<ReaderType>) searchResultTable.getModel()).getObjectAt(selectedRow));
				setStatus(ops.inChange);
			}
		});
		UpdateType.setBounds(160, 17, 93, 23);
		FunctionPanel.add(UpdateType);
		
		DeleteType = new JButton("\u5220\u9664");
		DeleteType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = searchResultTable.getSelectedRow();
				if(selectedRow < 0){
					JOptionPane.showMessageDialog(null, "请选中一条数据");
					return;
				}
				if(readerTypeBll.deleteReaderType
						(((CustomizedTableModel<ReaderType>) searchResultTable.getModel()).getObjectAt(selectedRow)) > 0){
					JOptionPane.showMessageDialog(null, "删除成功");
					hits = readerTypeBll.getReaderTypes();
					updateResultTable(hits);
					setStatus(ops.inSelect);
				}else{
					JOptionPane.showMessageDialog(null, "删除失败");
				}
			}
		});
		DeleteType.setBounds(285, 17, 93, 23);
		FunctionPanel.add(DeleteType);
		
		Enter = new JButton("\u786E\u5B9A");
		Enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((tfRdType.getText().trim().length() != 0)
						&&(tfRdTypeName.getText().trim().length() != 0)) {
					//让读者类型ID和读者类型名称不能为零
					if(ops == OpStatus.inNew){
						ReaderType readertype = getReaderTypeFromText();
						if(readerTypeBll.addReaderType(readertype) > 0){
							JOptionPane.showMessageDialog(null, "添加成功");
							hits = readerTypeBll.getReaderTypes();
							updateResultTable(hits);
						}else{
							JOptionPane.showMessageDialog(null, "添加失败");
							return;
						}
						setStatus(ops.inSelect);
					}else if(ops == OpStatus.inChange){
						ReaderType readertype = getReaderTypeFromText();
						if(readerTypeBll.updateReaderType(readertype) > 0){
							hits = readerTypeBll.getReaderTypes();
							updateResultTable(hits);
							JOptionPane.showMessageDialog(null, "修改成功");
						}else{
							JOptionPane.showMessageDialog(null, "修改失败");
							return;
						}
						setStatus(ops.inSelect);
					}
				}else {
					JOptionPane.showMessageDialog(null, "请正确输入读者类型信息");
					return;
				}
			}
		});
		Enter.setBounds(535, 17, 93, 23);
		FunctionPanel.add(Enter);
		
		Cancel = new JButton("\u53D6\u6D88");
		Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ops != OpStatus.inSelect){
					setStatus(ops.inSelect);
				}
			}
		});
		Cancel.setBounds(655, 17, 93, 23);
		FunctionPanel.add(Cancel);
		
		button = new JButton("\u8FD4\u56DE");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		button.setBounds(772, 17, 93, 23);
		FunctionPanel.add(button);
	}

	private void initResultPanel() {
		CustomizedTableModel<ReaderType> tableModel = new CustomizedTableModel<ReaderType>(
				readerTypeBll.getDisplayColumnNames(), readerTypeBll.getMethodNames());	
		searchResultTable = new JTable(tableModel);
		searchResultTable.setBounds(15, 26, 1119, 310);
		
		ResultPanel = new JScrollPane(searchResultTable);
		ResultPanel.setBorder(new TitledBorder(null, "\u67E5\u8BE2\u7ED3\u679C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		ResultPanel.setBounds(10, 141, 875, 176);
		add(ResultPanel);
		
		//初始化的时候直接显示所有的读者类型
		hits = readerTypeBll.getReaderTypes();
		updateResultTable(hits);
		
		//设置一次只能选择单条记录
		searchResultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
	}

	private void initInfoPanel() {
		InfoPanel = new JPanel();
		InfoPanel.setBounds(10, 6, 875, 127);
		InfoPanel.setBorder(new TitledBorder(null, "\u8BFB\u8005\u7C7B\u522B", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(InfoPanel);
		InfoPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u7C7B\u578BID\uFF1A");
		lblNewLabel.setBounds(52, 23, 78, 31);
		InfoPanel.add(lblNewLabel);
		
		JLabel label = new JLabel("\u53EF\u7EED\u501F\u6B21\u6570\uFF1A");
		label.setBounds(52, 69, 78, 31);
		InfoPanel.add(label);
		
		JLabel label_1 = new JLabel("\u7C7B\u578B\u540D\u79F0\uFF1A");
		label_1.setBounds(279, 23, 78, 31);
		InfoPanel.add(label_1);
		
		JLabel label_2 = new JLabel("\u53EF\u501F\u6570\u91CF\uFF1A");
		label_2.setBounds(497, 23, 78, 31);
		InfoPanel.add(label_2);
		
		JLabel label_3 = new JLabel("\u7F5A\u91D1\u7387\uFF1A");
		label_3.setBounds(279, 69, 78, 31);
		InfoPanel.add(label_3);
		
		JLabel label_4 = new JLabel("\u8BC1\u4EF6\u6709\u6548\u671F\uFF1A");
		label_4.setBounds(497, 69, 78, 31);
		InfoPanel.add(label_4);
		
		tfRdType = new JTextField();
		tfRdType.setBounds(128, 28, 95, 21);
		InfoPanel.add(tfRdType);
		tfRdType.setColumns(10);
		
		tfCanContinueTimes = new JTextField();
		tfCanContinueTimes.setColumns(10);
		tfCanContinueTimes.setBounds(128, 74, 95, 21);
		InfoPanel.add(tfCanContinueTimes);
		
		tfRdTypeName = new JTextField();
		tfRdTypeName.setColumns(10);
		tfRdTypeName.setBounds(345, 28, 95, 21);
		InfoPanel.add(tfRdTypeName);
		
		tfPunishRate = new JTextField();
		tfPunishRate.setColumns(10);
		tfPunishRate.setBounds(345, 74, 95, 21);
		InfoPanel.add(tfPunishRate);
		
		tfDateValid = new JTextField();
		tfDateValid.setColumns(10);
		tfDateValid.setBounds(574, 74, 95, 21);
		InfoPanel.add(tfDateValid);
		
		tfCanLendQty = new JTextField();
		tfCanLendQty.setColumns(10);
		tfCanLendQty.setBounds(574, 28, 95, 21);
		InfoPanel.add(tfCanLendQty);
		
		tfCanLendDay = new JTextField();
		tfCanLendDay.setColumns(10);
		tfCanLendDay.setBounds(770, 28, 95, 21);
		InfoPanel.add(tfCanLendDay);
		
		JLabel label_5 = new JLabel("\u53EF\u501F\u5929\u6570\uFF1A");
		label_5.setBounds(711, 23, 78, 31);
		InfoPanel.add(label_5);
	}

	private enum OpStatus{
		inNew,inChange,inSelect
	}
	
	private void setStatus(OpStatus opst){
		ops = opst;
		switch(ops){
			case inSelect:
				ResultPanel.setEnabled(true);
				tfRdType.setText("");
				tfRdTypeName.setText("");
				tfCanLendQty.setText("");
				tfCanLendDay.setText("");
				tfCanContinueTimes.setText("");
				tfPunishRate.setText("");
				tfDateValid.setText("");
				InfoPanel.setEnabled(false);
				FunctionPanel.setEnabled(true);
				setComponentStatusInPanel(InfoPanel, false);
				setComponentStatusInPanel(FunctionPanel, true);
				break;
			case inNew:
				InfoPanel.setEnabled(true);
				ResultPanel.setEnabled(false);
				FunctionPanel.setEnabled(true);
				setComponentStatusInPanel(InfoPanel, true);
				setComponentStatusInPanel(FunctionPanel, true);
				UpdateType.setEnabled(false);
				DeleteType.setEnabled(false);
				addType.setEnabled(false);
				break;
			case inChange:
				InfoPanel.setEnabled(true);
				ResultPanel.setEnabled(true);
				FunctionPanel.setEnabled(true);
				setComponentStatusInPanel(InfoPanel, true);
				setComponentStatusInPanel(FunctionPanel, true);
				tfRdType.setEnabled(false);
				UpdateType.setEnabled(false);
				addType.setEnabled(false);
				DeleteType.setEnabled(false);
				break;
		}
			
	}
	
	private void setComponentStatusInPanel(JPanel panel, boolean status) {
		for(Component comp: panel.getComponents()){
			comp.setEnabled(status);
		}
		
	}

	
	private void updateResultTable(ReaderType[] readerTypes){
		if(readerTypes == null){
			return;
		}
		CustomizedTableModel<ReaderType> tableModel = (CustomizedTableModel<ReaderType>) searchResultTable.getModel();
		tableModel.setRecords(readerTypes);
		tableModel.fireTableDataChanged();
	}
	
	private ReaderType getReaderTypeFromText(){
		ReaderType readerType = new ReaderType();
		
		readerType.setRdType(Integer.valueOf(tfRdType.getText()));
		readerType.setRdTypeName(tfRdTypeName.getText());
		readerType.setCanLendQty(Integer.valueOf(tfCanLendQty.getText()));
		readerType.setCanLendDay(Integer.valueOf(tfCanLendDay.getText()));
		readerType.setCanContinueTimes(Integer.valueOf(tfCanContinueTimes.getText()));
		readerType.setPunishRate(Float.valueOf(tfPunishRate.getText()));
		readerType.setDateValid(Integer.valueOf(tfDateValid.getText()));
		
		return readerType;
		
	}
	
	private void setReaderTypeToText(ReaderType readerType){
		
		tfRdType.setText(String.valueOf(readerType.getRdType()));
		tfRdTypeName.setText(readerType.getRdTypeName());
		tfCanLendQty.setText(String.valueOf(readerType.getCanLendQty()));
		tfCanLendDay.setText(String.valueOf(readerType.getCanLendDay()));
		tfCanContinueTimes.setText(String.valueOf(readerType.getCanContinueTimes()));
		tfPunishRate.setText(String.valueOf(readerType.getPunishRate()));
		tfDateValid.setText(String.valueOf(readerType.getDateValid()));
	}
}
