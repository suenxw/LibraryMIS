package com.xw.lmis.gui.commons;
/**
 * ����CustomizedTableModel�࣬�̳�AbstractTableModel�࣬
 * ��ΪJTable������ģ�͡�CustomizedTableModelʹ�÷��ͣ�
 * ������ΪAbstractModel���ͣ���������ڱ���Ŀ�е�����ʵ����
 * ����Reader��ReaderType��Borrow��Book�ȣ�
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
	//ͨ������ʵ����Ķ�Ӧ�ֶΣ���ȡjTable��ÿ����Ԫ���ȡֵ��
	//���õ�ͨ���ֶ�ת��������ʹ�÷��䣨Reflection�����������ڴ˷���Ϊ����ʵ����ͨ��
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
