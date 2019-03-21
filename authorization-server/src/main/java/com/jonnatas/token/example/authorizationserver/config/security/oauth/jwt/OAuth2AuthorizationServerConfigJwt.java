package com.jonnatas.token.example.authorizationserver.config.security.oauth.jwt;

import static com.jonnatas.token.example.authorizationserver.config.Scopes.*;
import static com.jonnatas.token.example.authorizationserver.config.GrantTypes.*;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.jonnatas.token.example.authorizationserver.token.CustomTokenEnhancer;

@Configuration
@EnableAuthorizationServer
@Profile("jwtTokenStore")
public class OAuth2AuthorizationServerConfigJwt extends AuthorizationServerConfigurerAdapter {

	@Value("${application.security.token.access.validity.seconds: 3600}")
	private Integer accessTokenValiditySeconds;
	
	@Value("${application.security.token.refresh.validity.seconds: 36000}")
	private Integer refreshTokenValiditySeconds;
	
	@Value("${application.security.keystore.alias}")
	private String keystoreAlias;
	
	@Value("${application.security.keystore.path}")
	private String keystorePath;
	
	@Value("${application.security.keystore.pass}")
	private String kestorePass;
	
	private static final String TOKEN_ACCESS_METHOD = "permitAll()";
	private static final String CHECK_TOKEN_ACCESS_METHOD = "isAuthenticated()";

	@Autowired
	@Qualifier("authenticationManagerBean")
	AuthenticationManager authenticationManager;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();

		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));

		endpoints.tokenStore(tokenStore()).tokenEnhancer(tokenEnhancerChain)
				.accessTokenConverter(accessTokenConverter()).authenticationManager(authenticationManager);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess(TOKEN_ACCESS_METHOD).checkTokenAccess(CHECK_TOKEN_ACCESS_METHOD);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
		.inMemory()
			.withClient("sampleClientId")
			.authorizedGrantTypes("implicit")
			.scopes(READ.getName())
			.autoApprove(true)
			.accessTokenValiditySeconds(accessTokenValiditySeconds)
		.and()
			.withClient("clientIdPassword")
			.secret(passwordEncoder().encode("secret"))
			.authorizedGrantTypes(PASSWORD.getName(), AUTHORIZATION_CODE.getName(), REFRESH_CODE.getName())
			.scopes(READ.getName())
			.accessTokenValiditySeconds(accessTokenValiditySeconds)
			.refreshTokenValiditySeconds(refreshTokenValiditySeconds);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}

	@Bean
	TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(keystorePath),
				kestorePass.toCharArray());

		converter.setKeyPair(keyStoreKeyFactory.getKeyPair(keystoreAlias));
		return converter;
	}

	@Bean
	@Primary
	DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}
}
