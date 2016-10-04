package com.mc.mctalk.vo;

public class MemberInfoVO {

	private String memberId;
	private String memberPassword;
	private String memberName;
	private int memberSex;
	private String memberBirth;

	public String getMemberBirth() {
		return memberBirth;
	}

	public void setMemberBirth(String memberBirth) {
		this.memberBirth = memberBirth;
	}

	public MemberInfoVO(String memberId, String memberPassword, String memberName, int memberSex, int memberBirthMonth,
			int memberBirthDay) {
		this.memberId = memberId;
		this.memberPassword = memberPassword;
		this.memberName = memberName;
		this.memberSex = memberSex;
		this.memberBirth = "month : " + memberBirthMonth + " day : " + memberBirthDay;

	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public int getMemberSex() {
		return memberSex;
	}

	public void setMemberSex(int memberSex) {
		this.memberSex = memberSex;
	}
}
