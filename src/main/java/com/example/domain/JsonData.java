package com.example.domain;

import java.util.List;

public class JsonData {

	
	private String uuid;
	
	private String urlName;
	
	private List<String> urlList;
	

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	@Override
	public String toString() {
		return "JsonData [uuid=" + uuid + ", urlName=" + urlName + "]";
	}
	
	
	
}
