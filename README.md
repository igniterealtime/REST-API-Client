# REST API Client [![Build Status](https://travis-ci.org/igniterealtime/REST-API-Client.svg?branch=master)](https://travis-ci.org/igniterealtime/REST-API-Client) [![Join the chat at https://gitter.im/igniterealtime/REST-API-Client](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/igniterealtime/REST-API-Client?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

REST API Client is an Java based Client for the [Openfire][1] [REST API Plugin][2] which provides the ability to manage Openfire instance by sending an REST/HTTP request to the server.

## Feature list
REST API Client cover all available REST API plugin features.

* [X] Get overview over all or specific user and to create, update or delete a user
* [X] Get overview over all or specific chat room and to create, update or delete a chat room
* [X] Get overview over all or specific user sessions
* [X] Get all participants of a specified room
* [X] Get overview over all or specific group and to create, update or delete a group
* [X] Get overview over all user roster entries and to add, update or delete a roster entry
* [X] Add user to a group
* [X] Remove user from a group
* [X] Lockout or unlock the user (enable / disable)
* [X] Get overview over all or specific system properties and to create, update or delete system property


## Repository
The project is available through the central Maven Repository
#### Maven
```xml
<dependency>
    <groupId>org.igniterealtime</groupId>
    <artifactId>rest-api-client</artifactId>
    <version>1.1.3</version>
</dependency>
```
#### Gradle / Grails
```xml
compile 'org.igniterealtime:rest-api-client:1.1.3'
```

## Dependencies
The REST API plugin need to be installed and configured on the Openfire server.

* [How to install REST API][3]
* [How to configure REST API][4]

## Examples

### Authentication
REST API Plugin provides two types of authentication.
* Basic HTTP Authentication
* Shared secret key

```java
  // Basic HTTP Authentication
  AuthenticationToken authenticationToken = new AuthenticationToken("admin", "testPassword");

  // Shared secret key
  AuthenticationToken authenticationToken = new AuthenticationToken("FQaCIpmRNBq4CfF8");
```

### User related examples
```java
  // Set Shared secret key
  AuthenticationToken authenticationToken = new AuthenticationToken("FQaCIpmRNBq4CfF8");
  // Set Openfire settings (9090 is the port of Openfire Admin Console)
  RestApiClient restApiClient = new RestApiClient("http://testdomain.com", 9090, authenticationToken);

  // Request all available users
  restApiClient.getUsers();

  // Get specific user by username
  restApiClient.getUser("testUsername");

  // Search for the user with the username "test". This act like the wildcard search %String%
  HashMap<String, String> querys = new HashMap<String, String>();
  querys.put("search", "test");
  restApiClient.getUsers(querys);

  // Create a new user (username, name, email, passowrd). There are more user settings available.
  UserEntity userEntity = new UserEntity("testUsername", "testName", "test@email.com", "p4ssw0rd");
  restApiClient.createUser(userEntity);

  // Update a user
  userEntity.setName("newName");
  restApiClient.updateUser(userEntity);

  // Delete a user
  restApiClient.deleteUser("testUsername");

  // Get all user groups from a user
  restApiCient.getUserGroups("testUsername");

  // Add user to groups
  List<String> groupNames = new ArrayList<String>();
  groupNames.add("Moderators");
  groupNames.add("Supporters");
  UserGroupsEntity userGroupsEntity = new UserGroupsEntity(groupNames);
  restApiClient.addUserToGroups("testUsername", userGroupsEntity);
  
  // Add user to group
  restApiClient.addUserToGroup("testUsername", "Moderators");
  
  // Delete user from a group
  restApiClient.deleteUserFromGroup("testUsername", "Moderators");

  // Lockout/Ban a user
  restApiClient.lockoutUser("testUsername");

  // Unlock/Unban a user
  restApiClient.unlockUser("testUsername");
```

### Chat rooms related examples
```java
  // Set Shared secret key
  AuthenticationToken authenticationToken = new AuthenticationToken("FQaCIpmRNBq4CfF8");
  // Set Openfire settings (9090 is the port of Openfire Admin Console)
  RestApiClient restApiClient = new RestApiClient("http://testdomain.com", 9090, authenticationToken);

  // Request all public chatrooms
  restApiClient.getChatRooms();

  // Search for the chat room with the room name "test". This act like the wildcard search %String%
  HashMap<String, String> querys = new HashMap<String, String>();
  querys.put("search", "test");
  restApiClient.getChatRooms(querys);

  // Create a new chat room (chatroom id, chatroom name, description). There are more chatroom settings available.
  MUCRoomEntity chatRoom = new MUCRoomEntity("chatroom1", "First Chat Room", "Some description");
  restApiClient.createChatRoom(chatRoom);

  // Update a chat room
  chatRoom.setDescription("Updated description");
  restApiClient.updateChatRoom(chatRoom);

  // Delete a chat room
  restApiClient.deleteChatRoom("chatroom1");

  // Add user with role "owner" to a chat room
  restApiClient.addOwner("chatroom1", "username");

  // Add user with role "admin" to a chat room
  restApiClient.addAdmin("chatroom1", "username");

  // Add user with role "member" to a chat room
  restApiClient.addMember("chatroom1", "username");

  // Add user with role "outcast" to a chat room
  restApiClient.addOutcast("chatroom1", "username");

  // Get all particapants from a specified chat room
  restApiClient.getChatRoomParticipants("chatroom1");
```

### Session related examples
```java
  // Set Shared secret key
  AuthenticationToken authenticationToken = new AuthenticationToken("FQaCIpmRNBq4CfF8");
  // Set Openfire settings (9090 is the port of Openfire Admin Console)
  RestApiClient restApiClient = new RestApiClient("http://testdomain.com", 9090, authenticationToken);

  // Request all active Sessions
  restApiClient.getSessions();

  // Request all active Sessions from a specific user
  restApiClient.getSessions(String username);
```

### System related examples
```java
  // Set Shared secret key
  AuthenticationToken authenticationToken = new AuthenticationToken("FQaCIpmRNBq4CfF8");
  // Set Openfire settings (9090 is the port of Openfire Admin Console)
  RestApiClient restApiClient = new RestApiClient("http://testdomain.com", 9090, authenticationToken);

  // Retrieve all system properties
  restApiClient.getSystemProperties();

  // Retrieve specific system property e.g. "xmpp.domain"
  restApiClient.getSystemProperty("xmpp.domain");

  // Create a system property
  SystemProperty systemProperty = new SystemProperty("propertyName", "propertyValue");
  restApiClient.createSystemProperty(systemProperty);

  // Update a system property
  SystemProperty systemProperty = new SystemProperty("propertyName", "ChangedPropertyValue");
  restApiClient.updateSystemProperty(systemProperty);

  // Delete a system property
  restApiClient.deleteSystemProperty("propertyName");
```

### Group related examples
```java
  // Set Shared secret key
  AuthenticationToken authenticationToken = new AuthenticationToken("FQaCIpmRNBq4CfF8");
  // Set Openfire settings (9090 is the port of Openfire Admin Console)
  RestApiClient restApiClient = new RestApiClient("http://testdomain.com", 9090, authenticationToken);

  // Retrieve all groups
  restApiClient.getGroups();

  // Retrieve specific group
  restApiClient.getGroup("Moderators");

  // Create a group
  GroupEntity groupEntity = new GroupEntity("Moderators", "Moderator Group");
  restApiClient.createGroup(groupEntity);

  // Update a group
  GroupEntity groupEntity = new GroupEntity("Moderators", "Changed Moderator Group description");
  restApiClient.updateGroup(groupEntity);

  // Delete a group
  restApiClient.deleteGroup("Moderators");
```

### Roster related examples
```java
  // Set Shared secret key
  AuthenticationToken authenticationToken = new AuthenticationToken("FQaCIpmRNBq4CfF8");
  // Set Openfire settings (9090 is the port of Openfire Admin Console)
  RestApiClient restApiClient = new RestApiClient("http://testdomain.com", 9090, authenticationToken);

  // Retrieve user roster
  restApiClient.getRoster("testUsername");

  // Create a user roster entry (Possible values for subscriptionType are: -1 (remove), 0 (none), 1 (to), 2 (from), 3 (both))
  RosterItemEntity rosterItemEntity = new RosterItemEntity("testUser2@testdomain.com", "TestUser2", 3);
  // Groups are optional
  List<String> groups = new ArrayList<String>();
  groups.add("Supporter");
  rosterItemEntity.setGroups(groups);
  restApiClient.addRosterEntry("testUsername", rosterItemEntity);

  // Update a user roster entry
  RosterItemEntity rosterItemEntity = new RosterItemEntity("testUser2@testdomain.com", "SomeUser", 3);
  restApiClient.updateRosterEntry("testUsername", rosterItemEntity);

  // Delete a user roster entry
  restApiClient.deleteRosterEntry("testUsername", "testUser2@testdomain.com");
```

## Copyright and license
Created and copyright (c) 2015 by Roman Soldatow (roman@soldatow.de).
REST API Client may be freely distributed under the Apache 2.0 license.

[1]: https://igniterealtime.org/projects/openfire/index.jsp
[2]: https://www.igniterealtime.org/projects/openfire/plugins/restapi/readme.html
[3]: https://www.igniterealtime.org/projects/openfire/plugins/restapi/readme.html#installation
[4]: https://www.igniterealtime.org/projects/openfire/plugins/restapi/readme.html#authentication
