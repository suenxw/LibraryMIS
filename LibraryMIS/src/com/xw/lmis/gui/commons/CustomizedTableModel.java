package com.xw.lmis.gui.commons;
/**
 * 定义CustomizedTableModel类，继承AbstractTableModel类，
 * 作为JTable的数据模型。CustomizedTableModel使用泛型，
 * 且限制为AbstractModel类型，因此适用于本项目中的所有实体类
 * （如Reader、ReaderType、Borrow、Book等）
 */
import javax.swing.table.AbstractTableModel;

import com.xw.lmis.model.AbstractModel;

public class CustomizedTableModel<T extends AbstractModel> extends
		AbstractTableModel {

	T[] records = null;
	String[] columnNames = null;
	String[] methodNames = null;

	public CustomizedTableModel(String[] columnNames, String[] methodNames) {
		this.columnNames = columnNames;
		this.methodNames = methodNames;
	}

	public void setRecords(T[] records) {
		this.records = records;
	}

	public T getObjectAt(int row) {
		return records[row];
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public int getColumnCount() {
		return columnNames == null ? 0 : columnNames.length;
	}

	@Override
	public int getRowCount() {
		return records == null ? 0 : records.length;
	}

	@Override
	//通过调用实体类的对应字段，获取jTable中每个单元格的取值。
	//常用的通用字段转换方法是使用反射（Reflection）技术。由于此方法为所有实体类通用
	public Object getValueAt(int row, int col) {
		T record = records[row];
		try {
			return record.getFieldValue(record.getClass(), methodNames[col]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
