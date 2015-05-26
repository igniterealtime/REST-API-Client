package org.igniterealtime.restclient;

import java.util.HashMap;

import org.igniterealtime.restclient.RestClient.RestClientBuilder;
import org.igniterealtime.restclient.entity.AuthenticationToken;
import org.igniterealtime.restclient.entity.UserEntities;

/**
 * The Class RestApiCient.
 */
public class RestApiCient {

	/** The rest client. */
	private RestClient restClient;

	/**
	 * Instantiates a new rest api cient.
	 *
	 * @param url
	 *            the url
	 * @param port
	 *            the port
	 * @param authenticationToken
	 *            the authentication token
	 */
	public RestApiCient(String url, int port, AuthenticationToken authenticationToken) {
		restClient = new RestClientBuilder(url + ":" + port).authenticationToken(authenticationToken)
				.connectionTimeout(5000).build();
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public UserEntities getUsers() {
		UserEntities userEntities = restClient.get("users", UserEntities.class, new HashMap<String, String>());
		return userEntities;
	}

	/**
	 * Gets the rest client.
	 *
	 * @return the rest client
	 */
	public RestClient getRestClient() {
		return restClient;
	}

}
