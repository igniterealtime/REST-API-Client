package org.igniterealtime.restclient;

import java.util.HashMap;
import java.util.Map;

import org.igniterealtime.restclient.RestClient.RestClientBuilder;
import org.igniterealtime.restclient.entity.AuthenticationToken;
import org.igniterealtime.restclient.entity.MUCRoomEntities;
import org.igniterealtime.restclient.entity.MUCRoomEntity;
import org.igniterealtime.restclient.entity.ParticipantEntities;
import org.igniterealtime.restclient.entity.SessionEntities;
import org.igniterealtime.restclient.entity.SystemProperties;
import org.igniterealtime.restclient.entity.SystemProperty;
import org.igniterealtime.restclient.entity.UserEntities;
import org.igniterealtime.restclient.entity.UserEntity;
import org.igniterealtime.restclient.entity.UserGroupsEntity;

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
		if (!url.startsWith("http")) {
			url = "http://" + url;
		}
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
	 * Gets the chat room participants.
	 *
	 * @param roomName the room name
	 * @return the chat room participants
	 */
	public ParticipantEntities getChatRoomParticipants(String roomName) {
		return restClient.get("chatrooms/" + roomName + "/participants", ParticipantEntities.class, new HashMap<String, String>());
	}

	/**
	 * Adds the owner.
	 *
	 * @param roomName
	 *            the room name
	 * @param jid
	 *            the jid
	 * @return true, if successful
	 */
	public boolean addOwner(String roomName, String jid) {
		return restClient.post("chatrooms/" + roomName + "/owners/" + jid, null, new HashMap<String, String>());
	}

	/**
	 * Adds the admin.
	 *
	 * @param roomName
	 *            the room name
	 * @param jid
	 *            the jid
	 * @return true, if successful
	 */
	public boolean addAdmin(String roomName, String jid) {
		return restClient.post("chatrooms/" + roomName + "/admins/" + jid, null, new HashMap<String, String>());
	}

	/**
	 * Adds the member.
	 *
	 * @param roomName
	 *            the room name
	 * @param jid
	 *            the jid
	 * @return true, if successful
	 */
	public boolean addMember(String roomName, String jid) {
		return restClient.post("chatrooms/" + roomName + "/members/" + jid, null, new HashMap<String, String>());
	}

	/**
	 * Adds the outcast.
	 *
	 * @param roomName
	 *            the room name
	 * @param jid
	 *            the jid
	 * @return true, if successful
	 */
	public boolean addOutcast(String roomName, String jid) {
		return restClient.post("chatrooms/" + roomName + "/outcasts/" + jid, null, new HashMap<String, String>());
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
	 * Gets the user groups.
	 *
	 * @param username the username
	 * @return the user groups
	 */
	public UserGroupsEntity getUserGroups(String username) {
		return restClient.get("users/" + username + "/groups", UserGroupsEntity.class,
				new HashMap<String, String>());
	}
	
	/**
	 * Adds the user to groups.
	 *
	 * @param username the username
	 * @param userGroupsEntity the user groups entity
	 * @return true, if adds the user to groups
	 */
	public boolean addUserToGroups(String username, UserGroupsEntity userGroupsEntity) {
		return restClient.post("users/" + username + "/groups", userGroupsEntity,
				new HashMap<String, String>());
	}
	
	
	/**
	 * Lockout/Ban a user from openfire.
	 *
	 * @param username the username
	 * @return true, if lockout user
	 */
	public boolean lockoutUser(String username) {
		return restClient.post("lockouts/" + username, null, new HashMap<String, String>());
	}
	
	/**
	 * Unlock user.
	 *
	 * @param username the username
	 * @return true, if unlock user
	 */
	public boolean unlockUser(String username) {
		return restClient.delete("lockouts/" + username, new HashMap<String, String>());
	}
	
	
	/**
	 * Gets the system properties.
	 *
	 * @return the system properties
	 */
	public SystemProperties getSystemProperties() {
		return restClient.get("system/properties", SystemProperties.class,
				new HashMap<String, String>());
	}
	
	/**
	 * Gets the system property.
	 *
	 * @param propertyName the property name
	 * @return the system property
	 */
	public SystemProperty getSystemProperty(String propertyName) {
		return restClient.get("system/properties/" + propertyName, SystemProperty.class,
				new HashMap<String, String>());
	}
	
	/**
	 * Creates the system property.
	 *
	 * @param property the property
	 * @return true, if creates the system property
	 */
	public boolean createSystemProperty(SystemProperty property) {
		return restClient.post("system/properties", property, new HashMap<String, String>());
	}
	
	/**
	 * Update system property.
	 *
	 * @param property the property
	 * @return true, if update system property
	 */
	public boolean updateSystemProperty(SystemProperty property) {
		return restClient.put("system/properties/" + property.getKey(), property, new HashMap<String, String>());
	}
	
	/**
	 * Delete system property.
	 *
	 * @param propertyName the property name
	 * @return true, if delete system property
	 */
	public boolean deleteSystemProperty(String propertyName) {
		return restClient.delete("system/properties/" + propertyName, new HashMap<String, String>());
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
