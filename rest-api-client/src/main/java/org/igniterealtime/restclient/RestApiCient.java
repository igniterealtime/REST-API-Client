package org.igniterealtime.restclient;

import org.igniterealtime.restclient.entity.AuthenticationToken;
import org.igniterealtime.restclient.entity.UserEntities;

public class RestApiCient {
	private String url;
	private int port;
	private AuthenticationToken authenticationToken;

	public RestApiCient(String url, int port, AuthenticationToken authenticationToken) {
		this.url = url;
		this.port = port;
		this.authenticationToken = authenticationToken;
	}

	public UserEntities getUsers() {

		return null;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public AuthenticationToken getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(AuthenticationToken authenticationToken) {
		this.authenticationToken = authenticationToken;
	}
}
