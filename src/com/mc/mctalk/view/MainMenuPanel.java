package com.mc.mctalk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainMenuPanel extends JPanel {
	private MainFrame mainFrame;
	private JPanel pMenuBtn, pAddBtn;
	private JButton[] menuButtons = new JButton[3];
	private JButton[] addButtons = new JButton[2];
	private int selectedIndex = 0;
	private String[] menuTitles = {"friend","chat","setting"};
	private String[] addTitles = {"addFriend","addChat"};
	private String[] menuBtnPaths = { "images/b_icon_friendsList.png", 
										"images/b_icon_chattingList.png",
										"images/b_icon_setting.png" };
	private String[] addBtnPaths = { "images/icon_add_friend.png",
										"images/icon_add_chatting.png" };
	
	private 	Map<String, JButton> mapMenuButtons, mapAddButtons;
	private final int menuBtnWidthHeight = 30, addBtnWidthHeight = 20;
	
	
	final private String TAG = "MainMenuPanel_new : ";
	
	//* 생성자 호출시 MainFrame을 인자로 넘겨 받음
	public MainMenuPanel(MainFrame f) {
		this.mainFrame = f;
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		pMenuBtn = new JPanel();
		pMenuBtn.setBackground(Color.white);
		pMenuBtn.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		pAddBtn = new JPanel();
		pAddBtn.setBackground(Color.white);
		pAddBtn.setBorder(new EmptyBorder(11,0,0,0));
		pAddBtn.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		mapMenuButtons  = new LinkedHashMap<String, JButton>();
		mapAddButtons  = new LinkedHashMap<String, JButton>();
		
		this.add(pMenuBtn, BorderLayout.LINE_START);
		this.add(pAddBtn, BorderLayout.LINE_END);

		boolean isMenuButton;
		//메뉴 버튼 생성
		for (int i = 0; i < menuBtnPaths.length; i++) {
			isMenuButton = true;
			menuButtons[i] = new JButton(resizeImageIcon(menuBtnPaths[i], menuBtnWidthHeight, menuBtnWidthHeight));
			setButtonOptions(i, menuButtons, isMenuButton);
			pMenuBtn.add(menuButtons[i]);
			mapMenuButtons.put(menuTitles[i], menuButtons[i]);
		}
		//추가 버튼 생성
		for (int i = 0; i < addBtnPaths.length; i++) {
			isMenuButton = false;
			addButtons[i] = new JButton(resizeImageIcon(addBtnPaths[i], addBtnWidthHeight, addBtnWidthHeight));
			setButtonOptions(i, addButtons, isMenuButton);
			mapAddButtons.put(addTitles[i], addButtons[i]);
		}
		//1번 메뉴로 버튼 선택 지정을 위해 메소드 호출
		changeButton();
	}

	
	//* 버튼 옵션 설정
	public void setButtonOptions(int index, JButton[] buttons, boolean isMenuButton) {
		// 이미지로만 버튼 나타내기
		buttons[index].setBorderPainted(false);
		buttons[index].setFocusPainted(false);
		buttons[index].setContentAreaFilled(false);

		// 텍스트 지정 없이 actionCommand로 구분값 설정
		buttons[index].setActionCommand(String.valueOf(index));
		
		// 리스너 설정
		if(isMenuButton){
			buttons[index].addActionListener(new MenuButtonActionListener());
		}else{
			buttons[index].addActionListener(new AddButtonActionListener());
		}
		buttons[index].addMouseMotionListener(new MenuMouseListener());
	}
	
	//* 선택된 메뉴 버튼 교체
	public void changeButton(){
		//System.out.println("선택된 인덱스 : " + selectedIndex);
		//선택된 인덱스에 따라 메뉴 버튼 이미지 교체
		for (int i = 0; i < menuButtons.length; i++) {
			String path = menuBtnPaths[i];
			if(i == selectedIndex){
				path = path.replace("b_", "");
			}
//			System.out.println("선택된 인덱스의 path : " + path);
			JButton button = mapMenuButtons.get(menuTitles[i]);
			button.setIcon(resizeImageIcon(path, menuBtnWidthHeight, menuBtnWidthHeight));
			mapMenuButtons.put(menuTitles[i], button);
		}
		
		//선택된 메뉴에 따라 버튼을 담고 있는 맵 수정		
		for (int j = 0; j < addButtons.length; j++) {
			if(j == selectedIndex){
				mapAddButtons.put(addTitles[j], addButtons[j]);
			}else{
				mapAddButtons.remove(addTitles[j]);
			}
		}
		
		//친구나 방 메뉴가 선택 됐을때만, 추가 버튼 변경. 그 외 숨김
		if(selectedIndex == 0 || selectedIndex == 1){
			pAddBtn.removeAll();
			pAddBtn.add(mapAddButtons.get(addTitles[selectedIndex]));
			pAddBtn.validate();
			pAddBtn.repaint();
		}else{
			pAddBtn.removeAll();
		}
	}
	
	//* 버튼 이미지 리사이즈
	public ImageIcon resizeImageIcon(String path, int width, int height){
		ImageIcon icon = new ImageIcon(path);
		Image image = icon.getImage();
		Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImage);
		return icon;
	}
	
	//* 메뉴 선택 리스너, 선택된 메뉴 구분 및 메인 프레임의 changePanel() 호출하여 선택된 메뉴에 따라 패널 교체
	class MenuButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			selectedIndex = Integer.parseInt(e.getActionCommand());
			changeButton();
			
			//이미지 파일의 구분자, 확장자 제거 후 메인 프레임으로 파일명(패널명) 넘김.
			String panelName = menuBtnPaths[selectedIndex].substring(14, (menuBtnPaths[selectedIndex].length() - 4));
			mainFrame.changePanel(panelName);
		}
	}
	
	//* 친구, 방 추가 버튼 리스너
	class AddButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//0 : 친구 추가,  1 :  채팅방 추가 
			//추가 다이얼로그 or 프레임 연결 필요
			int selectedIndex = Integer.parseInt(e.getActionCommand());
			
			if(selectedIndex==0){
				FriendsAddFrame addFriend = new FriendsAddFrame();
			}else{
				//방 추가 프레임 연결 필요
			}
			System.out.println(selectedIndex);
			
		}
	}
	
	//* 버튼들 마우스커서 변경 리스너
	class MenuMouseListener implements MouseMotionListener {
		@Override
		public void mouseMoved(MouseEvent e) {
			for (JButton b : menuButtons) {
				b.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			for (JButton b : addButtons) {
				b.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {
		}
	}
}
