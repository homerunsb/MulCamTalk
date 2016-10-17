package com.mc.mctalk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.text.ParseException;
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
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.google.gson.Gson;
import com.mc.mctalk.chatserver.ChattingClient;
import com.mc.mctalk.view.uiitem.CustomJScrollPane;
import com.mc.mctalk.view.uiitem.NotificationAlarmPlayer;
import com.mc.mctalk.vo.ChattingRoomVO;
import com.mc.mctalk.vo.MessageVO;
import com.mc.mctalk.vo.UserVO;

public class ChattingFrame extends JFrame {
	private final String TAG = "ChattingFrame : ";
	private Gson gson = new Gson();
	private ChattingClient client;
	private String loginID, roomID;
	
	private JPanel pCover = new JPanel();
	private JPanel pChatHistory = new JPanel(); // 채팅 저장해놓은 패널
	private JPanel pChatInput = new JPanel();// 채팅쓰는 패널
	private JButton btnSubmit = new JButton("전송");// 전송버튼
	private JTextPane tpChatHistory = new JTextPane();// 채팅 저장해놓은 판
	private JTextArea taChatInPut = new JTextArea(null, 3, 24);// 채팅 쓰는 판
	private JScrollPane scrollChatInput = new JScrollPane(taChatInPut);//  taInPutChatt 을 붙인 스크롤패널
	private CustomJScrollPane scrollChatHistory;//   historyChatt 에 붙인 스크롤 패널
	private StyledDocument doc = tpChatHistory.getStyledDocument();
	private SimpleAttributeSet sasRight = new SimpleAttributeSet();
	private SimpleAttributeSet sasLeft = new SimpleAttributeSet();
	private SimpleAttributeSet sasTime = new SimpleAttributeSet();
	private SimpleAttributeSet sasSendUser = new SimpleAttributeSet();
	
	public ChattingFrame(ChattingClient client, ChattingRoomVO roomVO) {
		System.out.println(TAG + "ChattingFrame()");
		this.client = client;
		this.loginID = client.getLoginUserVO().getUserID();
		this.roomID = roomVO.getChattingRoomID();
		this.setSize(380, 550);
		this.setTitle(roomVO.getChattingRoomName());
		this.setLocationByPlatform(true);

		pCover.setLayout(new BoxLayout(pCover, BoxLayout.Y_AXIS));
		this.setBackground(new Color(155, 186, 216));
		pChatHistory.setBackground(new Color(155, 186, 216));
		pChatInput.setBackground(new Color(155, 186, 216));
		pChatHistory.setPreferredSize(new Dimension(380, 470));
		pChatInput.setPreferredSize(new Dimension(380, 80));
		
		scrollChatHistory = new CustomJScrollPane(tpChatHistory, scrollChatHistory.VERTICAL_SCROLLBAR_AS_NEEDED,
				scrollChatHistory.HORIZONTAL_SCROLLBAR_NEVER);
		scrollChatHistory.setPreferredSize(new Dimension(372, 455));
		
		tpChatHistory.setEditable(false); // 채팅 저장해놓을 패널에 채팅 불가
		tpChatHistory.setBackground(new Color(155, 186, 216)); //historyChatt 배경색 지정

		pChatInput.setLayout(new FlowLayout(FlowLayout.CENTER));
		taChatInPut.setLineWrap(true); //taInPutChatt 스크롤 생성 true
		taChatInPut.setBorder(BorderFactory.createEmptyBorder(2, 7, 2, 7));
		taChatInPut.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
		btnSubmit.setPreferredSize(new Dimension(61, 61));
		btnSubmit.setBackground(new Color(255,236,66));
		btnSubmit.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
		btnSubmit.setMargin(new Insets(10,5,10,5));
		btnSubmit.setBorderPainted(false);
		btnSubmit.setFocusPainted(false);
		btnSubmit.setContentAreaFilled(true);
		scrollChatInput.setBorder(null);
		
		Border emptyBorder = BorderFactory.createEmptyBorder(0,0,0,0);
		pChatHistory.setBorder(emptyBorder);
		pChatInput.setBorder(emptyBorder);
		tpChatHistory.setBorder(emptyBorder);
		scrollChatHistory.setBorder(emptyBorder);
		StyleConstants.setAlignment(sasRight, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setAlignment(sasLeft, StyleConstants.ALIGN_LEFT);
		StyleConstants.setAlignment(sasSendUser, StyleConstants.ALIGN_LEFT);
		
		//채팅 히스토리 스크롤 컨트롤 관련
		DefaultCaret caret = (DefaultCaret) tpChatHistory.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scrollChatHistory.getVerticalScrollBar().setValue(scrollChatHistory.getVerticalScrollBar().getMaximum());
				
	    StyleConstants.setForeground(sasRight, Color.BLACK);
	    StyleConstants.setFontSize(sasRight, 15);
	    StyleConstants.setBackground(sasRight, Color.YELLOW);
	    StyleConstants.setSpaceAbove(sasRight, 5);
	    StyleConstants.setSpaceBelow(sasRight, 0);
//	    StyleConstants.setLeftIndent(sasRight, 10);
	    StyleConstants.setRightIndent(sasRight, 10);
	    StyleConstants.setFontFamily(sasRight, "Malgun Gothic");
	    StyleConstants.setBold(sasRight, false);

	    StyleConstants.setForeground(sasLeft, Color.BLACK);
	    StyleConstants.setFontSize(sasLeft, 15);
	    StyleConstants.setBackground(sasLeft, Color.WHITE);
	    StyleConstants.setSpaceAbove(sasLeft, 0);
	    StyleConstants.setSpaceBelow(sasLeft, 0);
	    StyleConstants.setLeftIndent(sasLeft, 20);
//	    StyleConstants.setRightIndent(sasLeft, 10);
	    StyleConstants.setFontFamily(sasLeft, "Malgun Gothic");
	    StyleConstants.setBold(sasLeft, false);
	    
	    StyleConstants.setFontSize(sasTime, 10);
	    StyleConstants.setFontFamily(sasTime, "Malgun Gothic");
	    
	    StyleConstants.setForeground(sasSendUser, Color.BLACK);
	    StyleConstants.setFontSize(sasSendUser, 13);
	    StyleConstants.setSpaceAbove(sasSendUser, 10);
	    StyleConstants.setSpaceBelow(sasSendUser, 2);
	    StyleConstants.setLeftIndent(sasSendUser, 15);
	    StyleConstants.setFontFamily(sasSendUser, "Malgun Gothic");
	    StyleConstants.setBold(sasSendUser, true);
		
		client.setChattingGUI(this, roomVO);
		
		pChatHistory.add(scrollChatHistory); // 채팅패널에 스크롤팬 붙임
		pChatInput.add(scrollChatInput);
		pChatInput.add(btnSubmit);
		pCover.add(pChatHistory);
		pCover.add(pChatInput);
		this.add(pCover);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		btnSubmit.addActionListener(new TotalActionListener());
		taChatInPut.addKeyListener(new TotalActionListener());
		this.addWindowListener(new CloseWindowListener());

	}

	class TotalActionListener implements ActionListener, KeyListener {
		int conTrollInPut = 0;  // 컨트롤 키값을 저장해놓을 변수
		int enterInPut = 0;		// 엔터 키값을 저장해 놓을 변수	

		@Override
		public void actionPerformed(ActionEvent e) { //전송 버튼을 누르면 채팅 입력
			String str = taChatInPut.getText();
			if (str.length() != 0) { 
				client.sendMsg(roomID, str);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			String str = taChatInPut.getText();

			if (KeyEvent.VK_ENTER == enterInPut) {
				if (KeyEvent.VK_ENTER == enterInPut && KeyEvent.VK_CONTROL == conTrollInPut) {  // 엔터와 컨트롤 동시에 누를시
					taChatInPut.setText(str+"\n"); //컨트롤 +엔터 누를시 개행 추가
					enterInPut = 0; //엔터 키값 0으로 초기화
				} else {                  
					if (str.length() > 1) { //엔터만 누를시 채팅입력창의 길이가 1보다 클시 textAreaSetText 매소드 호출
						client.sendMsg(roomID, str.trim());
						conTrollInPut = 0;
						enterInPut = 0;
					} else {							//엔터만 눌럿을시 채팅입력창의 길이가 1보다 작을 경우 그냥 채팅입력창만 초기화
						taChatInPut.setText("");
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
		String msgSendTime = messageVO.getSendTime();
		
		// 문자가 길면 개행을 하기 위해 인덱스값으로 인서트가 가능한 스트링 버퍼에 담기
		StringBuffer msgBuffer = new StringBuffer(msg);
		if (msgBuffer.length() > 20) { // 채팅 입력의 길이가 20 보다 클 경우 개행 추가
			for (int i = 1; i <= msgBuffer.length() / 20; i++) {
				msgBuffer.insert(20 * i, "\n");
			}
		}
		
		//다시 스트링에 넣기
		String insertedMsg = msgBuffer.toString();
		//개행 개수 파악을 위한 리스트
		ArrayList lineBreakList = new ArrayList();
		for (int i = 0; i < insertedMsg.length(); i++) {
			if (insertedMsg.charAt(i) == '\n') {
				lineBreakList.add(i);
			}
		}

		// 들어온 ID 잘라 로그인한 ID와 비교
		String sendUserID = messageVO.getSendUserID();
		String sendUserName = messageVO.getSendUserName();

		boolean isLoginID = false;
		if (sendUserID.equals(loginID)) {
			isLoginID = true;
		}

		//메시지 시간
		Date times = null;
		SimpleDateFormat formatter = null;
		try {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			times = formatter.parse(msgSendTime);
			formatter = new SimpleDateFormat("aahh:mm", Locale.KOREA);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String formattedTime = formatter.format(times);
		
		// 로그인 ID 여부에 따라 서식 적용
		SimpleAttributeSet leftOrRight = new SimpleAttributeSet();
		int minusLength = 0;
		int lineBreakCount = lineBreakList.size();
		
		// 개행 갯수에 따라 시간 붙일 위치(인덱스) 정하기 (오른쪽일 경우)
		if (isLoginID) { // 나 자신(오른쪽)
			leftOrRight = sasRight;
			formattedTime = formattedTime + "  ";
			//insertedMsg = insertedMsg.split(":")[1]; // 내가 친건 ID 표시 안함
			// 나 자신일 경우 개행이 있을시 마지막 줄 왼쪽에 시간 붙임
			if (lineBreakCount > 1) {
				minusLength = (int) lineBreakList.get(lineBreakCount - 1)
								  - (int) lineBreakList.get(lineBreakCount - 2);
			} else { // 개행 없을시 입력한 만큼만 빼기
				minusLength = insertedMsg.length();
			}
		} else {// 상대방(왼쪽)
			leftOrRight = sasLeft;
			formattedTime = "  " + formattedTime;
			minusLength = 1; // 0일시 개행이 되버림.
		}
		
		try {
			if(isLoginID != true){//상대방 메시지일 경우
				doc.insertString(doc.getLength(), sendUserName+"\n", sasSendUser); // 상대방 아이디 넣기
				doc.setParagraphAttributes((doc.getLength() - sendUserName.length()), doc.getLength(), sasSendUser, false); // 서식 지정하기
				
				//메시지 알람 재생
				Thread alarmThread = new Thread(new Runnable() {
					@Override
					public void run() {
						NotificationAlarmPlayer ap = new NotificationAlarmPlayer();
				        try {
				        	ap.playAudioFile();
						} catch (Exception e) {
							e.printStackTrace();
						}	
					}
				});
				alarmThread.start();
			}
			doc.insertString(doc.getLength(), insertedMsg, leftOrRight); // 메시지 넣기
			doc.insertString(doc.getLength() - minusLength, formattedTime, sasTime); // 시간 넣기
			doc.setParagraphAttributes((doc.getLength() - insertedMsg.length()), doc.getLength(), leftOrRight, false); // 서식 지정하기
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		taChatInPut.setText(""); // 채팅입력창 초기화
		
		//추가할 사항
		//공지(입장 퇴장 할때 메시지) 가운데로 폰트 따로 지정해서 출력 필요
	}
	
	public static void main(String[] args) {
		ChattingFrame a = new ChattingFrame(new ChattingClient(new UserVO()), null);
	}
}
