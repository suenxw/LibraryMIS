package com.xw.lmis.gui;

import javax.swing.JPanel;

import java.awt.Font;

import javax.swing.JLabel;



public class BlankPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	public BlankPanel() {
		setLayout(null);
		setBounds(100, 100, 895, 432);
		
		JLabel lblNewLabel = new JLabel("\u957F\u5927\u957F\u65B0");
		lblNewLabel.setFont(new Font("свт╡", Font.ITALIC, 43));
		lblNewLabel.setBounds(314, 130, 376, 106);
		add(lblNewLabel);
	}
}
