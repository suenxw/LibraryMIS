package com.xw.lmis.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import com.xw.lmis.bll.*;
import com.xw.lmis.model.Reader;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class Login extends JFrame {

	private JPanel contentPane;
	
	private int loginTimes = 0;//登录次数
	private ReaderAdmin readerBLL = new ReaderAdmin();
	public static Reader reader = null;//登录用户信息，可用于整个程序
	
	private JTextField tfUserName;
	private JPasswordField pwdField;
	private JLabel labelLoginInfo;
	private JButton btnLogin;
	private JButton btnClose;

	public static Main mainGUI;//用于后面的关闭窗口，然后新建



	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle("长江大学图书管理信息系统");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 352, 267);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setSize(new Dimension(350, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel labelUserName = new JLabel("用户编号");
		labelUserName.setName("labelUserName");
		labelUserName.setBounds(78, 60, 83, 21);
		contentPane.add(labelUserName);
		
		tfUserName = new JTextField();
		tfUserName.setName("tfUserName");
		tfUserName.setBounds(160, 60, 66, 21);
		contentPane.add(tfUserName);
		tfUserName.setColumns(10);
		
		JLabel labelPassword = new JLabel("用户密码");
		labelPassword.setName("labelPassword");
		labelPassword.setBounds(78, 91, 83, 23);
		contentPane.add(labelPassword);
		
		pwdField = new JPasswordField();//密码密文
		pwdField.setName("pwdField");
		pwdField.setBounds(160, 92, 66, 21);
		contentPane.add(pwdField);
		pwdField.setColumns(10);
		
		btnLogin = new JButton("登录");
		btnLogin.setName("btnLogin");
		btnLogin.setBounds(65, 137, 68, 23);
		contentPane.add(btnLogin);
		
		btnLogin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arge){
				/*++loginTimes;
				String errorMsg="";
				int rdId=-1;
				try{
					rdId=Integer.valueOf(tfUserName.getText().trim());
				}catch(NumberFormatException e){
					errorMsg="用户名无效";
					tfUserName.requestFocus();
				}
				if(rdId!=-1){
					reader=readerBLL.getReader(rdId);
					if(reader==null){
						errorMsg="用户名无效";
						tfUserName.requestFocus();
					}else if (reader.getRdPwd().equals(new String(pwdField.getPassword()).trim())){
						loadMainGUI();
						System.out.println("登陆成功");
					}else{
						errorMsg="密码错误";
						pwdField.requestFocus();
						JOptionPane.showMessageDialog(null, "密码错误");
					}
				}
				if(errorMsg.length()>0)
					labelLoginInfo.setText(errorMsg);*/
				
				if (tfUserName.getText().trim().equals("")||pwdField.getPassword().length == 0) {
		             JOptionPane.showMessageDialog(null,"请输入用户名和密码");
		             return;//返回 不提交
		         }//判断两个文本框中输入的是否为空值
				
				int rdId;
				rdId=Integer.valueOf(tfUserName.getText().trim());
				reader=readerBLL.getReader(rdId);
				if(reader == null) {
					JOptionPane.showMessageDialog(null, "该用户不存在");
					return;//返回 不提交
				}else if (reader.getRdPwd().equals(new String(pwdField.getPassword()).trim())){
					loadMainGUI();
					System.out.println("登陆成功");
				}else{
					JOptionPane.showMessageDialog(null, "密码错误");
					return;//返回 不提交
				}
			}
		});
		
		btnClose = new JButton("退出");
		btnClose.setName("btnClose");
		btnClose.setBounds(174, 137, 68, 23);
		contentPane.add(btnClose);
		
		btnClose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				dispose();//关闭当前窗体
			}
		});
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setName("labelLoginInfo");
		lblNewLabel.setBounds(65, 182, 54, 15);
		contentPane.add(lblNewLabel);
		
	
	}
	

	private void loadMainGUI() {
		this.dispose();
		
		mainGUI = new Main();
		mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//有用滴！
		mainGUI.setLocationRelativeTo(null);//设置窗口位置
		mainGUI.setVisible(true);//显示
	}

	public void start() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Login login = new Login();
		login.start();
	}
}
