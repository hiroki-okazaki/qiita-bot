package com.example.domain;

public class User {

	/**
	 * ID(主キー)
	 */
	private Integer id;
	
	/**
	 * ユーザーid
	 */
	private String userId;
	
	/**
	 * 登録ブログのURL
	 */
	private String registrationUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRegistrationUrl() {
		return registrationUrl;
	}

	public void setRegistrationUrl(String registrationUrl) {
		this.registrationUrl = registrationUrl;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", registrationUrl=" + registrationUrl + "]";
	}
	
}
