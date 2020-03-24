package com.xw.lmis.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.xw.lmis.bll.ReaderAdmin;
import com.xw.lmis.model.Reader;
import com.xw.lmis.gui.Login;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.Color;

public class PwdChangePanel extends JPanel {
	private JPanel AuthorityPanel;
	private JLabel lblNewLabel;
	private JLabel label;
	private JButton Enter;
	private JButton Close;
	private JPasswordField RdPwd;
	private JPasswordField RdPwdagain;
	
	private ReaderAdmin readerBll = new ReaderAdmin();
	/**
	 * Create the panel.
	 */
	public PwdChangePanel() {
		setBounds(100, 100, 895, 432);
		setLayout(null);
		initPwdChangePanel();
	}
	
	private void initPwdChangePanel() {

		AuthorityPanel = new JPanel();
		AuthorityPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u5BC6\u7801\u4FEE\u6539", TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.desktop));
		AuthorityPanel.setBounds(227, 86, 442, 255);
		add(AuthorityPanel);
		AuthorityPanel.setLayout(null);
		
		lblNewLabel = new JLabel("\u65B0\u5BC6\u7801\uFF1A");
		lblNewLabel.setBounds(100, 72, 60, 15);
		AuthorityPanel.add(lblNewLabel);
		
		label = new JLabel("\u786E\u5B9A\u5BC6\u7801\uFF1A");
		label.setBounds(100, 115, 87, 37);
		AuthorityPanel.add(label);
		
		Enter = new JButton("\u786E\u5B9A");
		Enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				if (RdPwd.getPassword().length == 0||RdPwdagain.getPassword().length == 0) {
		             JOptionPane.showMessageDialog(null,"�������û���������");
		             return;
		         }//�ж������ı�����������Ƿ�Ϊ��ֵ
				
				String Pwd =new String(RdPwd.getPassword()).trim();
				if (Pwd.equals(new String(RdPwdagain.getPassword()).trim())) {
					Login.reader.setRdPwd(Pwd);
					readerBll.updateReader(Login.reader);
				}else {
					JOptionPane.showMessageDialog(null, "�����������벻һ�£�");
					clearText();
					return;
				}
				JOptionPane.showMessageDialog(null, "�����޸ĳɹ���");
				new Login().setVisible(true);
				Login.mainGUI.dispose();
			}
		});
		Enter.setBounds(100, 194, 93, 23);
		AuthorityPanel.add(Enter);
		
		RdPwd = new JPasswordField();
		RdPwd.setBounds(199, 69, 139, 21);
		AuthorityPanel.add(RdPwd);
		RdPwd.setColumns(10);
		
		RdPwdagain = new JPasswordField();
		RdPwdagain.setBounds(199, 123, 139, 21);
		AuthorityPanel.add(RdPwdagain);
		RdPwdagain.setColumns(10);
		
		Close = new JButton("\u53D6\u6D88");
		Close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		Close.setBounds(296, 194, 93, 23);
		AuthorityPanel.add(Close);
	}
	
	private void clearText() {//����ı���, ����������
        RdPwd.setText("");
        RdPwdagain.setText("");
    }
}
