package com.mc.mctalk.view.uiitem;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class LogoManager{
	public static void setLogoFrame(JFrame jFrame){
		System.out.println("LogoManager : setLogoFrame()");
		System.out.println(jFrame);
		List<Image> icons = new ArrayList<Image>();
		icons.add(new ImageIcon("images/logo_small_16.png").getImage());
		icons.add(new ImageIcon("images/logo_small_32.png").getImage());
		jFrame.setIconImages(icons);
	}
}
