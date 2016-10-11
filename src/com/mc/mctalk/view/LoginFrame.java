package com.mc.mctalk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mc.mctalk.chatserver.ChattingClient;
import com.mc.mctalk.dao.UserDAO;
import com.mc.mctalk.view.MainMenuPanel.MenuButtonActionListener;
import com.mc.mctalk.vo.UserVO;

public class LoginFrame extends JFrame {

	private JLabel labelLogo = new JLabel();
	private JLabel labelID = new JLabel("ID");
	private JTextField textID = new JTextField(10);
	private JLabel labelPW = new JLabel("PW");
	private JPasswordField PW = new JPasswordField(10);
	private JButton loginbtn = new JButton("LOGIN");
	private JButton joinbtn = new JButton("가입");
	private JButton findPWBtn = new JButton("PW찾기");
	private JButton findIDBtn = new JButton("ID찾기");

	private JPanel logoPanel = new JPanel();

	public LoginFrame() {

		setLayout(null);

		ImageIcon m = new ImageIcon("images/logo.png");
		labelLogo.setIcon(m);
		logoPanel.add(labelLogo);
		logoPanel.setBounds(0, 0, 380, 300);
		add(logoPanel);

		labelID.setBounds(50, 301, 20, 30);
		labelPW.setBounds(50, 350, 20, 30);
		add(labelID);
		add(labelPW);

		textID.setBounds(100, 301, 100, 30);
		add(textID);
		PW.setBounds(100, 350, 100, 30);
		add(PW);

		add(loginbtn);
		loginbtn.setBounds(240, 300, 80, 80);
		loginbtn.addActionListener(new ButtonActionListener() );
		
		
		joinbtn.setBounds(50, 420, 75, 30);
		add(joinbtn);
		findIDBtn.setBounds(145, 420, 75, 30);
		add(findIDBtn);
		findPWBtn.setBounds(240, 420, 80, 30);
		add(findPWBtn);

		setSize(380, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

	}

	public class ButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == loginbtn) {		// 로그인 버튼이 눌리면,
				UserDAO dao = new UserDAO();
				// DB 로그인
				String id = textID.getText();
				String pw = PW.getText();
				ChattingClient client = dao.loginMember(id, pw);
				UserVO vo = client.getLoginUserVO();	
				
				if (vo != null) {	
					MainFrame mainFrame = new MainFrame(client);
				}

			} 
		}
	}
	
	public static void main(String[] args) {
		LoginFrame loginFrame = new LoginFrame();
	}
}