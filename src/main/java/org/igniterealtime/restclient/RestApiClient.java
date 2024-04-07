package org.igniterealtime.restclient;

import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;



import org.igniterealtime.restclient.RestClient.RestClientBuilder;
import org.igniterealtime.restclient.entity.AuthenticationToken;
import org.igniterealtime.restclient.entity.GroupEntities;
import org.igniterealtime.restclient.entity.GroupEntity;
import org.igniterealtime.restclient.entity.MUCRoomEntities;
import org.igniterealtime.restclient.entity.MUCRoomEntity;
import org.igniterealtime.restclient.entity.ParticipantEntities;
import org.igniterealtime.restclient.entity.RosterEntities;
import org.igniterealtime.restclient.entity.RosterItemEntity;
import org.igniterealtime.restclient.entity.SessionEntities;
import org.igniterealtime.restclient.entity.SystemProperties;
import org.igniterealtime.restclient.entity.SystemProperty;
import org.igniterealtime.restclient.entity.UserEntities;
import org.igniterealtime.restclient.entity.UserEntity;
import org.igniterealtime.restclient.entity.UserGroupsEntity;
import org.igniterealtime.restclient.enums.SupportedMediaType;

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
	 *                            the url
	 * @param port
	 *                            the port
	 * @param authenticationToken
	 *                            the authentication token
	 */
	public RestApiClient(
			String url, int port,
			AuthenticationToken authenticationToken) {
		url = adjustURL(url);
		restClient = new RestClientBuilder(url + ":" + port)
				.authenticationToken(authenticationToken)
				.connectionTimeout(5000)
				.mediaType(SupportedMediaType.XML)
				.build();
	}

	/**
	 * Instantiates a new rest api client.
	 *
	 * @param url
	 *                            the url
	 * @param port
	 *                            the port
	 * @param authenticationToken
	 *                            the authentication token
	 * @param mediaType
	 *                            the media to send/accept
	 */
	public RestApiClient(
			String url,
			int port,
			AuthenticationToken authenticationToken,
			SupportedMediaType mediaType) {
		url = adjustURL(url);
		restClient = new RestClientBuilder(url + ":" + port)
				.authenticationToken(authenticationToken)
				.connectionTimeout(5000)
				.mediaType(mediaType)
				.build();
	}

	/**
	 * Instantiates a new rest api client.
	 *
	 * @param url                 the url
	 * @param port                the port
	 * @param authenticationToken the authentication token
	 * @param connectionTimeout   the connection timeout
	 */
	public RestApiClient(
			String url,
			int port,
			AuthenticationToken authenticationToken,
			int connectionTimeout) {
		url = adjustURL(url);
		restClient = new RestClientBuilder(url + ":" + port)
				.authenticationToken(authenticationToken)
				.connectionTimeout(connectionTimeout)
				.mediaType(SupportedMediaType.XML)
				.build();
	}

	/**
	 * Instantiates a new rest api client.
	 *
	 * @param url                 the url
	 * @param port                the port
	 * @param authenticationToken the authentication token
	 * @param connectionTimeout   the connection timeout
	 * @param mediaType           the media to send/accept
	 */
	public RestApiClient(
			String url,
			int port,
			AuthenticationToken authenticationToken,
			int connectionTimeout,
			SupportedMediaType mediaType) {
		url = adjustURL(url);
		restClient = new RestClientBuilder(url + ":" + port)
				.authenticationToken(authenticationToken)
				.connectionTimeout(connectionTimeout)
				.mediaType(mediaType)
				.build();
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public UserEntities getUsers() {
		return restClient.get("users", UserEntities.class, new HashMap<>());
	}

	/**
	 * Gets the users.
	 *
	 * @param queryParams
	 *                    the query params
	 * @return the users
	 */
	public UserEntities getUsers(Map<String, String> queryParams) {
		return restClient.get("users", UserEntities.class, queryParams);
	}

	/**
	 * Gets the user.
	 *
	 * @param username
	 *                 the username
	 * @return the user
	 */
	public UserEntity getUser(String username) {
		return restClient.get("users/" + username, UserEntity.class, new HashMap<>());
	}

	/**
	 * Creates the user.
	 *
	 * @param userEntity
	 *                   the user entity
	 * @return the response
	 */
	public Response createUser(UserEntity userEntity) {
		return restClient.post("users", userEntity, new HashMap<>());
	}

	/**
	 * Update user.
	 *
	 * @param userEntity
	 *                   the user entity
	 * @return the response
	 */
	public Response updateUser(UserEntity userEntity) {
		return restClient.put("users/" + userEntity.getUsername(), userEntity, new HashMap<>());
	}

	/**
	 * Delete user.
	 *
	 * @param username
	 *                 the username
	 * @return the response
	 */
	public Response deleteUser(String username) {
		return restClient.delete("users/" + username, new HashMap<>());
	}

	/**
	 * Gets the chat rooms.
	 *
	 * @return the chat rooms
	 */
	public MUCRoomEntities getChatRooms() {
		return restClient.get("chatrooms", MUCRoomEntities.class, new HashMap<>());
	}

	/**
	 * Gets the chat rooms.
	 *
	 * @param queryParams
	 *                    the query params
	 * @return the chat rooms
	 */
	public MUCRoomEntities getChatRooms(Map<String, String> queryParams) {
		return restClient.get("chatrooms", MUCRoomEntities.class, queryParams);
	}

	/**
	 * Gets the chat room.
	 *
	 * @param roomName
	 *                 the room name
	 * @return the chat room
	 */
	public MUCRoomEntity getChatRoom(String roomName) {
		return restClient.get("chatrooms/" + roomName, MUCRoomEntity.class, new HashMap<>());
	}

	/**
	 * Creates the chat room.
	 *
	 * @param chatRoom
	 *                 the chat room
	 * @return the response
	 */
	public Response createChatRoom(MUCRoomEntity chatRoom) {
		return restClient.post("chatrooms", chatRoom, new HashMap<>());
	}

	/**
	 * Update chat room.
	 *
	 * @param chatRoom
	 *                 the chat room
	 * @return the response
	 */
	public Response updateChatRoom(MUCRoomEntity chatRoom) {
		return restClient.put("chatrooms/" + chatRoom.getRoomName(), chatRoom, new HashMap<>());
	}

	/**
	 * Delete chat room.
	 *
	 * @param roomName
	 *                 the room name
	 * @return the response
	 */
	public Response deleteChatRoom(String roomName) {
		return restClient.delete("chatrooms/" + roomName, new HashMap<>());
	}

	/**
	 * Gets the chat room participants.
	 *
	 * @param roomName
	 *                 the room name
	 * @return the chat room participants
	 */
	public ParticipantEntities getChatRoomParticipants(String roomName) {
		return restClient.get("chatrooms/" + roomName + "/participants", ParticipantEntities.class,
				new HashMap<>());
	}

	/**
	 * Adds the owner.
	 *
	 * @param roomName
	 *                 the room name
	 * @param jid
	 *                 the jid
	 * @return the response
	 */
	public Response addOwner(String roomName, String jid) {
		return restClient.post("chatrooms/" + roomName + "/owners/" + jid, null, new HashMap<>());
	}

	/**
	 * Delete owner from chatroom.
	 *
	 * @param roomName
	 *                 the room name
	 * @param jid
	 *                 the jid
	 * @return the response
	 */
	public Response deleteOwner(String roomName, String jid) {
		return restClient.delete("chatrooms/" + roomName + "/owners/" + jid,
				new HashMap<>());
	}

	/**
	 * Adds the admin.
	 *
	 * @param roomName
	 *                 the room name
	 * @param jid
	 *                 the jid
	 * @return the response
	 */
	public Response addAdmin(String roomName, String jid) {
		return restClient.post("chatrooms/" + roomName + "/admins/" + jid, null, new HashMap<>());
	}

	/**
	 * Delete admin from chatroom.
	 *
	 * @param roomName
	 *                 the room name
	 * @param jid
	 *                 the jid
	 * @return the response
	 */
	public Response deleteAdmin(String roomName, String jid) {
		return restClient.delete("chatrooms/" + roomName + "/admins/" + jid,
				new HashMap<>());
	}

	/**
	 * Adds the member.
	 *
	 * @param roomName
	 *                 the room name
	 * @param jid
	 *                 the jid
	 * @return the response
	 */
	public Response addMember(String roomName, String jid) {
		return restClient.post("chatrooms/" + roomName + "/members/" + jid, null, new HashMap<>());
	}

	/**
	 * Delete member from chatroom.
	 *
	 * @param roomName
	 *                 the room name
	 * @param jid
	 *                 the jid
	 * @return the response
	 */
	public Response deleteMember(String roomName, String jid) {
		return restClient.delete("chatrooms/" + roomName + "/members/" + jid,
				new HashMap<>());
	}

	/**
	 * Adds the outcast.
	 *
	 * @param roomName
	 *                 the room name
	 * @param jid
	 *                 the jid
	 * @return the response
	 */
	public Response addOutcast(String roomName, String jid) {
		return restClient.post("chatrooms/" + roomName + "/outcasts/" + jid, null, new HashMap<>());
	}

	/**
	 * Delete outcast from chatroom.
	 *
	 * @param roomName
	 *                 the room name
	 * @param jid
	 *                 the jid
	 * @return the response
	 */
	public Response deleteOutcast(String roomName, String jid) {
		return restClient.delete("chatrooms/" + roomName + "/outcasts/" + jid,
				new HashMap<>());
	}

	/**
	 * Adds the owner group.
	 *
	 * @param roomName
	 *                  the room name
	 * @param groupName
	 *                  the groupName
	 * @return the response
	 */
	public Response addOwnerGroup(String roomName, String groupName) {
		return restClient.post("chatrooms/" + roomName + "/owners/group/" + groupName, null,
				new HashMap<>());
	}

	/**
	 * Delete owner group from chatroom.
	 *
	 * @param roomName
	 *                  the room name
	 * @param groupName
	 *                  the groupName
	 * @return the response
	 */
	public Response deleteOwnerGroup(String roomName, String groupName) {
		return restClient.delete("chatrooms/" + roomName + "/owners/group/" + groupName,
				new HashMap<>());
	}

	/**
	 * Adds the group admin.
	 *
	 * @param roomName
	 *                  the room name
	 * @param groupName
	 *                  the groupName
	 * @return the response
	 */
	public Response addAdminGroup(String roomName, String groupName) {
		return restClient.post("chatrooms/" + roomName + "/admins/group/" + groupName, null,
				new HashMap<>());
	}

	/**
	 * Delete admin group from chatroom.
	 *
	 * @param roomName
	 *                  the room name
	 * @param groupName
	 *                  the groupName
	 * @return the response
	 */
	public Response deleteAdminGroup(String roomName, String groupName) {
		return restClient.delete("chatrooms/" + roomName + "/admins/group/" + groupName,
				new HashMap<>());
	}

	/**
	 * Adds the group member.
	 *
	 * @param roomName
	 *                  the room name
	 * @param groupName
	 *                  the groupName
	 * @return the response
	 */
	public Response addMemberGroup(String roomName, String groupName) {
		return restClient.post("chatrooms/" + roomName + "/members/group/" + groupName, null,
				new HashMap<>());
	}

	/**
	 * Delete member group from chatroom.
	 *
	 * @param roomName
	 *                  the room name
	 * @param groupName
	 *                  the groupName
	 * @return the response
	 */
	public Response deleteMemberGroup(String roomName, String groupName) {
		return restClient.delete("chatrooms/" + roomName + "/members/group/" + groupName,
				new HashMap<>());
	}

	/**
	 * Adds the group outcast.
	 *
	 * @param roomName
	 *                  the room name
	 * @param groupName
	 *                  the groupName
	 * @return the response
	 */
	public Response addOutcastGroup(String roomName, String groupName) {
		return restClient.post("chatrooms/" + roomName + "/outcasts/group/" + groupName, null,
				new HashMap<>());
	}

	/**
	 * Delete outcast group from chatroom.
	 *
	 * @param roomName
	 *                  the room name
	 * @param groupName
	 *                  the groupName
	 * @return the response
	 */
	public Response deleteOutcastGroup(String roomName, String groupName) {
		return restClient.delete("chatrooms/" + roomName + "/outcasts/group/" + groupName,
				new HashMap<>());
	}

	/**
	 * Gets the sessions.
	 *
	 * @return the sessions
	 */
	public SessionEntities getSessions() {
		return restClient.get("sessions", SessionEntities.class,
				new HashMap<>());
	}

	/**
	 * Gets the sessions.
	 *
	 * @param username
	 *                 the username
	 * @return the sessions
	 */
	public SessionEntities getSessions(String username) {
		return restClient.get("sessions/" + username, SessionEntities.class,
				new HashMap<>());
	}

	/**
	 * Close all user sessions.
	 *
	 * @param username the username
	 * @return the response
	 */
	public Response deleteSessions(String username) {
		return restClient.delete("sessions/" + username, new HashMap<>());
	}

	/**
	 * Gets the user groups.
	 *
	 * @param username
	 *                 the username
	 * @return the user groups
	 */
	public UserGroupsEntity getUserGroups(String username) {
		return restClient.get("users/" + username + "/groups", UserGroupsEntity.class,
				new HashMap<>());
	}

	/**
	 * Adds the user to groups.
	 *
	 * @param username
	 *                         the username
	 * @param userGroupsEntity
	 *                         the user groups entity
	 * @return the response
	 */
	public Response addUserToGroups(String username, UserGroupsEntity userGroupsEntity) {
		return restClient.post("users/" + username + "/groups/", userGroupsEntity,
				new HashMap<>());
	}

	/**
	 * Adds the user to group.
	 *
	 * @param username  the username
	 * @param groupName the group name
	 * @return the response
	 */
	public Response addUserToGroup(String username, String groupName) {
		return restClient.post("users/" + username + "/groups/" + groupName, null,
				new HashMap<>());
	}

	/**
	 * Delete user from group.
	 *
	 * @param username  the username
	 * @param groupName the group name
	 * @return the response
	 */
	public Response deleteUserFromGroup(String username, String groupName) {
		return restClient.delete("users/" + username + "/groups/" + groupName,
				new HashMap<>());
	}

	/**
	 * Lockout user.
	 *
	 * @param username
	 *                 the username
	 * @return the response
	 */
	public Response lockoutUser(String username) {
		return restClient.post("lockouts/" + username, null, new HashMap<>());
	}

	/**
	 * Unlock user.
	 *
	 * @param username
	 *                 the username
	 * @return the response
	 */
	public Response unlockUser(String username) {
		return restClient.delete("lockouts/" + username, new HashMap<>());
	}

	/**
	 * Gets the system properties.
	 *
	 * @return the system properties
	 */
	public SystemProperties getSystemProperties() {
		return restClient.get("system/properties", SystemProperties.class,
				new HashMap<>());
	}

	/**
	 * Gets the system property.
	 *
	 * @param propertyName
	 *                     the property name
	 * @return the system property
	 */
	public SystemProperty getSystemProperty(String propertyName) {
		return restClient.get("system/properties/" + propertyName, SystemProperty.class,
				new HashMap<>());
	}

	/**
	 * Creates the system property.
	 *
	 * @param property
	 *                 the property
	 * @return the response
	 */
	public Response createSystemProperty(SystemProperty property) {
		return restClient.post("system/properties", property, new HashMap<>());
	}

	/**
	 * Update system property.
	 *
	 * @param property
	 *                 the property
	 * @return the response
	 */
	public Response updateSystemProperty(SystemProperty property) {
		return restClient.put("system/properties/" + property.getKey(), property, new HashMap<>());
	}

	/**
	 * Delete system property.
	 *
	 * @param propertyName
	 *                     the property name
	 * @return the response
	 */
	public Response deleteSystemProperty(String propertyName) {
		return restClient.delete("system/properties/" + propertyName, new HashMap<>());
	}

	/**
	 * Gets the groups.
	 *
	 * @return the groups
	 */
	public GroupEntities getGroups() {
		return restClient.get("groups", GroupEntities.class, new HashMap<>());
	}

	/**
	 * Gets the group.
	 *
	 * @param groupName
	 *                  the group name
	 * @return the group
	 */
	public GroupEntity getGroup(String groupName) {
		return restClient.get("groups/" + groupName, GroupEntity.class, new HashMap<>());
	}

	/**
	 * Creates the group.
	 *
	 * @param group
	 *              the group
	 * @return the response
	 */
	public Response createGroup(GroupEntity group) {
		return restClient.post("groups", group, new HashMap<>());
	}

	/**
	 * Update group.
	 *
	 * @param group
	 *              the group
	 * @return the response
	 */
	public Response updateGroup(GroupEntity group) {
		return restClient.put("groups/" + group.getName(), group, new HashMap<>());
	}

	/**
	 * Delete group.
	 *
	 * @param groupName
	 *                  the group name
	 * @return the response
	 */
	public Response deleteGroup(String groupName) {
		return restClient.delete("groups/" + groupName, new HashMap<>());
	}

	/**
	 * Gets the roster.
	 *
	 * @param username
	 *                 the username
	 * @return the roster
	 */
	public RosterEntities getRoster(String username) {
		return restClient.get("users/" + username + "/roster", RosterEntities.class, new HashMap<>());
	}

	/**
	 * Adds the roster entry.
	 *
	 * @param username
	 *                         the username
	 * @param rosterItemEntity
	 *                         the roster item entity
	 * @return the response
	 */
	public Response addRosterEntry(String username, RosterItemEntity rosterItemEntity) {
		return restClient.post("users/" + username + "/roster", rosterItemEntity, new HashMap<>());
	}

	/**
	 * Update roster entry.
	 *
	 * @param username
	 *                         the username
	 * @param rosterItemEntity
	 *                         the roster item entity
	 * @return the response
	 */
	public Response updateRosterEntry(String username, RosterItemEntity rosterItemEntity) {
		return restClient.put("users/" + username + "/roster/" + rosterItemEntity.getJid(), rosterItemEntity,
				new HashMap<>());
	}

	/**
	 * Delete roster entry.
	 *
	 * @param username
	 *                 the username
	 * @param jid
	 *                 the jid
	 * @return the response
	 */
	public Response deleteRosterEntry(String username, String jid) {
		return restClient.delete("users/" + username + "/roster/" + jid, new HashMap<>());
	}

	/**
	 * Gets the rest client.
	 *
	 * @return the rest client
	 */
	public RestClient getRestClient() {
		return restClient;
	}

	/**
	 * Adjust URL.
	 *
	 * @param url the url
	 * @return the string
	 */
	private String adjustURL(String url) {
		if (!url.startsWith("http")) {
			url = "http://" + url;
		}
		return url;
	}

}
