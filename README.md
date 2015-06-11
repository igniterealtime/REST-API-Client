# REST API Client
REST API Client is an Java based Client for the [Openfire][1] [REST API Plugin][2] which provides the ability to manage Openfire instance by sending an REST/HTTP request to the server.

## Feature list
* Get overview over all or specific user and to create, update or delete a user
* Get overview over all or specific group and to create, update or delete a group
* Get overview over all user roster entries and to add, update or delete a roster entry
* Add user to a group and remove a user from a group
* Lockout or unlock the user (enable / disable)
* Get overview over all or specific system properties and to create, update or delete system property
* Get overview over all or specific chat room and to create, update or delete a chat room

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

### Get all or filtered users
```java
  // Set Shared secret key
  AuthenticationToken authenticationToken = new AuthenticationToken("FQaCIpmRNBq4CfF8");
  // Set Openfire settings (9090 is the port of Openfire Admin Console)
  RestApiCient restApiCient = new RestApiCient("http://testdomain.com", 9090, authenticationToken);
  
  // Request all available users
  restApiCient.getUsers();
  
  // Search for the user with the username "test". This act like the wildcard search %String%
  restApiCient.getFilteredUsers("test");
```


[1]: https://igniterealtime.org/projects/openfire/index.jsp
[2]: https://www.igniterealtime.org/projects/openfire/plugins/restapi/readme.html
[3]: https://www.igniterealtime.org/projects/openfire/plugins/restapi/readme.html#installation
[4]: https://www.igniterealtime.org/projects/openfire/plugins/restapi/readme.html#authentication
