package com.mc.mctalk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.mc.mctalk.chatserver.ChattingClient;
import com.mc.mctalk.chatserver.ChattingController;
import com.mc.mctalk.view.uiitem.CustomJScrollPane;
import com.mc.mctalk.view.uiitem.RoundedImageMaker;
import com.mc.mctalk.vo.UserVO;

public class CreatingChattingRoomPanel extends JFrame {
	private RoundedImageMaker imageMaker = new RoundedImageMaker();
	private JPanel topPanel = new JPanel();
	private JPanel middlePanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private CustomJScrollPane ChoiceFriendListScrollPanel ;
	private JScrollPane middleChoiceFriendListScrollPanel = new JScrollPane();
	private JLabel middleChoiceFriendListLabel = new JLabel();
	private JTextField middleSerchFriendListTextField = new JTextField(10);
	private JPanel middleSerchSpace = new JPanel();
	private JPanel middleSelectedFriendListPanel = new JPanel();
	private FriendsListPanel friendListPannel;
	private JList<UserVO> selectedList;
	private JButton closeBtn = new JButton("X"); // 나중에 이미지로 주면 이쁠것같다.
	private JButton confirmBtn = new JButton("확인");
	private JButton cancelBtn = new JButton("취소");
	private DefaultListModel<UserVO> listmodel = new DefaultListModel<>();
	private JLabel topPanelLabel = new JLabel();
	private JLabel topCountLabel = new JLabel("" + count);
	private static int count = 0;
	private Color backGraundColor = new Color(255, 255, 255);
	private Color selectedColor1 = new Color(64, 224, 208); // 아름다운 푸른색 찾아보기.
	private Font grayFont = new Font("dialog", Font.BOLD, 12);
	private ChattingClient client;

	public CreatingChattingRoomPanel(ChattingClient client) {
		this.client = client;
		friendListPannel = new FriendsListPanel(true, client);
		// frame setting
		this.setBackground(backGraundColor);
		this.setSize(520, 430);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 현재 창만 꺼지도록
		// this.setUndecorated(true);
		this.add(topPanel, BorderLayout.NORTH);
		this.add(middlePanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		topPanel.setBackground(backGraundColor);
		topPanelLabel.setBackground(backGraundColor);
		topCountLabel.setBackground(backGraundColor);
		middlePanel.setBackground(backGraundColor);
		bottomPanel.setBackground(backGraundColor);
		middleSelectedFriendListPanel.setBackground(backGraundColor);
		// topPanel setting
		topPanel.setLayout(new BorderLayout());
		JPanel panelAdd = new JPanel();
		panelAdd.setBackground(backGraundColor);
		topPanel.add(panelAdd, BorderLayout.CENTER);
		panelAdd.add(topPanelLabel);
		panelAdd.add(topCountLabel);
		closeBtn.setBackground(backGraundColor);
		topPanelLabel.setFont(grayFont);
		topPanelLabel.setText("초대하기");
		topCountLabel.setText("" + count);

		// middlePanel setting
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
		friendListPannel.setPreferredSize(new Dimension(250, 200));
		friendListPannel.getTfSearch().setPreferredSize(new Dimension(230, 15));
		friendListPannel.getpSearch().setBackground(backGraundColor);
		friendListPannel.getTfSearch().setBackground(backGraundColor);
		middlePanel.add(friendListPannel);
		// middlePanel.add(middleChoiceFriendListScrollPanel);
		middleSelectedFriendListPanel.setPreferredSize(new Dimension(220, 200));
		// 크기 설정하려면 서치패널에 있는 크기를 필수적으로 줄여주어야 한다....
		selectedList = new JList<>(listmodel);
		selectedList.setCellRenderer(new FriendsListCellRenderer());
		ChoiceFriendListScrollPanel = new CustomJScrollPane(selectedList);
		ChoiceFriendListScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		ChoiceFriendListScrollPanel.setBorder(null);
		ChoiceFriendListScrollPanel.setPreferredSize(new Dimension(220, 200));
		middlePanel.add(ChoiceFriendListScrollPanel);
		selectedList.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
			friendListPannel.getSelectedFriends().remove(selectedList.getSelectedValue().getUserID());
			System.out.println(friendListPannel.getSelectedFriends().size());
				friendListPannel.setSelectedFriends(friendListPannel.getSelectedFriends());
				
//				MouseListener[] a= friendListPannel.getJlFriendsList().getMouseListeners();
//				a[0].mouseClicked(e);
//				listmodel.remove(selectedList.getSelectedIndex());
				validate();
				repaint();
			}
		});
		friendListPannel.getpSearch().getpSearchOuter().setBackground(backGraundColor);
		friendListPannel.getJlFriendsList().addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
				listmodel.removeAllElements();
				Iterator<Entry<String, UserVO>> entry = friendListPannel.getSelectedFriends().entrySet().iterator();
				for (int i = 0; i < friendListPannel.getSelectedFriends().size(); i++) {

					listmodel.addElement(entry.next().getValue());
				}
				count = friendListPannel.getSelectedFriends().size();
				topCountLabel.setText(count + "");
				repaint();
			}
		});
		// bottomPanel setting
		bottomPanel.add(confirmBtn);
		bottomPanel.add(cancelBtn);
		// 버튼 액션 리스너!!!
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LinkedHashMap<String, UserVO> lastSelected = (LinkedHashMap<String, UserVO>) friendListPannel
						.getSelectedFriends();

				Thread chatCreat = new Thread(new Runnable() {
					public void run() {
						ChattingController makeRoom = new ChattingController(client, lastSelected);// 다중
																									// 채팅컨트롤러손봐야
																									// 함.
					}
				});
				chatCreat.start();
				dispose();
			}
		});
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		this.setVisible(true);
	}

	class FriendsListCellRenderer extends JPanel implements ListCellRenderer<UserVO> {
		private JLabel lbCloseBtn = new JLabel();
		private ImageIcon modelIcon = new ImageIcon("images/modelimg.png");
		private ImageIcon graundIcon = new ImageIcon("images/btnimg.png");
		private JLabel totalLabel = new JLabel();
		private JLabel lbImgIcon = new JLabel();
		private JLabel lbName = new JLabel();
		private JLabel lbStatMsg = new JLabel();
		private JPanel panelText;

		public FriendsListCellRenderer() {
			// Border border = this.getBorder();
			Border margin = new EmptyBorder(5, 20, 5, 20);

			LineBorder roundedborder = new LineBorder(backGraundColor, 7, true);

			// lbCloseBtn.setForeground(selectedColor1);
			lbCloseBtn.setPreferredSize(new Dimension(48, 45));
			lbCloseBtn.setIcon(graundIcon);
			this.setLayout(new BorderLayout()); // 간격 조정이 되버림(확인필요)

			this.setBorder(roundedborder);
			// border // 둥글게 표현할수 있는지확인하기

			lbName.setFont(new Font("Malgun Gothic", Font.BOLD, 13));
			lbStatMsg.setFont(new Font("Malgun Gothic", Font.PLAIN, 8));
			// lbStatMsg.setBorder(new EmptyBorder(0, 6, 0, 6));

			panelText = new JPanel(new GridLayout(0, 1));
			// panelText.setBorder(new EmptyBorder(10, 6, 10, 0));
			panelText.add(lbName);
			// panelText.add(lbStatMsg);
			totalLabel.setLayout(new BorderLayout());
			add(totalLabel);

			totalLabel.setBorder(BorderFactory.createEmptyBorder(0, 38, 0, 0));
			totalLabel.setPreferredSize(new Dimension(200, 45));
			totalLabel.add(lbImgIcon, BorderLayout.WEST);
			JLabel pluslabel = new JLabel();
			totalLabel.add(pluslabel, BorderLayout.CENTER);
			pluslabel.setLayout(new BorderLayout());
			pluslabel.add(panelText, BorderLayout.WEST);
			pluslabel.add(lbCloseBtn, BorderLayout.CENTER);
			totalLabel.setIcon(modelIcon);
			lbImgIcon.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends UserVO> selectedColor, UserVO value, int index,
				boolean isSelected, boolean cellHasFocus) {
			// 받아온 JList의 값을 UserVO 객체에 담기
			UserVO vo = (UserVO) value;

			// 리턴할 객체에 둥근 프로필 이미지, 이름과, 상태 메세지 세팅
			ImageIcon profileImage = imageMaker.getRoundedImage(vo.getUserImgPath(), 35, 35);
			lbImgIcon.setIcon(profileImage);
			lbName.setText("   " + vo.getUserName());
			if (vo.getUserMsg() != null) {
				lbStatMsg.setText(vo.getUserMsg());
			}

			// 투명도 설정
			lbImgIcon.setOpaque(false);
			lbName.setOpaque(false);
			lbStatMsg.setOpaque(true);
			panelText.setOpaque(false);

			// 선택됐을때 색상 변경
			if (isSelected) {

				lbName.setBackground(selectedColor1);
				lbStatMsg.setBackground(selectedColor1);
				panelText.setBackground(selectedColor1);
				lbCloseBtn.setBackground(selectedColor1);
				setBackground(backGraundColor);
			} else {
				// lbImgIcon.setBackground(selectedColor1);
				lbName.setForeground(backGraundColor);
				lbStatMsg.setBackground(selectedColor1);
				panelText.setForeground(backGraundColor);
				lbCloseBtn.setBackground(selectedColor1);
				setBackground(backGraundColor);
			}

			return this;
		}
	}

	// 종료스레드 작성
	public static void main(String[] args) {
		CreatingChattingRoomPanel c = new CreatingChattingRoomPanel(null);
	}
}
