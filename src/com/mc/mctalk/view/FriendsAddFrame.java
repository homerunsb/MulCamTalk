package com.mc.mctalk.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	private JPanel firstPanel = new JPanel(); //윗 패널
	private JLabel addLabel = new JLabel("검색할 아이디를 입력하시오.");
	private JTextField nameField = new JTextField(26);
	
	private JPanel secondPanel = new JPanel(); //아랫 패널
	private JButton searchBtn = new JButton("검색");
	
	public FriendsAddFrame(){
		
		firstPanel.add(addLabel);
		firstPanel.add(nameField);
		add(firstPanel);
		
		this.setTitle("친구 추가");
		this.setSize(300, 360);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
}
