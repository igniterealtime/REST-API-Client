# REST API Client
REST API Client is an Java based Client for the [Openfire][1] [REST API][2] Plugin which provides the ability to manage Openfire instance by sending an REST/HTTP request to the server.

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

* How to install REST API: https://www.igniterealtime.org/projects/openfire/plugins/restapi/readme.html#installation
* How to configure REST API: https://www.igniterealtime.org/projects/openfire/plugins/restapi/readme.html#authentication

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

[1]: https://igniterealtime.org/projects/openfire/index.jsp
[2]: https://www.igniterealtime.org/projects/openfire/plugins/restapi/readme.html
