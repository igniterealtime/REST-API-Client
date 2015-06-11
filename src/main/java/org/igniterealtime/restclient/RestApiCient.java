package org.igniterealtime.restclient;

import java.util.HashMap;

import org.igniterealtime.restclient.RestClient.RestClientBuilder;
import org.igniterealtime.restclient.entity.AuthenticationToken;
import org.igniterealtime.restclient.entity.SessionEntities;
import org.igniterealtime.restclient.entity.UserEntities;
import org.igniterealtime.restclient.entity.UserEntity;

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
	 * Gets the filtered users.
	 *
	 * @param searchUsername Search/Filter by username. This act like the wildcard search %String%
	 * @return the filtered users
	 */
	public UserEntities getFilteredUsers(String searchUsername) {
		HashMap<String, String> querys = new HashMap<String, String>();
		querys.put("search", searchUsername);
		UserEntities userEntities = restClient.get("users", UserEntities.class, querys);
		return userEntities;
	}
	
	/**
	 * Gets the user.
	 *
	 * @param username the username
	 * @return the user
	 */
	public UserEntity getUser(String username) {
		UserEntity userEntity = restClient.get("users/" + username, UserEntity.class, new HashMap<String, String>());
		return userEntity;
	}
	
	/**
	 * Creates the user.
	 *
	 * @param userEntity the user entity
	 */
	public void createUser(UserEntity userEntity) {
		restClient.post("users", UserEntity.class, userEntity, new HashMap<String, String>());
	}
	
	/**
	 * Gets the sessions.
	 *
	 * @return the sessions
	 */
	public SessionEntities getSessions() {
		SessionEntities sessionEntities = restClient.get("sessions", SessionEntities.class, new HashMap<String, String>());
		return sessionEntities;
	}
	
	/**
	 * Gets the sessions.
	 *
	 * @param username the username
	 * @return the sessions
	 */
	public SessionEntities getSessions(String username) {
		SessionEntities sessionEntities = restClient.get("sessions/" + username, SessionEntities.class, new HashMap<String, String>());
		return sessionEntities;
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
