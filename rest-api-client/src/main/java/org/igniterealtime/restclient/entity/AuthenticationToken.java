package org.igniterealtime.restclient.entity;

/**
 * The Class AuthenticationToken.
 */
public class AuthenticationToken {

	/** The auth mode. */
	private AuthenticationMode authMode;

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	/** The shared secret key. */
	private String sharedSecretKey;

	/**
	 * Instantiates a new authentication token.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 */
	public AuthenticationToken(String username, String password) {
		this.authMode = AuthenticationMode.BASIC_AUTH;
		this.username = username;
		this.password = password;
	}

	/**
	 * Instantiates a new authentication token.
	 *
	 * @param sharedSecretKey
	 *            the shared secret key
	 */
	public AuthenticationToken(String sharedSecretKey) {
		this.authMode = AuthenticationMode.SHARED_SECRET_KEY;
		this.sharedSecretKey = sharedSecretKey;
	}

	/**
	 * Gets the auth mode.
	 *
	 * @return the auth mode
	 */
	public AuthenticationMode getAuthMode() {
		return authMode;
	}

	/**
	 * Sets the auth mode.
	 *
	 * @param authMode
	 *            the new auth mode
	 */
	public void setAuthMode(AuthenticationMode authMode) {
		this.authMode = authMode;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username
	 *            the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the shared secret key.
	 *
	 * @return the shared secret key
	 */
	public String getSharedSecretKey() {
		return sharedSecretKey;
	}

	/**
	 * Sets the shared secret key.
	 *
	 * @param sharedSecretKey
	 *            the new shared secret key
	 */
	public void setSharedSecretKey(String sharedSecretKey) {
		this.sharedSecretKey = sharedSecretKey;
	}

}
