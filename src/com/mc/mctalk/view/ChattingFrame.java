package com.mc.mctalk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.google.gson.Gson;
import com.mc.mctalk.chatserver.ChattingClient;
import com.mc.mctalk.view.uiitem.CustomJScrollPane;
import com.mc.mctalk.vo.ChattingRoomVO;
import com.mc.mctalk.vo.MessageVO;

public class ChattingFrame extends JFrame {
	String TAG = "ChattingFrame : ";
	Gson gson = new Gson();
	ChattingClient client;
	String loginID, roomID;
	
	JPanel coverPanel = new JPanel();
	JPanel northpanel = new JPanel(); // 채팅 저장해놓은 패널
	JPanel southpanel = new JPanel();// 채팅쓰는 패널
	JButton messeageGoButton = new JButton("전송");// 전송버튼
	JTextPane historyChatt = new JTextPane();// 채팅 저장해놓은 판
	JTextArea taInPutChatt = new JTextArea(null, 3, 26);// 채팅 쓰는 판
	JScrollPane scrollpane = new JScrollPane(taInPutChatt);//  taInPutChatt 을 붙인 스크롤패널
	CustomJScrollPane scrollpane1;//   historyChatt 에 붙인 스크롤 패널
	StyledDocument doc = historyChatt.getStyledDocument();
	SimpleAttributeSet right = new SimpleAttributeSet();
	SimpleAttributeSet left = new SimpleAttributeSet();
	SimpleAttributeSet time = new SimpleAttributeSet();
	
	Border line = BorderFactory.createEmptyBorder();
	
	boolean isEnter = false; // 컨트롤 엔터 값을 구별해줄 변수

	private BufferedReader br;
	private BufferedWriter bw;
	
	public ChattingFrame(ChattingClient client, ChattingRoomVO roomVO) {
		System.out.println(TAG + "ChattingFrame()");
		this.client = client;
		this.loginID = client.getLoginUserVO().getUserID();
		this.roomID = roomVO.getChattingRoomID();
		System.out.println(client+"\n"+loginID+"\n" + roomID+"\n");
		
		setSize(380, 550);
		setTitle(roomVO.getChattingRoomName());
		coverPanel.setLayout(new BoxLayout(coverPanel, BoxLayout.Y_AXIS));

		setBackground(new Color(155, 186, 216)); //historyChatt 배경색 지정
		northpanel.setBackground(new Color(155, 186, 216)); //historyChatt 배경색 지정
		southpanel.setBackground(new Color(155, 186, 216)); //historyChatt 배경색 지정
		coverPanel.add(northpanel);
		coverPanel.add(southpanel);
		add(coverPanel);
		scrollpane1 = new CustomJScrollPane(historyChatt, scrollpane1.VERTICAL_SCROLLBAR_AS_NEEDED,
				scrollpane1.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane1.setPreferredSize(new Dimension(360, 420));
		northpanel.add(scrollpane1); // 채팅패널에 스크롤팬 붙임
		historyChatt.setEditable(false); // 채팅 저장해놓을 패널에 채팅 불가

		taInPutChatt.setLineWrap(true); //taInPutChatt 스크롤 생성 true
		historyChatt.setBackground(new Color(155, 186, 216)); //historyChatt 배경색 지정

		southpanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		messeageGoButton.setPreferredSize(new Dimension(60, 60));
		southpanel.add(scrollpane);
		southpanel.add(messeageGoButton);
		

//		messeageGoButton.setBorderPainted(false);
//		messeageGoButton.setFocusPainted(false);
//		messeageGoButton.setContentAreaFilled(false);

		taInPutChatt.setBorder(line);
		historyChatt.setBorder(line);
		scrollpane1.setBorder(line);
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);

//		doc.setParagraphAttributes(0, doc.getLength(), right, false);

		//채팅 히스토리 스크롤 컨트롤 관련
		DefaultCaret caret = (DefaultCaret) historyChatt.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scrollpane1.getVerticalScrollBar().setValue(scrollpane1.getVerticalScrollBar().getMaximum());
//		scrollpane1.setViewportView(historyChatt);
				
	    StyleConstants.setForeground(right, Color.BLACK);
	    StyleConstants.setFontSize(right, 15);
	    StyleConstants.setBackground(right, Color.YELLOW);
	    StyleConstants.setSpaceAbove(right, 10);
	    StyleConstants.setLeftIndent(right, 10);
	    StyleConstants.setRightIndent(right, 10);
	    StyleConstants.setFontFamily(right, "Malgun Gothic");
	    
	    StyleConstants.setForeground(left, Color.BLACK);
	    StyleConstants.setFontSize(left, 15);
	    StyleConstants.setBackground(left, Color.WHITE);
	    StyleConstants.setSpaceAbove(left, 10);
	    StyleConstants.setLeftIndent(left, 10);
	    StyleConstants.setRightIndent(left, 10);
	    StyleConstants.setFontFamily(left, "Malgun Gothic");

	    StyleConstants.setFontSize(time, 10);
	    StyleConstants.setFontFamily(time, "Malgun Gothic");

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		
		client.setChattingGUI(this, roomVO);
		
		
		messeageGoButton.addActionListener(new TotalActionListener());
		taInPutChatt.addKeyListener(new TotalActionListener());
		this.addWindowListener(new CloseWindowListener());

	}

//	public static void main(String[] args) {
//		ChattingFrame a = new ChattingFrame();
//	}

	class TotalActionListener implements ActionListener, KeyListener {
		int conTrollInPut = 0;  // 컨트롤 키값을 저장해놓을 변수
		int enterInPut = 0;		// 엔터 키값을 저장해 놓을 변수	

		@Override
		public void actionPerformed(ActionEvent e) { //전송 버튼을 누르면 채팅 입력
			String str = taInPutChatt.getText();
			if (str.length() != 0) { 
//				textAreaSetText("tes:"+str+"\n");
				client.sendMsg(roomID, str);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			String str = taInPutChatt.getText();

			if (KeyEvent.VK_ENTER == enterInPut) {
				if (KeyEvent.VK_ENTER == enterInPut && KeyEvent.VK_CONTROL == conTrollInPut) {  // 엔터와 컨트롤 동시에 누를시
					isEnter = false;
					taInPutChatt.setText(str+"\n"); //컨트롤 +엔터 누를시 개행 추가
															
					enterInPut = 0; //엔터 키값 0으로 초기화
				} else {                  
					if (str.length() > 1) { //엔터만 누를시 채팅입력창의 길이가 1보다 클시 textAreaSetText 매소드 호출
//						textAreaSetText(str);		// 한뒤  엔터와 컨트롤 키값 초기화	
						client.sendMsg(roomID, str.trim());
						conTrollInPut = 0;
						enterInPut = 0;
						isEnter = true;
					} else {							//엔터만 눌럿을시 채팅입력창의 길이가 1보다 작을 경우 그냥 채팅입력창만 초기화
						taInPutChatt.setText("");
						conTrollInPut = 0;			// 컨트롤과 엔터 키값 초기화
						enterInPut = 0;
					}
				}
			} else {
				conTrollInPut = 0;			//엔터도 아닐경우 키값만 초기화
				enterInPut = 0;
			}
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_CONTROL) {   //컨트롤을 눌렀을시conTrollInPut 변수에 키값 저장
				conTrollInPut = e.getKeyCode();
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {		//엔터를 눌렀을시 enterInPut 변수에 키값 저장
				enterInPut = e.getKeyCode();
			}
		}
		public void keyTyped(KeyEvent e) {}
	}
	
	//채팅 프레임 닫기 이벤트
	public class CloseWindowListener extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e) {
			System.out.println(TAG + "CloseWindowListener.windowClosing()" );
			//채팅 프레임 닫을때 중복으로 같은 방을 열기위해 담아 두었던 맵에 해당 UI 삭제
			client.removeHtChattingGUI(roomID);
		}
	}
	
	public void textAreaSetText(String msg) {
		MessageVO messageVO = gson.fromJson(msg, MessageVO.class);
		msg = messageVO.getMessage().replace("/n", "\n") + "\n";
		
		// 문자가 길면 개행을 하기 위해 인덱스값으로 인서트가 가능한 스트링 버퍼에 담기
		StringBuffer msgBuffer = new StringBuffer(msg);
		if (msgBuffer.length() > 20) { // 채팅 입력의 길이가 33 보다 클 경우 33번째에 개행 추가
			for (int i = 1; i <= msgBuffer.length() / 20; i++) {
				msgBuffer.insert(20 * i, "\n");
			}
		}
		
		//다시 스트링에 넣기
		String insertedMsg = msgBuffer.toString();
		
		
		//개행 인서트할때 합치면 될듯.
		//개행 개수 파악을 위한 리스트
		ArrayList lineBreakList = new ArrayList();
		for (int i = 0; i < insertedMsg.length(); i++) {
			if (insertedMsg.charAt(i) == '\n') {
				lineBreakList.add(i);
			}
		}

		// 들어온 ID 잘라 로그인한 ID와 비교
		String sendUserID = messageVO.getSendUserID();
		boolean isLoginID = false;
		if (sendUserID.equals(loginID)) {
			isLoginID = true;
		}

		long currentTime = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("aahh:mm", Locale.KOREA);
		Date times = new Date(currentTime);
		String dTime = formatter.format(times);
		
		// 로그인 ID 여부에 따라 서식 적용
		SimpleAttributeSet leftOrRight = new SimpleAttributeSet();
		int minusLength = 0;
		int lineBreakCount = lineBreakList.size();
		
		// 개행 갯수에 따라 시간 붙일 위치(인덱스) 정하기 (오른쪽일 경우)
		if (isLoginID) { // 나 자신(오른쪽)
			leftOrRight = right;
			dTime = dTime + "  ";
//			insertedMsg = insertedMsg.split(":")[1]; // 내가 친건 ID 표시 안함
			// 나 자신일 경우 개행이 있을시 마지막 줄 왼쪽에 시간 붙임
			if (lineBreakCount > 1) {
				minusLength = (int) lineBreakList.get(lineBreakCount - 1)
						- (int) lineBreakList.get((lineBreakCount - 2));
			} else { // 개행 없을시 입력한 만큼만 빼기
				minusLength = insertedMsg.length();
			}
		} else {// 상대방(왼쪽)
			leftOrRight = left;
			dTime = "  " + dTime;
			minusLength = 1; // 0일시 개행이 되버림.
		}
		
		try {
			doc.insertString(doc.getLength(), insertedMsg, leftOrRight); // 메시지 넣기
			doc.insertString(doc.getLength() - minusLength, dTime, time); // 시간 넣기
			doc.setParagraphAttributes((doc.getLength() - insertedMsg.length()), doc.getLength(), leftOrRight, false); // 서식 지정하기
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		taInPutChatt.setText(""); // 채팅입력창 초기화
		
		//추가할 사항
		//공지(입장 퇴장 할때 메시지) 가운데로 폰트 따로 지정해서 출력 필요
		//대화 폰트 크기 조절 필요
		//버튼 변경 및 입력창 높이 버튼이랑 맞춰야함
		
	}
}
