package com.mc.mctalk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mc.mctalk.chatserver.ChattingClient;
import com.mc.mctalk.view.uiitem.CustomTitlebar;
import com.mc.mctalk.view.uiitem.LogoManager;
import com.mc.mctalk.vo.UserVO;

public class MainFrame extends JFrame {
	final private String TAG = "MainFrame : ";
	private String loginID;	//로그인 ID
	private JPanel pCover, pTitlebar;
	private MainMenuPanel pMainMenu;
	private FriendsListPanel pFriendsList;
	private ChattingRoomListPanel pChattingList;
	private SettingPanel pSetting;
	private ChattingClient client;
	final private String FRAME_TITLE = "MulCamTalk";
	final private int FRAME_WIDTH = 380, FRAME_HEIGHT = 600;
	final private int MENU_WIDTH = 380, MENU_HEIGHT = 50;
	final private int CONTENT_WIDTH = 380, CONTENT_HEIGHT = 550;
	private JButton btnMinimize, btnClose;
	
	public MainFrame(ChattingClient client) {
		System.out.println(TAG + "MainFrame()");
		//화면 중간에 띄우기
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width-300)/2, (screenSize.height - frameSize.height-600)/2);
		
		this.client = client;
		loginID = client.getLoginUserVO().getUserID();
		pCover = new JPanel();
		pMainMenu = new MainMenuPanel(this, client);
		pFriendsList = new FriendsListPanel(client);
		pChattingList = new ChattingRoomListPanel(client);
		pSetting = new SettingPanel();
		
		//* panel setting
		pCover.setLayout(new BoxLayout(pCover, BoxLayout.Y_AXIS));
		pCover.setBackground(new Color(82, 134, 198));
		pCover.setBorder(BorderFactory.createLineBorder(new Color(82, 134, 198)));

		pMainMenu.setPreferredSize(new Dimension(MENU_WIDTH, MENU_HEIGHT));
		pFriendsList.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
		pChattingList.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
		pSetting.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
		
		//로고 설정
		new LogoManager().setLogoFrame(this);
		//기본 타이틀바 제거 및 CustomTitlebar 추가
		this.setUndecorated(true);
		CustomTitlebar pTitlebar = new CustomTitlebar(this, client);

		//* add
		pCover.add(pTitlebar);
		pCover.add(pMainMenu);
		pCover.add(pFriendsList);
		this.add(pCover);
		
		this.setTitle(FRAME_TITLE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void changePanel(String panelName){
		JPanel selectedPanel = null;
		switch (panelName) {
			case "friendsList": selectedPanel = pFriendsList = new FriendsListPanel(client); break;
			case "chattingList": selectedPanel = pChattingList = new ChattingRoomListPanel(client); break;
			case "setting": selectedPanel = pSetting; break;
		}
		pCover.remove(2);
		pCover.add(selectedPanel, BorderLayout.CENTER);
		pCover.validate();
		pCover.repaint();
	}

	public String getLoginID() {
		return loginID;
	}
	
	public static void main(String[] args) {
		MainFrame f = new MainFrame(new ChattingClient(new UserVO()));
	}
}

