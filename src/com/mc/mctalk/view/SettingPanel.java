package com.mc.mctalk.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.mc.mctalk.chatserver.ChattingClient;
import com.mc.mctalk.vo.FileVO;

/*
 * 담당자 : 정대용
 * 수정 필요 사항 : 객체 크기 조절, 기능 구현
 * 추가작업 : (김영태) 2016.10.16 세팅패널 클라이언트 객체로 받을 수 있게 생성자 작업. 파일전송 액션리스너 작성. 
 */

public class SettingPanel extends JPanel {
private JPanel mainPanel = new JPanel();
	private JPanel accountPanel = new JPanel();
	private JLabel accountLabel = new JLabel("내 계정");
	private JButton logoutBtn = new JButton("로그아웃");
	
	private JPanel profilePanel = new JPanel();
	private JLabel profileLabel = new JLabel("프로필 설정");
	private JPanel profileInPanel = new JPanel();
	private JButton nameChangeBtn = new JButton("대화명 변경");
	private JButton messageChangeBtn = new JButton("상태메세지 변경");
	
	private JPanel alramPanel = new JPanel();
	private JLabel alramLabel = new JLabel("알림설정");
	private JPanel alramInPanel = new JPanel();
	private JButton alramOnBtn = new JButton("켜기");
	private JButton alramOffBtn = new JButton("끄기");
	
	
	private ChattingClient client ;
	
	private EtchedBorder eBorder = new EtchedBorder(EtchedBorder.RAISED);
	
	public SettingPanel(){
		//계정 패널(서브패널)
				accountPanel.add(accountLabel, BorderLayout.CENTER);
				accountPanel.add(logoutBtn);
				logoutBtn.setPreferredSize(new Dimension(100, 50));
				accountPanel.setBorder(eBorder);
				mainPanel.add(accountPanel);
				 
				//프로필 패널(서브패널)
				profilePanel.add(profileLabel);
				profileInPanel.add(nameChangeBtn);
				profileInPanel.add(messageChangeBtn);
				profilePanel.add(profileInPanel);
				profilePanel.setBorder(eBorder);
				mainPanel.add(profilePanel);
				
				//알림패널(서브패널)
				alramPanel.add(alramLabel);
				alramInPanel.add(alramOnBtn);
				alramInPanel.add(alramOffBtn);
				alramPanel.add(alramInPanel);
				alramPanel.setBorder(eBorder);
				mainPanel.add(alramPanel);
				
				add(mainPanel);
				
				//패널크기 설정
				mainPanel.setPreferredSize(new Dimension(380, 550));
				accountPanel.setPreferredSize(new Dimension(360, 100));
				profilePanel.setPreferredSize(new Dimension(360, 100));
				alramPanel.setPreferredSize(new Dimension(360, 100));
				
				//패널 레이아웃 세팅
				accountPanel.setLayout(new GridLayout(1, 2, 5, 5));
				profilePanel.setLayout(new GridLayout(1, 2, 5, 5));
				profileInPanel.setLayout(new GridLayout(2, 1, 5, 5));
				alramPanel.setLayout(new GridLayout(1, 2, 5, 5));
				alramInPanel.setLayout(new GridLayout(2, 1, 5, 5));
//				mainPanel.setLayout(new GridLayout(3, 1));
				
				setSize(380,550);
				setVisible(true);
	}
	
	
	public SettingPanel(ChattingClient client)
	{		
		this();
		messageChangeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				if(e.getSource()==messageChangeBtn){
					int returnVal = fileChooser.showOpenDialog(mainPanel);
					if(returnVal== fileChooser.APPROVE_OPTION){
						String imagesDirPath= System.getProperty("user.dir");
						
						FileVO selectImgFile =new FileVO(fileChooser.getSelectedFile().getPath(), client.getLoginUserVO().getUserID()); 
						client.sendImg(selectImgFile);
						
						System.out.println(fileChooser.getSelectedFile().getPath());
						System.out.println(selectImgFile.exists());
						System.out.println(imagesDirPath+"/images");
						
					}else{
						
					}
				}
			}
		});
		
	}
}
