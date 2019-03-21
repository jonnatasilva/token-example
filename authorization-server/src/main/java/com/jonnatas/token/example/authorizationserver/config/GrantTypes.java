package com.jonnatas.token.example.authorizationserver.config;

public enum GrantTypes {

	PASSWORD("password"),
	AUTHORIZATION_CODE("authorization_code"),
	REFRESH_CODE("refresh_token");
	
	private String name;
	
	GrantTypes(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
