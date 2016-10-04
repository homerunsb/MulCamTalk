package com.mc.mctalk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import com.mc.mctalk.chatserver.ChattingController;
import com.mc.mctalk.dao.UserDAO;
import com.mc.mctalk.view.uiitem.CustomJScrollPane;
import com.mc.mctalk.view.uiitem.SearchPanel;
import com.mc.mctalk.vo.FriendsVO;

public class FriendsListPanel extends JPanel {
	String loginID = MainFrame.getLoginID();
	ArrayList<FriendsVO> alFriendsList;
	Map<String, FriendsVO> mapFriends;
	JList jlFriendsList;
	CustomJScrollPane scrollPane;
	DefaultListModel listModel;
	SearchPanel pSearch;
	JTextField tfSearch;
	
	public FriendsListPanel() {
		this.setLayout(new BorderLayout());

		//친구 찾기 패널 생성 및 해당 서치 키워드 액션 리스너 연결
		pSearch = new SearchPanel();
		tfSearch = pSearch.getTfSearch();
		tfSearch.addKeyListener(new FriendSearchKeyListener());
		
		// JList에 데이터 담기
		jlFriendsList = new JList(new DefaultListModel());
		listModel = (DefaultListModel) jlFriendsList.getModel();
		
		//DB접속 후 친구 목록 가져와 Custom JList Model에 프로필 사진 path, 이름 엘리먼트 추가하기.
		UserDAO dao = new UserDAO();
		mapFriends = new LinkedHashMap<String, FriendsVO>();
		mapFriends = dao.getAllFriendsMap(loginID);
		showFriendsList();
		
		// JList 모양 변경
		jlFriendsList.setCellRenderer(new FriendsListCellRenderer());
		jlFriendsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlFriendsList.addMouseListener(new FriendSelectionListener());
		
		scrollPane = new CustomJScrollPane(jlFriendsList);
		scrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.lightGray));

		this.add(pSearch, "North");
		this.add(scrollPane, "Center");
	}
	
	//JList를 기존에 가져온 LinkedHashMap(순서보장) 데이터로 초기화
	public void showFriendsList(){
		Set<Map.Entry<String, FriendsVO>> entrySet = mapFriends.entrySet();
		Iterator<Map.Entry<String, FriendsVO>> entryIterator = entrySet.iterator();
		while (entryIterator.hasNext()) {
			Map.Entry<String, FriendsVO> entry = entryIterator.next();
			String key = entry.getKey();
			FriendsVO vo = entry.getValue();
			listModel.addElement(vo);
		}
	}
	
	//선택된 친구에 대한 더블클릭 리스너
	class FriendSelectionListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			//getClickCount가 2 이상이면 더블클릭으로 판단함 && 선택된 인덱스가 -1이면 제대로된 선택이 아님
			if(e.getClickCount() >= 2 && jlFriendsList.getSelectedIndex() != -1){
				//선택된 친구ID와 로그인 ID를 매개변수로 컨트롤러 호출
				FriendsVO vo = (FriendsVO)jlFriendsList.getSelectedValue();
				new ChattingController(loginID, vo.getUserID());
			}
		}
		public void mouseReleased(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent arg0) {}
	}
	
	//TextField 검색 키보드 리스너(입력될때마다 리스너 실행)
	class FriendSearchKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			searchFriendsMap();
		}
		@Override
		public void keyReleased(KeyEvent e) {
			searchFriendsMap();
		}
		@Override
		public void keyTyped(KeyEvent e) {	
			searchFriendsMap();
		}
	}
	
	//Map에 있는 개체 중 검색 값을 가진 JList엘리먼트를 찾아 보여주기 
	public void searchFriendsMap(){
		String inputSearchText = tfSearch.getText().trim();
		//System.out.println("입력된 값 : " + inputSearchText);
		//입력된 값이 없을 경우 전체 리스트 항목 삭제 후 다시 로딩
		if(inputSearchText.length()==0){
			listModel.removeAllElements();
			showFriendsList();
		}else{
		//있을 경우 전체 삭제 후 입력된 값을 받아 객채 안에 해당 값을 가진 엘리먼트를 추가. 없다면 삭제.
		//DB를 다시 붙는 개념이 아니고, 최초 1번만 붙어서 받아온 데이터를 담고 있는 map에 대한 컨트롤임.
		//메뉴 이동시 & 친구 추가시 친구 리스트에 대한 refresh 필요할 듯.
			listModel.removeAllElements();
			for (Map.Entry<String, FriendsVO> entry : mapFriends.entrySet()) {
				FriendsVO vo = entry.getValue();
				if (vo.getUserName().contains(inputSearchText)) {
					listModel.addElement(vo);
				} else {
					listModel.removeElement(vo);
				}
			}//for
		}//if
	}
	
	//JList 모양 변경
	class FriendsListCellRenderer extends DefaultListCellRenderer{
		public FriendsListCellRenderer(){
			this.setOpaque(true);
			this.setIconTextGap(20); 			//아이콘과 텍스트의 간격 설정
		}
		
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			
			JLabel comp = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			Border border = comp.getBorder();
			Border margin = new EmptyBorder(0,10,0,10);
			comp.setBorder(new CompoundBorder(border, margin));
			//받아온 JList의 값을 FriendsVO 객체에 담기
			FriendsVO vo = (FriendsVO) value;
			
			//리턴할 객체에 이미지, 이름과, 상태 메세지 세팅
			comp.setIcon(vo.getProfileImage());
			comp.setText(vo.getUserName() 
					+ (vo.getUserMsg() == null ? "" : "  /  " + vo.getUserMsg()) );
			return comp;
		}
	}
	
	//테스트 용(키조합 입력(Ctrl+enter) 리스너)
	//상균이 소스 문제 없을시 삭제 필요
	class TestKeyListener implements KeyListener {
		int inputKey = 0;
		int inputKey2 = 0;
		int count = 0;
		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("keyPressed : " + e.getKeyCode());
			if(e.getKeyCode() == KeyEvent.VK_CONTROL){
				inputKey = e.getKeyCode();
			}
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				inputKey2 = e.getKeyCode();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, e.CTRL_MASK);
			System.out.println(ks.getKeyCode());
			
			if(e.getKeyCode()==KeyEvent.VK_ENTER ){
				if(inputKey == KeyEvent.VK_CONTROL && inputKey2 == KeyEvent.VK_ENTER){
					System.out.println("콘트롤 + 엔터  : " + inputKey + ", " + inputKey2);
				}else{
					inputKey = 0;
					inputKey2 = 0;
				}
			}else{
				inputKey = 0;
				inputKey2 = 0;
			}
			System.out.println("keyReleased : " + e.getKeyCode());			
		}
		@Override
		public void keyTyped(KeyEvent e) {
//			System.out.println("keyTyped : " + e.getKeyCode());
		}
	}
}
