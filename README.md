# REST API Client [![Build Status](https://travis-ci.org/Redor/REST-API-Client.svg?branch=master)](https://travis-ci.org/Redor/REST-API-Client) [![Join the chat at https://gitter.im/Redor/REST-API-Client](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/Redor/REST-API-Client?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

REST API Client is an Java based Client for the [Openfire][1] [REST API Plugin][2] which provides the ability to manage Openfire instance by sending an REST/HTTP request to the server.

## Feature list
* [X] Get overview over all or specific user and to create, update or delete a user
* [X] Get overview over all or specific chat room and to create, update or delete a chat room
* [X] Get overview over all or specific user sessions
* [ ] Get all participants of a specified room
* [ ] Get overview over all or specific group and to create, update or delete a group
* [ ] Get overview over all user roster entries and to add, update or delete a roster entry
* [ ] Add user to a group and remove a user from a group
* [ ] Lockout or unlock the user (enable / disable)
* [ ] Get overview over all or specific system properties and to create, update or delete system property


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
  RestApiClient restApiClient = new restApiClient("http://testdomain.com", 9090, authenticationToken);

  // Request all available users
  restApiClient.getUsers();
  
  // Get specific user by username
  restApiClient.getUser("testUsername");

  // Search for the user with the username "test". This act like the wildcard search %String%
  HashMap<String, String> querys = new HashMap<String, String>();
  querys.put("search", "test");
  restApiClient.getUser(querys);
  
  // Create a new user (username, name, email, passowrd). There are more user settings available.
  UserEntity userEntity = new UserEntity("testUsername", "testName", "test@email.com", "p4ssw0rd");
  restApiClient.createUser(userEntity);
  
  // Update a user
  userEntity.setName("newName");
  restApiClient.updateUser(userEntity);
  
  // Delete a user
  restApiClient.deleteUser("testUsername");
```

### Chat rooms related examples
```java
  // Set Shared secret key
  AuthenticationToken authenticationToken = new AuthenticationToken("FQaCIpmRNBq4CfF8");
  // Set Openfire settings (9090 is the port of Openfire Admin Console)
  RestApiClient restApiClient = new restApiClient("http://testdomain.com", 9090, authenticationToken);

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
```

### Session related examples
```java
  // Set Shared secret key
  AuthenticationToken authenticationToken = new AuthenticationToken("FQaCIpmRNBq4CfF8");
  // Set Openfire settings (9090 is the port of Openfire Admin Console)
  RestApiClient restApiClient = new restApiClient("http://testdomain.com", 9090, authenticationToken);

  // Request all active Sessions
  restApiClient.getSessions();
  
  // Request all active Sessions from a specific user
  restApiClient.getSessions(String username);
```

## Copyright and license
Created and copyright (c) 2015 by Roman Soldatow.
REST API Client may be freely distributed under the Apache 2.0 license.

[1]: https://igniterealtime.org/projects/openfire/index.jsp
[2]: https://www.igniterealtime.org/projects/openfire/plugins/restapi/readme.html
[3]: https://www.igniterealtime.org/projects/openfire/plugins/restapi/readme.html#installation
[4]: https://www.igniterealtime.org/projects/openfire/plugins/restapi/readme.html#authentication
