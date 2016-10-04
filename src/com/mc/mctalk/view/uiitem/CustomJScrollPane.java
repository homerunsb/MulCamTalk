package com.mc.mctalk.view.uiitem;

import javax.swing.JScrollPane;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.plaf.metal.MetalScrollBarUI;

public class CustomJScrollPane extends JScrollPane {
	public CustomJScrollPane() {
		super();
	}
	public CustomJScrollPane(JList jlist) {
		super(jlist);
		JScrollBar sb = this.getVerticalScrollBar();
		
		//스크롤바 모양 설정 (굵기, 보더)
		sb.setPreferredSize(new Dimension(15, 0));
	    Border brd = BorderFactory.createMatteBorder(1, 3, 1, 3, new Color(238, 238, 238));
		sb.setBorder(brd);
		
		sb.setUI(new MyScrollbarUI());
	}

	static class MyScrollbarUI extends MetalScrollBarUI {

		private Image imageThumb, imageTrack;
		private JButton b = new JButton() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(0, 0);
			}
		};

		MyScrollbarUI() {
			imageThumb = FauxImage.create(1, 1, Color.lightGray);
			imageTrack = FauxImage.create(1, 1, new Color(238, 238, 238));
		}

		@Override
		protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
			g.setColor(Color.blue);
			((Graphics2D) g).drawImage(imageThumb, r.x, r.y, r.width, r.height, null);
		}

		@Override
		protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
			((Graphics2D) g).drawImage(imageTrack, r.x, r.y, r.width, r.height, null);
		}

		@Override
		protected JButton createDecreaseButton(int orientation) {
			return b;
		}

		@Override
		protected JButton createIncreaseButton(int orientation) {
			return b;
		}
	}

	private static class FauxImage {
		static public Image create(int w, int h, Color c) {
			BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = bi.createGraphics();
			g2d.setPaint(c);
			g2d.fillRect(0, 0, w, h);
			g2d.dispose();
			return bi;
		}
	}
}
