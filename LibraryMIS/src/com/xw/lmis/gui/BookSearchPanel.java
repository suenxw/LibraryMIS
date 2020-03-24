package com.xw.lmis.gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

import com.xw.lmis.bll.BookAdmin;
import com.xw.lmis.dal.BookDAL;
import com.xw.lmis.gui.commons.CustomizedTableModel;
import com.xw.lmis.model.Book;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class BookSearchPanel extends JPanel {
	private JTabbedPane tabbedPane;
	private JTextField SearchContent;
	private JTextField tfBookName;
	private JTextField tfBookAuthor;
	private JTextField tfBookPress;
	private JPanel SimpleSearchPanel;
	private JComboBox SearchKey;
	private JButton SearchBtn;
	private JPanel AdvancedSearchPanel;
	private JButton AdvancedSearchBtn;
	private JPanel FunctionPanel;
	public static JButton Update;
	private JButton Delete;
	private JButton ToExcel;
	private JButton Retrun;
	private NewBookPanel updateBookPanel;
	private BookAdmin bookBll = new BookAdmin();
	public static Map<String,String> BookKey;
	private JScrollPane ResultPanel;
	private JTable searchResultTable;
	private Book[] bks = null;
	private final String  UpdateBook= "UpdateBook";
	public BookSearchPanel() {
		setLayout(null);
		setBounds(100, 100, 895, 432);
		
		//初始化各个面板
		initMapSearch();
		initTabbedPanel();
		initSimpleSearchPanel();
		initSupSearchPanel();
		initResultPanel();
		initFunctionPanel();
		
	}
	
	private void initMapSearch(){
		BookKey = new HashMap<String,String>();
		BookKey.put("书名", "bkName");
		BookKey.put("作者", "bkAuthor");
		BookKey.put("出版社", "bkPress");
		BookKey.put("简介", "bkBrief");
	}
	

	private void initTabbedPanel(){
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 10, 885, 101);
		add(tabbedPane);
	}
	
	private void initFunctionPanel(){
		FunctionPanel = new JPanel();
		FunctionPanel.setBounds(0, 366, 885, 56);
		add(FunctionPanel);
		FunctionPanel.setLayout(null);
		
		Update = new JButton("\u4FEE\u6539");
		Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = searchResultTable.getSelectedRow();
				if(selectedRow<0){
					JOptionPane.showMessageDialog(null, "请选中一条数据！");
					return;
				}
				Book book = ((CustomizedTableModel<Book>) searchResultTable.getModel()).getObjectAt(selectedRow);
			
				updateBookPanel = new NewBookPanel(book);
				updateBookPanel.setVisible(false);
				Main.cards.add(updateBookPanel,UpdateBook);
				CardLayout cl = (CardLayout)Main.cards.getLayout();
				cl.show(Main.cards, UpdateBook);
			}
		});
		
		Update.setBounds(42, 10, 123, 29);
		FunctionPanel.add(Update);
		
		Delete = new JButton("\u5220\u9664");
		Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = searchResultTable.getSelectedRow();
				if(selectedRow<0){
					JOptionPane.showMessageDialog(null, "请选中一条数据！");
					return;
				}
				Book book = ((CustomizedTableModel<Book>) searchResultTable.getModel()).getObjectAt(selectedRow);
				if(bookBll.deleteBook(book) > 0){
					JOptionPane.showMessageDialog(null, "删除成功");
					updateResultTable(bookBll.getBooks());;
				}else{
					JOptionPane.showMessageDialog(null, "删除失败");
				}
			}
		});
		
		Delete.setBounds(250, 10, 123, 29);
		FunctionPanel.add(Delete);
		
		ToExcel = new JButton("\u5BFC\u51FAExcel");
		ToExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
	                   exportTable(searchResultTable, new File("Book.xlsx"));
	               } catch (IOException ex) {
	            	   System.out.println(ex.getMessage());
	                   ex.printStackTrace();
	               }
			}
		});
		ToExcel.setBounds(523, 10, 123, 29);
		FunctionPanel.add(ToExcel);
		
		Retrun = new JButton("\u8FD4\u56DE");
		Retrun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		Retrun.setBounds(732, 10, 123, 29);
		FunctionPanel.add(Retrun);
	}
	
	private void initResultPanel(){
		CustomizedTableModel<Book> tableModel = new CustomizedTableModel<>(
				bookBll.getDisplayColumnNames(),bookBll.getMethodNames());
		searchResultTable = new JTable(tableModel);
		ResultPanel = new JScrollPane(searchResultTable);
		ResultPanel.setBorder(new TitledBorder(null, "\u67E5\u8BE2\u7ED3\u679C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		ResultPanel.setBounds(0, 113, 885, 243);
		add(ResultPanel);
		searchResultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	}

	private void initSupSearchPanel(){
		AdvancedSearchPanel = new JPanel();
		tabbedPane.addTab("高级查询", null, AdvancedSearchPanel, null);
		AdvancedSearchPanel.setLayout(null);
		
		JLabel label_1 = new JLabel("\u56FE\u4E66\u540D\u79F0\uFF1A");
		label_1.setBounds(10, 10, 105, 37);
		AdvancedSearchPanel.add(label_1);
		
		tfBookName = new JTextField();
		tfBookName.setBounds(74, 15, 144, 27);
		AdvancedSearchPanel.add(tfBookName);
		tfBookName.setColumns(10);
		
		JLabel label_2 = new JLabel("\u4F5C\u8005\uFF1A");
		label_2.setBounds(271, 18, 99, 21);
		AdvancedSearchPanel.add(label_2);
		
		tfBookAuthor = new JTextField();
		tfBookAuthor.setBounds(322, 15, 144, 27);
		AdvancedSearchPanel.add(tfBookAuthor);
		tfBookAuthor.setColumns(10);
		
		JLabel label_3 = new JLabel("\u51FA\u7248\u793E\uFF1A");
		label_3.setBounds(513, 18, 81, 21);
		AdvancedSearchPanel.add(label_3);
		
		tfBookPress = new JTextField();
		tfBookPress.setBounds(566, 15, 144, 27);
		AdvancedSearchPanel.add(tfBookPress);
		tfBookPress.setColumns(10);
		
		AdvancedSearchBtn = new JButton("\u641C\u7D22");
		AdvancedSearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bkName = tfBookName.getText().trim();
				String bkAuthor = tfBookAuthor.getText().trim();
				String bkPress = tfBookPress.getText().trim();
				try{
					updateResultTable(bookBll.getBookBy(bkName, bkAuthor, bkPress));
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		AdvancedSearchBtn.setBounds(776, 14, 69, 29);
		AdvancedSearchPanel.add(AdvancedSearchBtn);
	}

	private void initSimpleSearchPanel(){
		SimpleSearchPanel = new JPanel();
		tabbedPane.addTab("简单查询", null, SimpleSearchPanel, null);
		SimpleSearchPanel.setLayout(null);
		
		JLabel label = new JLabel("\u68C0\u7D22\u5B57\u6BB5\uFF1A");
		label.setBounds(32, 10, 105, 37);
		SimpleSearchPanel.add(label);
		
		SearchKey = new JComboBox();
		SearchKey.setBounds(101, 15, 142, 27);
		Set set = BookKey.keySet();
		Iterator e = set.iterator();
        while(e.hasNext()){
        	SearchKey.addItem(e.next());
        }
		SimpleSearchPanel.add(SearchKey);
		
		SearchContent = new JTextField();
		SearchContent.setBounds(309, 15, 429, 27);
		SearchContent.setColumns(10);
		SimpleSearchPanel.add(SearchContent);
		
		SearchBtn = new JButton("\u641C\u7D22");
		SearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = BookKey.get(SearchKey.getSelectedItem());
				String content = SearchContent.getText().trim();
				try{
					updateResultTable(bookBll.getBooksBy(value, content));
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		SearchBtn.setBounds(776, 14, 69, 29);
		SimpleSearchPanel.add(SearchBtn);
	}

	private void updateResultTable(Book[] books){
		if(books == null){
			JOptionPane.showMessageDialog(null, "没有符合要求的记录！");
			return;
		}
		CustomizedTableModel<Book> tableModel = (CustomizedTableModel<Book>) searchResultTable.getModel();
		tableModel.setRecords(books);
		tableModel.fireTableDataChanged();
	
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
