package com.mc.mctalk.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.border.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*
 * 담당자 : 정대용
 * 구현 기능 : 1) 친구 추가 Frame
 * 				2) 친구 검색 기능(id, 이름) - 이미 추가된 친구가 검색되어선 안됨.
 * 				3) 친구 추가 기능
 * 				*) 가능하다면, 프레임이 나타나는 위치가 메인 프레임 중간에 나타나게끔 됐음 좋겠어요.
 * 				*) 또, 뒷 배경이 불투명한 약간 어두운색이 되고 컨트롤이 안되게끔.( 카톡 PC버젼의 친구 추가 기능 참고..)
 * 참고 사항 : 1) 검색창 UI 패널은 기 구현된게 있으니 활용하기 바람.(SearchPanel.java)
 * 				2) 친구 추가 후 해당 frame을 닫을 경우 친구 리스트를 refresh 해야 함.(추후 협의)
 * 				3) 카톡 창 참고해서 깔끔하고 이쁘게 만들어 주세요~~^^ㅋㅋㅋ
 */

public class FriendsAddFrame extends JFrame {	
	private JFrame f = new JFrame();
	
	private JPanel firstPanel = new JPanel(); //윗 패널
	private JLabel addLabel = new JLabel("검색할 아이디를 입력하시오.");
	private JTextField nameField = new JTextField();
	private JButton searchBtn = new JButton("검색");

	private JPanel secondPanel = new JPanel(); //가운데 패널
	private JList searchList = new JList(); //검색된 유저 리스트
	private JScrollPane listScroll = new JScrollPane(); //리스트 스크롤
//	private String[] searchUser = {"1","2","3"}; //검색된 유저(차후에 수정해야함)
	
	private JPanel thirdPanel = new JPanel(); //하단 패널
	private JButton addBtn = new JButton("친구추가");

	public FriendsAddFrame(){
		
		//상단 패널
		firstPanel.add(addLabel);
		firstPanel.add(nameField);
		nameField.setPreferredSize(new Dimension(270, 30));
		firstPanel.setPreferredSize(new Dimension(300, 100));
		firstPanel.add(searchBtn);
		searchBtn.setPreferredSize(new Dimension(250, 30));
		add(firstPanel, BorderLayout.NORTH);
		
		//가운데 패널에 넣을 검색 리스트, 스크롤 세팅
		listScroll.setViewportView(searchList);
		listScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		searchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		searchList.setListData(searchUser);
		
		//가운데 패널
		secondPanel.add(listScroll);
		listScroll.setViewportView(searchList);
		searchList.setPreferredSize(new Dimension(250, 180));
		add(secondPanel, BorderLayout.CENTER);
		
		//하단 패널
		thirdPanel.add(addBtn);
		addBtn.setPreferredSize(new Dimension(250, 30));
		addBtn.addActionListener(new MemberSearchListener());
		add(thirdPanel, BorderLayout.SOUTH);
		
		this.setTitle("친구 추가");
		this.setSize(300, 360);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	
	//버튼 이벤트
	class MemberSearchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == searchBtn)
			{
				
			}
			else if(e.getSource() == addBtn)
			{
				
			}
		}
		
	}
}
