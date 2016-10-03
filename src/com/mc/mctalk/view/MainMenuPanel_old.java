package com.mc.mctalk.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenuPanel_old extends JPanel {
	private MainFrame mainFrame;
	private JPanel pMenuBtn, pAddBtn;
	private JButton[] menuButtons = new JButton[3];
	private int selectedIndex = 0;
	private String[] menuBtnPaths = { "images/b_icon_friendsList.png", 
										"images/b_icon_chattingList.png",
										"images/b_icon_setting.png" };
	private String[] addBtnPaths = { "images/icon_add_friend.png",
										"images/icon_add_chatting.png" };
	
	//* 생성자 호출시 MainFrame을 인자로 넘겨 받음
	public MainMenuPanel_old(MainFrame f) {
		this.mainFrame = f;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(Color.white);
		pMenuBtn = new JPanel();
		pMenuBtn.setBackground(Color.white);
		pMenuBtn.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(pMenuBtn);
		
		replaceMenuButtons();

	}

	//* 패널에 올라간 버튼 제거 및 재생성
	public void replaceMenuButtons() {
		this.removeAll();
		for (int i = 0; i < menuBtnPaths.length; i++) {
			makeMenuImageButtons(i);
		}
		this.validate();
		this.repaint();

	}
	
	//* 버튼 생성
	public void makeMenuImageButtons(int index) {
		// 선택된 메뉴 구분하여 진한 이미지의 path 설정
		String path = menuBtnPaths[index];
		if (selectedIndex == index) {
			path = path.replace("b_", "");
		}

		// 이미지 리사이즈
		ImageIcon icon = new ImageIcon(path);
		Image image = icon.getImage();
		Image newImage = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImage);
		menuButtons[index] = new JButton(icon);

		// 이미지로만 버튼 나타내기
		menuButtons[index].setBorderPainted(false);
		menuButtons[index].setFocusPainted(false);
		menuButtons[index].setContentAreaFilled(false);

		// 텍스트 지정 없이 actionCommand로 구분값 설정
		menuButtons[index].setActionCommand(String.valueOf(index));
		menuButtons[index].addActionListener(new MenuActionListener());
		menuButtons[index].addMouseMotionListener(new MenuMouseListener());
		this.add(menuButtons[index]);
	}
	
	
	//* 선택된 메뉴 구분 및 메인 프레임의 changePanel() 호출하여 선택된 메뉴에 따라 패널 교체
	class MenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			selectedIndex = Integer.parseInt(e.getActionCommand());
			replaceMenuButtons();
			
			//이미지 파일의 구분자, 확장자 제거 후 파일명 넘김.
			String panelName = menuBtnPaths[selectedIndex].substring(14, (menuBtnPaths[selectedIndex].length() - 4));
			mainFrame.changePanel(panelName);
		}
	}
	
	//* 메뉴 마우스 커서 변경
	class MenuMouseListener implements MouseMotionListener {
		@Override
		public void mouseMoved(MouseEvent e) {
			for (JButton b : menuButtons) {
				b.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {
		}
	}
}
