package com.mc.mctalk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class SettingPanel extends JPanel {
private JPanel mainPanel = new JPanel();
	
	private JPanel accountPanel = new JPanel();
	private JLabel accountLabel = new JLabel("내 계정");
	private JButton logoutBtn = new JButton("로그아웃");
	
	private JPanel profilePanel = new JPanel();
	private JLabel profileLabel = new JLabel("프로필 설정");
	private JPanel profileInPanel = new JPanel();
	private JButton nameChangeBtn = new JButton("대화명 변경");
	private JButton messageChangeBtn = new JButton("상태메세지 변경");
	
	private JPanel alramPanel = new JPanel();
	private JLabel alramLabel = new JLabel("알림설정");
	private JPanel alramInPanel = new JPanel();
	private JButton alramOnBtn = new JButton("켜기");
	private JButton alramOffBtn = new JButton("끄기");
	
	
	private EtchedBorder eBorder = new EtchedBorder(EtchedBorder.RAISED);
	
	public SettingPanel()
	{
//		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		accountPanel.add(accountLabel);
		accountPanel.add(logoutBtn);
		accountPanel.setBorder(eBorder);
		mainPanel.add(accountPanel);
		 
		profilePanel.add(profileLabel);
		profileInPanel.add(nameChangeBtn);
		profileInPanel.add(messageChangeBtn);
		profilePanel.add(profileInPanel);
		profilePanel.setBorder(eBorder);
		mainPanel.add(profilePanel);
		
		alramPanel.add(alramLabel);
		alramInPanel.add(alramOnBtn);
		alramInPanel.add(alramOffBtn);
		alramPanel.add(alramInPanel);
		alramPanel.setBorder(eBorder);
		mainPanel.add(alramPanel);
		
		add(mainPanel);
		accountPanel.setLayout(new GridLayout());
		profilePanel.setLayout(new GridLayout());
		profileInPanel.setLayout(new GridLayout(2, 1));
		alramPanel.setLayout(new GridLayout());
		alramInPanel.setLayout(new GridLayout(2, 1));
		mainPanel.setLayout(new GridLayout(3, 1));
		
		accountPanel.setBackground(Color.WHITE);
		profilePanel.setBackground(Color.WHITE);
		alramPanel.setBackground(Color.WHITE);
		
		setSize(380,550);
		setVisible(true);
	}
}
