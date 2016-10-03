package com.mc.mctalk.view.uiitem;

import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.plaf.metal.MetalScrollBarUI;

public class CustomJScrollPane2 extends JScrollPane {

	/** @see http://stackoverflow.com/a/12270067/230513 */

	public static void main(String[] args) {
		JScrollPane before = makeExamplePane();
		JScrollPane after = makeExamplePane();
		JScrollBar sb = after.getVerticalScrollBar();
		sb.setUI(new MyScrollbarUI());
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new GridLayout(0, 1));
		f.add(before);
		f.add(after);
		f.pack();
		f.setSize(320, 240);
		f.setVisible(true);
	}

	private static JScrollPane makeExamplePane() {
		JTextArea text = new JTextArea(16, 16);
		text.append("Lorem ipsum dolor sit amet…");
		JScrollPane scroll = new JScrollPane(text);
		return scroll;
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
			imageThumb = FauxImage.create(5, 5, Color.lightGray);
			imageTrack = FauxImage.create(5, 5, new Color(238, 238, 238));
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
