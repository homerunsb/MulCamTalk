package com.mc.mctalk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
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
import com.mc.mctalk.view.uiitem.LogoManager;
import com.mc.mctalk.vo.UserVO;

public class MainFrame extends JFrame {
	final private String TAG = "MainFrame : ";
	private String loginID;	//로그인 ID
	private JPanel pCover, pTitlebar;
	private MainMenuPanel pMainMenu;
	private FriendsListPanel pFriendsList;
	private JPanel pChattingList;
	private SettingPanel pSetting;
	private ChattingClient client;
	final private String FRAME_TITLE = "MulCamTalk";
	final private int FRAME_WIDTH = 380, FRAME_HEIGHT = 600;
	final private int MENU_WIDTH = 380, MENU_HEIGHT = 50;
	final private int CONTENT_WIDTH = 380, CONTENT_HEIGHT = 550;
	private JButton btnMinimize, btnClose;
	
	public MainFrame(ChattingClient client) {
		System.out.println(TAG + "MainFrame()");
		this.client = client;
		LogoManager.setLogoFrame(this);
		
		loginID = client.getLoginUserVO().getUserID();
		pCover = new JPanel();
		pMainMenu = new MainMenuPanel(this, client);
		pFriendsList = new FriendsListPanel(client);
		pChattingList = new JPanel();
		pSetting = new SettingPanel();
		
		//* panel setting
		pCover.setLayout(new BoxLayout(pCover, BoxLayout.Y_AXIS));
		pCover.setBackground(new Color(82, 134, 198));
		pCover.setBorder(BorderFactory.createLineBorder(new Color(82, 134, 198)));

		pMainMenu.setPreferredSize(new Dimension(MENU_WIDTH, MENU_HEIGHT));
		pFriendsList.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
		pChattingList.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
		pSetting.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
		
		//기본 타이틀 바 삭제 및 커스텀 타이틀바 적용
		this.setUndecorated(true);
		pTitlebar = new JPanel();
		JPanel pTitle = new JPanel();
		JPanel pButton = new JPanel();
		pTitlebar.setBackground(new Color(82, 134, 198));
		pTitlebar.setLayout(new BorderLayout());
		pTitle.setBackground(new Color(82, 134, 198));
		pTitle.setBorder(new EmptyBorder(5, 15, 5, 5));
		pTitle.setLayout(new FlowLayout(FlowLayout.LEFT));
		pButton.setBackground(new Color(82, 134, 198));
		pButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		//커스텀 타이틀바 - 타이틀 설정
		JLabel lblTitle = new JLabel("<html><font color='white'>mulcam<b>talk<b></font></html>");
		lblTitle.setFont(new Font("Malgun Gothic", Font.PLAIN, 16));
		pTitle.add(lblTitle);
		
		//커스텀 타이틀바 - 버튼 설정
		ImageIcon imgClose = new ImageIcon("images/btn_close_active.png");
		ImageIcon imgMinimize = new ImageIcon("images/btn_minimize_active.png");
		btnMinimize = new JButton(imgMinimize);
		btnClose = new JButton(imgClose);
		btnMinimize.setPreferredSize(new Dimension(30,30));
		btnClose.setPreferredSize(new Dimension(30,30));
		btnMinimize.addActionListener(new TitleButtonActionListener());
		btnClose.addActionListener(new TitleButtonActionListener());
		
		btnMinimize.setBorderPainted(false);
		btnMinimize.setFocusPainted(false);
		btnMinimize.setContentAreaFilled(false);
		btnClose.setBorderPainted(false);
		btnClose.setFocusPainted(false);
		btnClose.setContentAreaFilled(false);
		btnMinimize.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		pButton.add(btnMinimize);
		pButton.add(btnClose);
		
		pTitlebar.add(pTitle, BorderLayout.LINE_START);
		pTitlebar.add(pButton, BorderLayout.LINE_END);
		
		//커스텀 타이틀바 - 이동 마우스 리스너
		FrameDragListener frameDragListener = new FrameDragListener(this);
		this.addMouseListener(frameDragListener);
		this.addMouseMotionListener(frameDragListener);
		
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
	
	class TitleButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			if(button == btnMinimize){
				setExtendedState(JFrame.ICONIFIED);
			}else if(button == btnClose){
				System.exit(0);
//				int isExit = JOptionPane.showConfirmDialog(getContentPane(), "종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null);
//    			if(isExit==0){
//    				System.exit(0);
//    			}
			}
		}
	}
	
	public void changePanel(String panelName){
		JPanel selectedPanel = null;
		switch (panelName) {
			case "friendsList": selectedPanel = pFriendsList = new FriendsListPanel(client); break;
			case "chattingList": selectedPanel = pChattingList; break;
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

class FrameDragListener extends MouseAdapter {

	private final JFrame frame;
	private Point mouseDownCompCoords = null;

	public FrameDragListener(JFrame frame) {
		this.frame = frame;
	}

	public void mouseReleased(MouseEvent e) {
		mouseDownCompCoords = null;
	}

	public void mousePressed(MouseEvent e) {
		mouseDownCompCoords = e.getPoint();
	}

	public void mouseDragged(MouseEvent e) {
		Point currCoords = e.getLocationOnScreen();
		frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
	}
}


