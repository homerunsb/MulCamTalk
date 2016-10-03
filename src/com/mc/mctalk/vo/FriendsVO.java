package com.mc.mctalk.vo;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class FriendsVO {
	private final String DEFAULT_IMAGE = "images/default_profile.png";
	private String userID, userName, userMsg;
	Icon profileImage;

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Icon getProfileImage() {
		return profileImage;
	}
	public String getUserMsg() {
		return userMsg;
	}
	public void setUserMsg(String userMsg) {
		this.userMsg = userMsg;
	}
	public void setProfileImage(String img_path) {
		if(img_path == null || img_path.equals("")){
			img_path = DEFAULT_IMAGE;
		}
		ImageIcon profileImage = new ImageIcon(img_path);
		this.profileImage = profileImage;
	}
}
