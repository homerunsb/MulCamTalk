package com.mc.mctalk.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.RepaintManager;

import com.mc.mctalk.dao.UserDAO;

public class MainFrame extends JFrame {
	final private String TAG = "MainFrame : ";
	private static String loginID = "test";	//로그인 ID
	static private JPanel pCover = new JPanel();
	private MainMenuPanel pMainMenu = new MainMenuPanel(this);
	static private FriendsListPanel pFriendsList = new FriendsListPanel();
	static private JPanel pChattingList = new JPanel();
	static private SettingPanel pSetting = new SettingPanel();
	final private String FRAME_TITLE = "MulCamTalk";
	final private int FRAME_WIDTH = 380, FRAME_HEIGHT = 600;
	final private int MENU_WIDTH = 380, MENU_HEIGHT = 50;
	final private int CONTENT_WIDTH = 380, CONTENT_HEIGHT = 550;

	public MainFrame() {
		System.out.println(TAG + "MainFrame()");
		//* panel setting
		pCover.setLayout(new BoxLayout(pCover, BoxLayout.Y_AXIS));
		pCover.setBackground(Color.black);
		
		pMainMenu.setPreferredSize(new Dimension(MENU_WIDTH, MENU_HEIGHT));
		pFriendsList.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
		pChattingList.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
		pSetting.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
		
		pFriendsList.setBackground(Color.yellow);
		pChattingList.setBackground(Color.GREEN);
		pSetting.setBackground(Color.DARK_GRAY);

		//* add
		pCover.add(pMainMenu);
		pCover.add(pFriendsList);
		this.add(pCover);

		//* frame setting
		ImageIcon img = new ImageIcon("images/logo.png");
		this.setIconImage(img.getImage());
		
		this.setTitle(FRAME_TITLE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	public void changePanel(String panelName){
		JPanel selectedPanel = null;
		switch (panelName) {
			case "friendsList": selectedPanel = pFriendsList; break;
			case "chattingList": selectedPanel = pChattingList; break;
			case "setting": selectedPanel = pSetting; break;
		}
		pCover.remove(1);
		pCover.add(selectedPanel, BorderLayout.CENTER);
		pCover.validate();
		pCover.repaint();
	}

	public static String getLoginID() {
		return loginID;
	}
	
}
