package com.mc.mctalk.view.uiitem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.prompt.PromptSupport;

public class SearchPanel extends JPanel{
	private JPanel pSearchInner, pSearchOuter;
	private JTextField tfSearch;
	private JLabel jlSearch;
	
	public SearchPanel() {
		this.setLayout(new BorderLayout());
		pSearchInner = new JPanel();
		pSearchOuter = new JPanel();
		pSearchInner.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
		pSearchInner.setBackground(Color.white);
		pSearchInner.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		// 친구 검색 창 placeHolder 설정
		// 앞 부분에 돋보기 모양 작은 이미지 박스 추가 필요
		// placeholder 색 변경 및 PANEL 크기 조정 필요
		tfSearch = new JTextField(29);
		tfSearch.setPreferredSize(new Dimension(290, 15));
		tfSearch.setBorder(BorderFactory.createEmptyBorder());
		PromptSupport.setPrompt("이름검색", tfSearch);
		
		jlSearch = new JLabel(new ImageIcon("images/icon_search.png"));
		pSearchInner.add(jlSearch);
		pSearchInner.add(tfSearch);
		pSearchOuter.add(pSearchInner);
		this.add(pSearchOuter);
	}
	
	public JTextField getTfSearch() {
		return tfSearch;
	}
	public void setTfSearch(JTextField tfSearch) {
		this.tfSearch = tfSearch;
	}
	
	
}
