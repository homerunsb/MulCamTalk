package com.mc.mctalk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
import javax.swing.text.StyledDocument;

public class ChattingFrame extends JFrame {

	JPanel northpanel = new JPanel();
	JPanel southpanel = new JPanel();
	JButton messeageGoButton = new JButton("전송");
	JTextPane historyChatt = new JTextPane();
	JTextArea inputTextArea = new JTextArea(null, 3, 25);
	JScrollPane scrollpane = new JScrollPane(inputTextArea);
	JScrollPane scrollpane1;

	
	StyledDocument doc = historyChatt.getStyledDocument();
	SimpleAttributeSet rightAlign = new SimpleAttributeSet();
	
	Border line = BorderFactory.createLineBorder(Color.BLACK);

	String a, b, c;

	public ChattingFrame() {
		setSize(380, 550);
		setLayout(new BorderLayout());
		add(northpanel, BorderLayout.NORTH);
		add(southpanel, BorderLayout.SOUTH);
		scrollpane1 = new JScrollPane(historyChatt, scrollpane1.VERTICAL_SCROLLBAR_AS_NEEDED,
				scrollpane1.HORIZONTAL_SCROLLBAR_NEVER);

		scrollpane1.setPreferredSize(new Dimension(360, 420));
		northpanel.add(scrollpane1);
		historyChatt.setEditable(false);

		inputTextArea.setLineWrap(true);
		
		historyChatt.setBackground(new Color(155, 186, 216));

		southpanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		southpanel.add(scrollpane, BorderLayout.LINE_START);
		southpanel.add(messeageGoButton, BorderLayout.LINE_END);
		messeageGoButton.addActionListener(new InputActionListener());
		messeageGoButton.setPreferredSize(new Dimension(60, 60));
		inputTextArea.addKeyListener(new InputActionListener());
		inputTextArea.setBorder(line);
		historyChatt.setBorder(line);
		

//		SimpleAttributeSet right = new SimpleAttributeSet();
//		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);


	    StyleConstants.setAlignment(rightAlign, StyleConstants.ALIGN_RIGHT);
	    StyleConstants.setForeground(rightAlign, Color.BLACK);
	    StyleConstants.setFontSize(rightAlign, 13);
	    StyleConstants.setBackground(rightAlign, Color.YELLOW);
	    
		
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

	}
	
	//액션 리스너 합치기(왜 가능한지 책에서 찾아 이해하기) & 클래스명 의미 있는 이름으로 바꾸기
	//같은 기능 메소드로 만들어 호출 하기
	//첫번째 공백라인 들어가는 것 없애기
	//버튼 크기 조절 필요
	//입력값이 0일 경우 막아야 함
	//마지막 줄 공백라인 들어가는 거 없애기
	
	
	//라인 색 및 정렬 지정 시 84~87 라인, 123~126 라인 봐야 함.
	
	//내가 친 텍스트 오른쪽 정렬, 남이 친 텍스트 왼쪽 정렬
	//전송 시간 텍스트 앞 or 뒤로 텍스트 크기 작게해서 붙이기
	
	class InputActionListener implements ActionListener, KeyListener {
		public void appendChattingHistory() throws BadLocationException{
			String textAreaStr =  inputTextArea.getText().trim();
			
			int textAreaLength  = textAreaStr.length();
			
			System.out.println("텍스트 입력 길이 : " +  textAreaLength);
			
			if(textAreaLength>1){
				StringBuffer buffer = new StringBuffer(textAreaStr);
				a = historyChatt.getText();
				for (int i = 1; i < buffer.length() / 14; i++) {
					System.out.println(buffer.length() / 14);
					buffer.insert(14*i, "\n");
				}
				
			    doc.insertString(doc.getLength(), (doc.getLength() == 0 ? "" : "\n" ) + buffer.toString(), rightAlign);
//			    System.out.println(a.length());
//			    System.out.println("doc.length : " + doc.getLength());
			    doc.setParagraphAttributes(0, doc.getLength(), rightAlign, false);
				
//				historyChatt.setText(a + "\n" + buffer.toString());
			}
			inputTextArea.setText("");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				appendChattingHistory();
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				try {
					appendChattingHistory();
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
		}
		@Override
		public void keyTyped(KeyEvent e) {
		}
	}
	
	
	//상대방인지 나인지 판별하는 파라미터 받을 필요 있음
	public void setStyleJTextPane(boolean isMyText){
		SimpleAttributeSet leftAlign = new SimpleAttributeSet();
	    SimpleAttributeSet rightAlign = new SimpleAttributeSet();
	    
	    StyleConstants.setAlignment(leftAlign, StyleConstants.ALIGN_LEFT);
	    StyleConstants.setForeground(leftAlign, Color.black);
	    StyleConstants.setFontSize(leftAlign, 13);
	    StyleConstants.setBackground(leftAlign, Color.WHITE);
	    
	    StyleConstants.setAlignment(rightAlign, StyleConstants.ALIGN_RIGHT);
	    StyleConstants.setForeground(rightAlign, Color.black);
	    StyleConstants.setFontSize(rightAlign, 13);
	    StyleConstants.setBackground(rightAlign, Color.YELLOW);
		if(isMyText){

		}
//	    doc
	    
	}
	
	
	
	public static void main(String[] args) {
		ChattingFrame a = new ChattingFrame();
	}
	
}
