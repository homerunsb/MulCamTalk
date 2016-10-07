package com.mc.mctalk.vo;

import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.mc.mctalk.view.uiitem.RoundedImageMaker;

public class UserVO implements Serializable{
    private static final long serialVersionUID = 2L;

	private final String DEFAULT_IMAGE = "images/default_profile.png";
	private String userID, userPassword, userName, userMsg, userBirth, userPhone, userAddress;
	private int userSex;
	private Icon profileImage;

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserBirth() {
		return userBirth;
	}
	public void setUserBirth(String userBirth) {
		this.userBirth = userBirth;
	}
	public int getUserSex() {
		return userSex;
	}
	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getUserMsg() {
		return userMsg;
	}
	public void setUserMsg(String userMsg) {
		this.userMsg = userMsg;
	}
	public Icon getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String img_path) {
		//프로필 이미지가 지정이 안되어 있을시 디폴트 이미지 사용
		if(img_path == null || img_path.equals("")){
			img_path = DEFAULT_IMAGE;
		}
		//이미지 둥글게 만들기
		RoundedImageMaker imageMaker = new RoundedImageMaker();
		ImageIcon profileImage = imageMaker.getRoundedImage(img_path, 70, 70);
		this.profileImage = profileImage;
	}
}
