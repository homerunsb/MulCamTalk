package com.mc.mctalk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import com.mc.mctalk.vo.FriendsVO;

public class CreatingChattingRoomPanel extends JFrame {
	

	private JPanel topPanel = new JPanel();
	private JPanel middlePanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
 
	private JScrollPane middleChoiceFriendListScrollPanel = new JScrollPane();
	private JLabel middleChoiceFriendListLabel = new JLabel();
	private JTextField middleSerchFriendListTextField = new JTextField(10);
	private JPanel middleSerchSpace = new JPanel();
	private JPanel middleSelectedFriendListPanel = new JPanel();
	private FriendsListPanel friendListPannel = new FriendsListPanel(true);
	private JList<FriendsVO> selectedList;

	private JButton closeBtn = new JButton("X"); // 나중에 이미지로 주면 이쁠것같다.
	private JButton confirmBtn = new JButton("확인");
	private JButton cancelBtn = new JButton("취소");

	private DefaultListModel<FriendsVO> listmodel = new DefaultListModel<>();
	private JLabel topPanelLabel = new JLabel();
	private JLabel topCountLabel = new JLabel("" + count);
	private static int count = 0;
	private static boolean exitCheck = false;

	private Font grayFont = new Font("dialog", Font.BOLD, 12);

	public CreatingChattingRoomPanel() {
		// friendListPannel.pSearch.setPreferredSize(new Dimension(150, 30));

		// frame setting
		this.setBackground(Color.white);
		this.setSize(520, 430);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setUndecorated(true);
		this.add(topPanel, BorderLayout.NORTH);
		this.add(middlePanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		topPanel.setBackground(Color.white);
		topPanelLabel.setBackground(Color.white);
		topCountLabel.setBackground(Color.white);
		middlePanel.setBackground(Color.white);
		bottomPanel.setBackground(Color.white);
		middleSelectedFriendListPanel.setBackground(Color.white);

		// topPanel setting
		topPanel.setLayout(new BorderLayout());
		JPanel panelAdd = new JPanel();
		panelAdd.setBackground(Color.white);
		topPanel.add(panelAdd, BorderLayout.CENTER);
		panelAdd.add(topPanelLabel);
		panelAdd.add(topCountLabel);
		topPanel.add(closeBtn, BorderLayout.LINE_END);
		closeBtn.setBackground(Color.white);
		closeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		topPanelLabel.setFont(grayFont);
		topPanelLabel.setText("초대하기");
		topCountLabel.setText("" + count);

		// middlePanel setting
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
		friendListPannel.setPreferredSize(new Dimension(100, 100));
		friendListPannel.tfSearch.setPreferredSize(new Dimension(230, 15));
		friendListPannel.pSearch.setBackground(Color.white);
		friendListPannel.tfSearch.setBackground(Color.white);
		middlePanel.add(friendListPannel);
		// middlePanel.add(middleChoiceFriendListScrollPanel);
		middleSelectedFriendListPanel.setPreferredSize(new Dimension(250, 200));
		// 크기 설정하려면 서치패널에 있는 크기를 필수적으로 줄여주어야 한다....
		middlePanel.add(middleSelectedFriendListPanel);

		// middleSelectedFriendListPanel.add(selectedList);
		// listmodel.addElement(null);
		selectedList = new JList<>(listmodel);

		selectedList.setCellRenderer(new FriendsListCellRenderer());

		middleSelectedFriendListPanel.add(selectedList);

		friendListPannel.jlFriendsList.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				listmodel.removeAllElements();
				Iterator<Entry<String, FriendsVO>> entry = friendListPannel.selectedFriends.entrySet().iterator();
				for (int i = 0; i < friendListPannel.selectedFriends.size(); i++) {

					listmodel.addElement(entry.next().getValue());
				}

				System.out.println(listmodel.size());

				count = friendListPannel.selectedFriends.size();
				topCountLabel.setText(count + "");
				repaint();

			}
		});
		//

		// bottomPanel setting
		bottomPanel.add(confirmBtn);
		bottomPanel.add(cancelBtn);

		// 버튼 액션 리스너!!!
		confirmBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread chatCreat = new Thread(new Runnable() {
					
					@Override
					public void run() {
						ChattingFrame cf = new ChattingFrame();
						
					}
				});
				chatCreat.start();
				try {
					chatCreat.join();
					ChattingFrame ff = new ChattingFrame();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(Thread.currentThread().getState()+"");

			}
		});

		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		this.setVisible(true);

	}

	class FriendsListCellRenderer extends DefaultListCellRenderer {
		public FriendsListCellRenderer() {
			this.setOpaque(true);
			this.setIconTextGap(0); // 아이콘과 텍스트의 간격 설정
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {

			JLabel comp = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			Border border = comp.getBorder();
			Border margin = new EmptyBorder(0, 0, 0, 0);
			comp.setBorder(new CompoundBorder(border, margin));
			// 받아온 JList의 값을 FriendsVO 객체에 담기
			FriendsVO vo = (FriendsVO) value;

			// 리턴할 객체에 이미지, 이름과, 상태 메세지 세팅
			// comp.setIcon(vo.getProfileImage());
			comp.setText(vo.getUserName());
			return comp;
		}
	}

	// 종료스레드 작성

	public static void main(String[] args) {
		CreatingChattingRoomPanel c = new CreatingChattingRoomPanel();
		
	}

}
