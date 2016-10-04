package com.mc.mctalk.view.uiitem;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.AbstractBorder;

public class RoundedImageMaker extends AbstractBorder {
	public ImageIcon getRoundedImage(String path, int width, int height) {
		try {
			BufferedImage master = ImageIO.read(new File(path));

			int diameter = Math.min(master.getWidth(), master.getHeight());
			BufferedImage mask = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);

			Graphics2D g2d = mask.createGraphics();
			applyQualityRenderingHints(g2d);
			g2d.fillOval(0, 0, diameter - 1, diameter - 1);
			g2d.dispose();

			BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
			g2d = masked.createGraphics();
			applyQualityRenderingHints(g2d);
			int x = (diameter - master.getWidth()) / 2;
			int y = (diameter - master.getHeight()) / 2;
			g2d.drawImage(master, x, y, null);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
			g2d.drawImage(mask, 0, 0, null);
			g2d.dispose();
			
			//리사이즈 추가
			ImageIcon icon = new ImageIcon(masked);
			Image image = icon.getImage();
			Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			icon = new ImageIcon(newImage);
			
			return icon;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void applyQualityRenderingHints(Graphics2D g2d) {

		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

	}

}