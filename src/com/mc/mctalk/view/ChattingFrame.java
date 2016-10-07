package com.mc.mctalk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class ChattingFrame extends JFrame {

	JPanel northpanel = new JPanel(); // 채팅 저장해놓은 패널
	JPanel southpanel = new JPanel();// 채팅쓰는 패널
	JButton messeageGoButton = new JButton("전송");// 전송버튼
	JTextPane historyChatt = new JTextPane();// 채팅 저장해놓은 판
	JTextArea taInPutChatt = new JTextArea(null, 3, 26);// 채팅 쓰는 판
	JScrollPane scrollpane = new JScrollPane(taInPutChatt);//  taInPutChatt 을 붙인 스크롤패널
	JScrollPane scrollpane1;                      //   historyChatt 에 붙인 스크롤 패널
	StyledDocument doc = historyChatt.getStyledDocument();
	SimpleAttributeSet right = new SimpleAttributeSet();
	SimpleAttributeSet time = new SimpleAttributeSet();
	Date date = new Date();
    SimpleDateFormat simple = new SimpleDateFormat("aahh:mm");
    
    


	Border line = BorderFactory.createLineBorder(Color.BLACK);


	boolean isEnter = false; // 컨트롤 엔터 값을 구별해줄 변수
	

	public ChattingFrame() {
		setSize(380, 550);
		setLayout(new BorderLayout());
		add(northpanel, BorderLayout.NORTH);
		add(southpanel, BorderLayout.SOUTH);
		scrollpane1 = new JScrollPane(historyChatt, scrollpane1.VERTICAL_SCROLLBAR_AS_NEEDED,
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
		
		messeageGoButton.addActionListener(new TotalActionListener());

		taInPutChatt.addKeyListener(new TotalActionListener());
		taInPutChatt.setBorder(line);
		historyChatt.setBorder(line);

		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		
	    StyleConstants.setForeground(right, Color.BLACK);
	    StyleConstants.setFontSize(right, 18);
	    StyleConstants.setBackground(right, Color.YELLOW);
	    StyleConstants.setSpaceAbove(right, 10);
	    StyleConstants.setLeftIndent(right, 10);
	    StyleConstants.setRightIndent(right, 10);
	    StyleConstants.setFontSize(time, 9);
	    StyleConstants.setFontFamily(time, "Malgun Gothic");
	    
	    
	    scrollpane1.getVerticalScrollBar().setValue(scrollpane1.getVerticalScrollBar().getMaximum());
	    
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

	}

	public static void main(String[] args) {
		ChattingFrame a = new ChattingFrame();
	}


	class TotalActionListener implements ActionListener, KeyListener {
		int conTrollInPut = 0;  // 컨트롤 키값을 저장해놓을 변수
		int enterInPut = 0;		// 엔터 키값을 저장해 놓을 변수	

		@Override
		public void actionPerformed(ActionEvent e) { //전송 버튼을 누르면 채팅 입력
			String str = taInPutChatt.getText()+"\n";
			textAreaSetText(str);
			
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
						textAreaSetText(str);					   // 한뒤  엔터와 컨트롤 키값 초기화	
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

		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_CONTROL) {   //컨트롤을 눌렀을시conTrollInPut 변수에 키값 저장
				conTrollInPut = e.getKeyCode();
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {		//엔터를 눌렀을시 enterInPut 변수에 키값 저장
				enterInPut = e.getKeyCode();
				if(taInPutChatt.getText().equals("\n")){
					taInPutChatt.setText("");
				}
			}
		}
	}
	
	void textAreaSetText(String str) {

		StringBuffer buffer = new StringBuffer(str);

		if (buffer.length() > 15) { // 채팅 입력의 길이가 33 보다 클 경우 33번째에 개행 추가
			for (int i = 1; i <= buffer.length() / 15; i++) {
				buffer.insert(15 * i, "\n");
			}
		}
		try {

			String dTime = (simple.format(date));

			ArrayList temp = new ArrayList();
			str = buffer.toString();

			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == '\n') {
					temp.add(i);
				}
			}

			// 무조건 실행
			doc.insertString(doc.getLength(), buffer.toString(), right); // 버퍼에서 따와서 길이만큼 넣기

			int index = 0;
			if (temp.size() > 1) {
	        	 
	            index = (int) temp.get(temp.size()-1) - (int) temp.get((temp.size()-2));
	            doc.insertString(doc.getLength()-index, dTime, time); //버퍼에서 따와서 길이만큼 넣기
	         }else{
	            doc.insertString(doc.getLength()-buffer.length(), dTime, time);
	         }
			   

			doc.setParagraphAttributes(0, doc.getLength(), right, false);
			taInPutChatt.setText("");  //채팅입력창 초기화
		} catch (BadLocationException e) { //주석
			e.printStackTrace();
		}
	}
}
