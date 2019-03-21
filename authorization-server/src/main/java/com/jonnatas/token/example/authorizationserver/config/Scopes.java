package com.jonnatas.token.example.authorizationserver.config;

public enum Scopes {
	
	READ("read"),
	WRITE("write");
	
	private String name;
	
	Scopes(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
