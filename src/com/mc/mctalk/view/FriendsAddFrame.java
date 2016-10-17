package com.mc.mctalk.view;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.border.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import com.mc.mctalk.chatserver.ChattingClient;
import com.mc.mctalk.dao.UserDAO;
import com.mc.mctalk.view.uiitem.CustomJScrollPane;
import com.mc.mctalk.view.uiitem.RoundedImageMaker;
import com.mc.mctalk.vo.UserVO;

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
	private JLabel addLabel = new JLabel("검색할 이름을 입력하시오.");
	private JTextField nameField = new JTextField();
	private JButton searchBtn = new JButton("검색");

	private JPanel secondPanel = new JPanel(); //가운데 패널
	private JList searchList = new JList(); //검색된 유저 리스트
	private DefaultListModel listModel; //리스트 모델
	private JScrollPane listScroll = new JScrollPane(); //리스트 스크롤
	
	private JPanel thirdPanel = new JPanel(); //하단 패널
	private JButton addBtn = new JButton("친구추가");
	
	private Map<String, UserVO> mapFriends;
	private RoundedImageMaker imageMaker = new RoundedImageMaker();
	
	public FriendsAddFrame(){
		initPanel();
		
		//상단 패널
		firstPanel.add(addLabel);
		firstPanel.add(nameField);
		firstPanel.add(searchBtn);
		nameField.setPreferredSize(new Dimension(270, 30));
		searchBtn.addActionListener(new MemberSearchListener());
		searchBtn.setPreferredSize(new Dimension(270, 30));
		firstPanel.setPreferredSize(new Dimension(300, 100));
		add(firstPanel, BorderLayout.NORTH);
		
		//가운데 패널에 넣을 검색 리스트, 스크롤 세팅
		listScroll.setViewportView(searchList);
		listScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		searchList.setCellRenderer(new FriendsListCellRenderer());
		searchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		searchList.setListData(searchUser);
		
		//가운데 패널
		secondPanel.add(listScroll);
//		secondPanel.setPreferredSize(new Dimension(300, 200));
		listScroll.setViewportView(searchList);
		listScroll.setPreferredSize(new Dimension(270, 150));
		searchList.setPreferredSize(new Dimension(250, 150));
		add(secondPanel, BorderLayout.CENTER);
		
		//하단 패널
		thirdPanel.add(addBtn);
		addBtn.setPreferredSize(new Dimension(270, 30));
		addBtn.addActionListener(new MemberAddListener());
		add(thirdPanel, BorderLayout.SOUTH);
		
		this.setTitle("친구 추가");
		this.setSize(300, 360);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void initPanel(){
		this.setLayout(new BorderLayout());
		
		// JList에 데이터 담기
		searchList = new JList(new DefaultListModel());
		listModel = (DefaultListModel) searchList.getModel();
		
		listScroll = new CustomJScrollPane(searchList);
		listScroll.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, new Color(230, 230, 230)));
	}
	
	//JList를 기존에 가져온 LinkedHashMap(순서보장) 데이터로 초기화
	public void addElementToJList(){
		Set<Map.Entry<String, UserVO>> entrySet = mapFriends.entrySet();
		Iterator<Map.Entry<String, UserVO>> entryIterator = entrySet.iterator();
		while (entryIterator.hasNext()) {
			Map.Entry<String, UserVO> entry = entryIterator.next();
			String key = entry.getKey();
			UserVO vo = entry.getValue();
			listModel.addElement(vo);
		}
	}
	
	//Map에 있는 개체 중 검색 값을 가진 JList엘리먼트를 찾아 보여주기 
	public void searchFriendsMap(){
		String inputSearchText = nameField.getText().trim();
		if(inputSearchText.length()==0){
			listModel.removeAllElements();
			addElementToJList();
		}else{
			listModel.removeAllElements();
			for (Map.Entry<String, UserVO> entry : mapFriends.entrySet()) {
				UserVO vo = entry.getValue();
				if (vo.getUserID().contains(inputSearchText)) {
					listModel.addElement(vo);
				} else {
					listModel.removeElement(vo);
				}
			}
		}
		System.out.println(listModel);
	}
	
	public void setNameField(JTextField nameField)
	{
		this.nameField = nameField;
	}
	public JTextField getNameField()
	{
		return nameField;
	}
	
	public void setSearchList(JList searchList)
	{
		this.searchList = searchList;
	}
	public JList getSearchList()
	{
		return searchList;
	}
	
	public void setMapFriends(Map<String, UserVO> mapFriends)
	{
		this.mapFriends = mapFriends;
	}
	public Map<String, UserVO> getMapFriends()
	{
		return mapFriends;
	}
	
	//버튼 이벤트
	class MemberSearchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			//로그인하자마자 로그인 ID 변수 소멸
			//MemberAddListener도 마찬가지
			UserDAO udo = new UserDAO();
			UserVO vo = new UserVO();
			ChattingClient client = new ChattingClient(vo);
			mapFriends = new LinkedHashMap<String, UserVO>();
			mapFriends = udo.SearchMember(client.getLoginUserVO().getUserID(), nameField.getText().toString());
			searchFriendsMap();
			addElementToJList();
			System.out.println(listModel);
			String s = vo.getUserID();
			System.out.println();
			System.out.println(client.getLoginUserVO().getUserID());
		}
		
	}
	
	class MemberAddListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			UserDAO udo = new UserDAO();
			UserVO vo = new UserVO();
			ChattingClient client = new ChattingClient(vo);
			String c = (listModel.getElementAt(searchList.getSelectedIndex())).toString();
			int i = c.indexOf("=");
			int j = c.indexOf(",");
			String userId = c.substring(i+1, j);
			System.out.println();
			
			
//			udo.AddFriend(userId, client.getLoginUserVO().toString());
		}
	}
	
	//JList 모양 변경
		class FriendsListCellRenderer extends JPanel implements ListCellRenderer<UserVO> {
			private JLabel lbImgIcon = new JLabel();
			private JLabel lbName = new JLabel();
			private JLabel lbStatMsg = new JLabel();
			private JPanel panelText;
			
			public FriendsListCellRenderer() {
				Border border = this.getBorder();
				Border margin = new EmptyBorder(5, 15, 5, 10);
				this.setLayout(new BorderLayout(10, 10)); //간격 조정이 되버림(확인필요)
				this.setBorder(new CompoundBorder(border, margin));
				
				lbName.setFont(new Font("Malgun Gothic", Font.BOLD, 13));
				lbStatMsg.setFont(new Font("Malgun Gothic", Font.PLAIN, 10));
				lbStatMsg.setBorder(new EmptyBorder(0, 10, 0, 10));
				
				panelText = new JPanel(new GridLayout(0, 1));
				panelText.setBorder(new EmptyBorder(15, 10, 15, 0));
				panelText.add(lbName);
				panelText.add(lbStatMsg);

				add(lbImgIcon, BorderLayout.WEST);
				add(panelText, BorderLayout.CENTER);
			}
			
			@Override
			public Component getListCellRendererComponent(JList<? extends UserVO> list, UserVO value, int index,
					boolean isSelected, boolean cellHasFocus) {
				//받아온 JList의 값을 UserVO 객체에 담기
				UserVO vo = (UserVO) value;
				
				//리턴할 객체에 둥근 프로필 이미지, 이름과, 상태 메세지 세팅
				ImageIcon profileImage = imageMaker.getRoundedImage(vo.getUserImgPath(), 70, 70);
				lbImgIcon.setIcon(profileImage);
				lbName.setText(vo.getUserName());
				if(vo.getUserMsg() != null ){
					lbStatMsg.setText(vo.getUserMsg());
				}
				
				//투명도 설정
				lbImgIcon.setOpaque(true);
			    lbName.setOpaque(true);
			    lbStatMsg.setOpaque(true);
				panelText.setOpaque(true);
				
			    // 선택됐을때 색상 변경
			    if (isSelected) {
			    	lbImgIcon.setBackground(list.getSelectionBackground());
			        lbName.setBackground(list.getSelectionBackground());
			        lbStatMsg.setBackground(list.getSelectionBackground());
			        panelText.setBackground(list.getSelectionBackground());
			        setBackground(list.getSelectionBackground());
			    } else { 
			    	lbImgIcon.setBackground(list.getBackground());
			    	lbName.setBackground(list.getBackground());
			    	lbStatMsg.setBackground(list.getBackground());
			        panelText.setBackground(list.getBackground());
			        setBackground(list.getBackground());
			    }
			    
				return this;
			}
		}
}
