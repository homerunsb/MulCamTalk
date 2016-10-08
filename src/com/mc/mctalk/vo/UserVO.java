package com.mc.mctalk.vo;

import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.mc.mctalk.view.uiitem.RoundedImageMaker;

public class UserVO implements Serializable{
	private final String DEFAULT_IMAGE = "images/default_profile.png";
	private String userID, userPassword, userName, userMsg, userBirth, 
						userMail, userPhone, userAddress, userJoinDate, userImgPath;
	private int userSex;

	public UserVO(){		
	}
	
	public UserVO(String text, String text2, String text3, int memberSex, int memberBirthMonth, int memberBirthDay) {
		this.userID = text;
		this.userPassword = text2;
		this.userName = text3;
		this.userSex = memberSex;
		this.userBirth = "month : " + memberBirthMonth + " day : " + memberBirthDay;
	}
	
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
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
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
	public String getUserJoinDate() {
		return userJoinDate;
	}
	public void setUserJoinDate(String userJoinDate) {
		this.userJoinDate = userJoinDate;
	}
	public String getUserImgPath() {
		return userImgPath;
	}
	public void setUserImgPath(String userImgPath) {
		this.userImgPath = userImgPath;
	}

	@Override
	public String toString() {
		return "UserVO [userID=" + userID + ", userPassword=" + userPassword + ", userName=" + userName + ", userMsg="
				+ userMsg + ", userBirth=" + userBirth + ", userMail=" + userMail + ", userPhone=" + userPhone
				+ ", userAddress=" + userAddress + ", userJoinDate=" + userJoinDate + ", userImgPath=" + userImgPath
				+ ", userSex=" + userSex + "]";
	}
}
