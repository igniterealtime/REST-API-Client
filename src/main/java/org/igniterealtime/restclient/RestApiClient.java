package org.igniterealtime.restclient;

import java.util.HashMap;
import java.util.Map;

import org.igniterealtime.restclient.RestClient.RestClientBuilder;
import org.igniterealtime.restclient.entity.AuthenticationToken;
import org.igniterealtime.restclient.entity.MUCRoomEntities;
import org.igniterealtime.restclient.entity.MUCRoomEntity;
import org.igniterealtime.restclient.entity.SessionEntities;
import org.igniterealtime.restclient.entity.UserEntities;
import org.igniterealtime.restclient.entity.UserEntity;

/**
 * The Class RestApiClient.
 */
public class RestApiClient {

	/** The rest client. */
	private RestClient restClient;

	/**
	 * Instantiates a new rest api client.
	 *
	 * @param url
	 *            the url
	 * @param port
	 *            the port
	 * @param authenticationToken
	 *            the authentication token
	 */
	public RestApiClient(String url, int port, AuthenticationToken authenticationToken) {
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
	 * Gets the users.
	 *
	 * @param queryParams
	 *            the query params
	 * @return the users
	 */
	public UserEntities getUsers(Map<String, String> queryParams) {
		UserEntities userEntities = restClient.get("users", UserEntities.class, queryParams);
		return userEntities;
	}

	/**
	 * Gets the user.
	 *
	 * @param username
	 *            the username
	 * @return the user
	 */
	public UserEntity getUser(String username) {
		UserEntity userEntity = restClient.get("users/" + username, UserEntity.class, new HashMap<String, String>());
		return userEntity;
	}

	/**
	 * Creates the user.
	 *
	 * @param userEntity
	 *            the user entity
	 * @return true, if successful
	 */
	public boolean createUser(UserEntity userEntity) {
		return restClient.post("users", userEntity, new HashMap<String, String>());
	}

	/**
	 * Update user.
	 *
	 * @param userEntity
	 *            the user entity
	 * @return true, if successful
	 */
	public boolean updateUser(UserEntity userEntity) {
		return restClient.put("users/" + userEntity.getUsername(), userEntity, new HashMap<String, String>());
	}

	/**
	 * Delete user.
	 *
	 * @param username
	 *            the username
	 * @return true, if successful
	 */
	public boolean deleteUser(String username) {
		return restClient.delete("users/" + username, new HashMap<String, String>());
	}

	/**
	 * Gets the chat rooms.
	 *
	 * @return the chat rooms
	 */
	public MUCRoomEntities getChatRooms() {
		return restClient.get("chatrooms", MUCRoomEntities.class, new HashMap<String, String>());
	}

	/**
	 * Gets the chat rooms.
	 *
	 * @param queryParams
	 *            the query params
	 * @return the chat rooms
	 */
	public MUCRoomEntities getChatRooms(Map<String, String> queryParams) {
		return restClient.get("chatrooms", MUCRoomEntities.class, queryParams);
	}

	/**
	 * Gets the chat room.
	 *
	 * @param roomName
	 *            the room name
	 * @return the chat room
	 */
	public MUCRoomEntity getChatRoom(String roomName) {
		return restClient.get("chatrooms/" + roomName, MUCRoomEntity.class, new HashMap<String, String>());
	}

	/**
	 * Creates the chat room.
	 *
	 * @param chatRoom
	 *            the chat room
	 * @return true, if successful
	 */
	public boolean createChatRoom(MUCRoomEntity chatRoom) {
		return restClient.post("chatrooms", chatRoom, new HashMap<String, String>());
	}

	/**
	 * Update chat room.
	 *
	 * @param chatRoom
	 *            the chat room
	 * @return true, if successful
	 */
	public boolean updateChatRoom(MUCRoomEntity chatRoom) {
		return restClient.put("chatrooms/" + chatRoom.getRoomName(), chatRoom, new HashMap<String, String>());
	}

	/**
	 * Delete chat room.
	 *
	 * @param roomName
	 *            the room name
	 * @return true, if successful
	 */
	public boolean deleteChatRoom(String roomName) {
		return restClient.delete("chatrooms/" + roomName, new HashMap<String, String>());
	}

	/**
	 * Gets the sessions.
	 *
	 * @return the sessions
	 */
	public SessionEntities getSessions() {
		SessionEntities sessionEntities = restClient.get("sessions", SessionEntities.class,
				new HashMap<String, String>());
		return sessionEntities;
	}

	/**
	 * Gets the sessions.
	 *
	 * @param username
	 *            the username
	 * @return the sessions
	 */
	public SessionEntities getSessions(String username) {
		SessionEntities sessionEntities = restClient.get("sessions/" + username, SessionEntities.class,
				new HashMap<String, String>());
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
