package com.raysdata.riskmanagementserver.bean;

public class UserInfo {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String uPaword;
    
    /**
     * 真实姓名
     */
    private String realName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getuPaword() {
		return uPaword;
	}

	public void setuPaword(String uPaword) {
		this.uPaword = uPaword;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}