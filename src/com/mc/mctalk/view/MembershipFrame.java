package com.mc.mctalk.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import org.jdesktop.swingx.prompt.PromptSupport;
import com.mc.mctalk.dao.UserDAO;
import com.mc.mctalk.vo.MemberInfoVO;
/**
 * 
 * @author : 김영태
 * @comment : 권석범
 * 추가 필요한 기능 : 아이디 중복 체크 기능, 회원 가입 후, 취소 후 로그인창으로 이동 
 * 불필요한 개행 정리 필요.
 * boolean 변수의 시작은 is~, has~, can~ 으로 시작으로 변경 필요
 * 93라인, 313라인 DAO 객체 중복 정의 되어 있음  - 중복정의 수정 완료(2016.10.6)
 * 전반적인 리펙토링 필요(ex. 패널8개가 배열에 들어가 있어, 각각의 패널이 어느 부분에서 어떤 역할을 하는지 유지보수 측면에서 불확실함) - 수정완료 2016.10.7
 */
public class MembershipFrame extends JFrame {
	private JPanel mainPanel = new JPanel();
	private JTextField idfield = new JTextField();
	private JTextField nameTextfield = new JTextField();
	private JPasswordField passwordfield = new JPasswordField();
	private JPasswordField passwordCheckfield = new JPasswordField();
	private boolean joinJudge = false;
	private boolean checkManJudge = false;
	private boolean checkWomanJudge = false;
	private int checkSexReuslt = 2;
	private String id = new String("아이디");
	private JLabel errorCheck = new JLabel();
	private JLabel birthLabel = new JLabel("");
	private JLabel monthLabel = new JLabel("월");
	private JLabel labelSpace = new JLabel("          ");
	private JLabel dayLabel = new JLabel("일");
	private JLabel labelSpace2 = new JLabel(" ");
	private JCheckBox checkBoxMan = new JCheckBox("남자");
	private JCheckBox checkBoxWoman = new JCheckBox("여자");
	private JComboBox monthCombo;
	private JComboBox dayCombo;
	private JButton join = new JButton("가입하기");
	private Font grayFont = new Font("dialog", Font.BOLD, 12);
	private Color backGroundColor = new Color(255, 255, 255);
	private Dimension fieldSize = new Dimension(215, 27);
	private joinResult joincomplete = new joinResult();
	private ImageIcon tokLogo = new ImageIcon("images/logo.png");
	private CheckPassword checkPassword = new CheckPassword();
	private ActionCheckManSexBox checkManSex = new ActionCheckManSexBox();
	private ActionCheckWomanSexBox checkWomanSex = new ActionCheckWomanSexBox();
	private JOptionPane NullWarning = new JOptionPane();
	private Map<String, MemberInfoVO> memberInfos = new HashMap<String, MemberInfoVO>();
	private List<JPanel> panels = new ArrayList<>();
	private JPanel logoPanel = new JPanel();
	private	JPanel idPanel = new JPanel();
	private JPanel passwordPanel = new JPanel();
	private JPanel passwordCheckPanel = new JPanel();
	private JPanel overlapCheckPanel = new JPanel();
	private JPanel nameCheckBoxPanel = new JPanel();
	private JPanel birthPanel = new JPanel();
	private JPanel joinbtnPanel = new JPanel();
	//  프레임 생성자 
	public MembershipFrame() {
		// frame setting
		panels.add(logoPanel);
		panels.add(idPanel);
		panels.add(passwordPanel);
		panels.add(passwordCheckPanel);
		panels.add(overlapCheckPanel);
		panels.add(nameCheckBoxPanel);
		panels.add(birthPanel);
		panels.add(joinbtnPanel);
		this.setTitle("membership");
		this.setSize(380, 550);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(mainPanel);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		for(JPanel panel : panels){
			mainPanel.add(panel);
			panel.setBackground(backGroundColor);
		}
		// logo panel setting(1 panel)
		JLabel logoLabel = new JLabel();
		logoPanel.add(logoLabel);
		logoPanel.setSize(380, 220);
		logoPanel.setPreferredSize(logoPanel.getSize());
		logoLabel.setIcon(tokLogo);
		/// id panel setting(2 panel)
		idfield.setLocation(5, 5);
		PromptSupport.setPrompt(id, idfield);
		Border fieldBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true);
		idfield.setSize(fieldSize);
		idfield.setFont(grayFont);
		idfield.setBorder(fieldBorder);
		idfield.setPreferredSize(idfield.getSize());
		idPanel.add(idfield);
		// password panel setting( 3 panel)
		passwordfield.setEchoChar('*');
		passwordfield.setFont(grayFont);
		passwordfield.setBorder(fieldBorder);
		passwordfield.setSize(fieldSize);
		passwordfield.setPreferredSize(passwordfield.getSize());
		PromptSupport.setPrompt("비밀번호", passwordfield);
		passwordPanel.add(passwordfield);
		// check password panel setting(4panel)
		passwordCheckPanel.add(passwordCheckfield);
		passwordCheckPanel.setSize(380, 25);
		passwordCheckPanel.setPreferredSize(passwordCheckPanel.getSize());
		passwordCheckfield.setEchoChar('*');
		passwordCheckfield.setFont(grayFont);
		passwordCheckfield.setBorder(fieldBorder);
		passwordCheckfield.setSize(fieldSize);
		passwordCheckfield.setPreferredSize(passwordCheckfield.getSize());
		passwordCheckfield.addKeyListener(checkPassword);
		PromptSupport.setPrompt("비밀번호 확인", passwordCheckfield);
		// error check panel setting (5panel)
		overlapCheckPanel.add(errorCheck);
		overlapCheckPanel.setSize(380, 20);
		overlapCheckPanel.setPreferredSize(overlapCheckPanel.getSize());
		// name and checkbox panel setting(6panel)
		nameTextfield.setFont(grayFont);
		nameTextfield.setBorder(fieldBorder);
		nameTextfield.setSize(100, 27);
		nameTextfield.setPreferredSize(nameTextfield.getSize());
		PromptSupport.setPrompt("이름", nameTextfield);
		nameCheckBoxPanel.setSize(370, 40);
		nameCheckBoxPanel.setPreferredSize(nameCheckBoxPanel.getSize());
		nameCheckBoxPanel.add(nameTextfield);
		nameCheckBoxPanel.add(checkBoxMan);
		nameCheckBoxPanel.add(checkBoxWoman);
		checkBoxMan.setBackground(backGroundColor);
		checkBoxWoman.setBackground(backGroundColor);
		checkBoxMan.addActionListener(checkManSex);
		checkBoxWoman.addActionListener(checkWomanSex);

		// birth panel setting (7panel)
		String[] months = new String[13];
		for (int i = 0; i < months.length; i++) {
			if (i == 0) {
				months[i] = "";
			} else {
				months[i] = i + "";
			}
		}
		String[] days = new String[32];
		for (int i = 0; i < months.length; i++) {
			if (i == 0) {
				days[i] = "";
			} else {
				days[i] = i + "";
			}
		}
		dayCombo = new JComboBox(days);
		monthCombo = new JComboBox(months);
		birthLabel.setLayout(new BoxLayout(birthLabel, BoxLayout.X_AXIS));
		birthLabel.setSize(fieldSize);
		birthLabel.setFont(grayFont);
		birthLabel.setBorder(fieldBorder);
		birthLabel.setPreferredSize(birthLabel.getSize());
		birthPanel.add(birthLabel);
		birthLabel.setFont(grayFont);
		birthLabel.setText("생일");
		birthLabel.add(labelSpace);
		birthLabel.add(labelSpace2);
		birthLabel.add(monthLabel);
		birthLabel.add(monthCombo);
		birthLabel.add(dayLabel);
		birthLabel.add(dayCombo);
		// joinbtn panel setting(8panel)
		if (joinJudge) {
			join.setEnabled(joinJudge);
		}
		joinbtnPanel.add(join);
		join.addActionListener(joincomplete);

	}
	// run
	public static void main(String[] args) {
		MembershipFrame f = new MembershipFrame();
	}
	// listener setting
	class CheckPassword implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (passwordfield.getText().equals(passwordCheckfield.getText() + e.getKeyChar())) {
				errorCheck.setText(" ");
				joinJudge = true;
			} else {
				errorCheck.setFont(new Font("Dialog", Font.PLAIN, 10));
				errorCheck.setForeground(Color.red);
				errorCheck.setText("비밀번호가 잘못입력 되었습니다!");
			}
		}
		public void keyReleased(KeyEvent e) {
		}
		public void keyTyped(KeyEvent e) {
		}
	}
	class ActionCheckManSexBox implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			checkSexReuslt = 0;
			checkManJudge = true;
			if (checkManJudge == true && checkWomanJudge == true) {
				checkBoxWoman.setSelected(false);
			}
		}
	}
	class ActionCheckWomanSexBox implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			checkSexReuslt = 1;
			checkWomanJudge = true;
			if (checkManJudge == true && checkWomanJudge == true) {
				checkBoxMan.setSelected(false);
			}
		}
	}
	class joinResult implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (idfield.getText().equals("")) {
				NullWarning.showMessageDialog(idfield, "아이디 안넣으셨어요!!");
			}
			if (passwordfield.getText().equals("")) {
				NullWarning.showMessageDialog(idfield, "비밀번호 안넣으셨어요!!");
			}
			if (nameTextfield.getText().equals("")) {
				NullWarning.showMessageDialog(idfield, "이름을 안넣으셨어요!!");
			}
			if (checkSexReuslt == 2) {
				NullWarning.showMessageDialog(idfield, "성별 입력 확인하세요!");
			}
			if (monthCombo.getSelectedIndex() == 0) {
				NullWarning.showMessageDialog(idfield, "날짜 입력 확인하세요!");
			}
			if (dayCombo.getSelectedIndex() == 0) {
				NullWarning.showMessageDialog(idfield, "날짜 입력 확인하세요!");
			}
			if(!idfield.getText().equals("")&&!passwordfield.getText().equals("")
					&&!nameTextfield.getText().equals("")&&!(checkSexReuslt == 2)
					&&!(monthCombo.getSelectedIndex() == 0)&&!(dayCombo.getSelectedIndex() == 0)){
				memberInfos.put(idfield.getText(),
						new MemberInfoVO(idfield.getText(), passwordfield.getText(), nameTextfield.getText(),
								checkSexReuslt, monthCombo.getSelectedIndex(), dayCombo.getSelectedIndex()));
				UserDAO memberDAO = new UserDAO();
				if(memberDAO.joinMember(memberInfos.get(idfield.getText()))){
					NullWarning.showMessageDialog(joinbtnPanel, "가입완료!!"); 
				}
			}
		}
	}
}
