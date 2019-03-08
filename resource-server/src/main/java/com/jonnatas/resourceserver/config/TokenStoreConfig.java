package com.jonnatas.resourceserver.config;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
public class TokenStoreConfig {

	@Value("${authentication.schema:http}")
	private String authenticationSchema;
	
	@Value("${authentication.host}")
	private String authenticationHost;
	
	@Value("${authentication.port}")
	private Integer authenticationPort;
	
	private String authenticationPath; 
	
	@Value("${authentication.clientId}")
	private String authenticationClientId;
	
	@Value("${authentication.secret}")
	private String authenticationSecret;
	
	@Primary
	@Bean
	public RemoteTokenServices tokenService() throws MalformedURLException, URISyntaxException {
		RemoteTokenServices tokenService = new RemoteTokenServices();
		tokenService.setCheckTokenEndpointUrl(getAuthenticationServerURL());
		tokenService.setClientId(authenticationClientId);
		tokenService.setClientSecret(authenticationSecret);
		return tokenService;
	}
	
	@Value("${authentication.path}")
	public void setAuthenticationPath(String authenticationPath) {
		this.authenticationPath = authenticationPath.startsWith("/") ? authenticationPath : "/" + authenticationPath;
	}
	
	private String getAuthenticationServerURL() throws MalformedURLException, URISyntaxException {
		return new URI(authenticationSchema, null, authenticationHost, this.authenticationPort, authenticationPath, null, null).toURL().toString();
	}
}
