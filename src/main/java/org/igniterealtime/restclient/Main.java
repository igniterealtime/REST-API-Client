package org.igniterealtime.restclient;

import org.igniterealtime.restclient.entity.AuthenticationToken;

public class Main {
	public static void main(String[] args) {
		AuthenticationToken authenticationToken = new AuthenticationToken("admin", "meineoma");
		// AuthenticationToken authenticationToken = new
		// AuthenticationToken("FQaCIpmRNBq4CfF8");
		RestApiCient restApiCient = new RestApiCient("http://10.26.5.169", 9090, authenticationToken);
		restApiCient.getUsers();
	}
}
