package com.mc.mctalk.view.uiitem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mc.mctalk.chatserver.ChattingClient;

public class CustomTitlebar extends JPanel{
	final private String TAG = "CustomTitlebar : ";
	final private int PANEL_WIDTH = 380, PANEL_HEIGHT = 36;
	private JButton btnMinimize, btnClose;
	private JFrame frame;
	private ChattingClient client;
	
	public CustomTitlebar(JFrame frame, ChattingClient client){
		this.frame = frame;
		this.client = client;

		this.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setBackground(new Color(82, 134, 198));
		this.setLayout(new BorderLayout());
		
		JPanel pTitle = new JPanel();
		JPanel pButton = new JPanel();

		pTitle.setBackground(new Color(82, 134, 198));
		pTitle.setBorder(new EmptyBorder(5, 15, 5, 5));
		pTitle.setLayout(new FlowLayout(FlowLayout.LEFT));
		pButton.setBackground(new Color(82, 134, 198));
		pButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		//커스텀 타이틀바 - 타이틀 설정
		JLabel lblTitle = new JLabel("<html><font color='white'>mulcam<b>talk<b></font></html>");
		lblTitle.setFont(new Font("Malgun Gothic", Font.PLAIN, 16));
		pTitle.add(lblTitle);
		
		//커스텀 타이틀바 - 버튼 설정
		ImageIcon imgClose = new ImageIcon("images/btn_close_active.png");
		ImageIcon imgMinimize = new ImageIcon("images/btn_minimize_active.png");
		btnMinimize = new JButton(imgMinimize);
		btnClose = new JButton(imgClose);
		btnMinimize.setPreferredSize(new Dimension(30,30));
		btnClose.setPreferredSize(new Dimension(30,30));
		btnMinimize.addActionListener(new TitleButtonActionListener());
		btnClose.addActionListener(new TitleButtonActionListener());
		
		btnMinimize.setBorderPainted(false);
		btnMinimize.setFocusPainted(false);
		btnMinimize.setContentAreaFilled(false);
		btnClose.setBorderPainted(false);
		btnClose.setFocusPainted(false);
		btnClose.setContentAreaFilled(false);
		btnMinimize.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		pButton.add(btnMinimize);
		pButton.add(btnClose);
		
		this.add(pTitle, BorderLayout.LINE_START);
		this.add(pButton, BorderLayout.LINE_END);
		
		
		//커스텀 타이틀바 - 이동 마우스 리스너
		FrameDragListener frameDragListener = new FrameDragListener(frame);
		this.addMouseListener(frameDragListener);
		this.addMouseMotionListener(frameDragListener);
	}
	
	//버튼 리스너
	class TitleButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			if(button == btnMinimize){
				frame.setExtendedState(JFrame.ICONIFIED);
			}else if(button == btnClose){
				if(client != null) client.stopClient();
				System.exit(0);
//				int isExit = JOptionPane.showConfirmDialog(getContentPane(), "종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null);
//    			if(isExit==0){
//    				System.exit(0);
//    			}
			}
		}
	}
}

//마우스 리스너
class FrameDragListener extends MouseAdapter {
	private final JFrame frame;
	private Point mouseDownCompCoords = null;

	public FrameDragListener(JFrame frame) {
		this.frame = frame;
	}
	public void mouseReleased(MouseEvent e) {
		mouseDownCompCoords = null;
	}
	public void mousePressed(MouseEvent e) {
		mouseDownCompCoords = e.getPoint();
	}
	public void mouseDragged(MouseEvent e) {
		Point currCoords = e.getLocationOnScreen();
		frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
	}
}
