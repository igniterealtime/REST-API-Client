package org.igniterealtime.restclient;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.igniterealtime.restclient.entity.AuthenticationMode;
import org.igniterealtime.restclient.entity.AuthenticationToken;
import org.igniterealtime.restclient.exception.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * The Class RestClient.
 */
public final class RestClient {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(RestClient.class);

	/** The Constant METHOD_PUT. */
	public static final String METHOD_PUT = "PUT";

	/** The Constant METHOD_POST. */
	public static final String METHOD_POST = "POST";

	/** The uri. */
	private String baseURI;

	/** The token. */
	private AuthenticationToken token;

	/** The password. */
	private String password;

	/** The connection timeout. */
	private int connectionTimeout;

	/** The headers. */
	private Map<String, String> headers;

	/**
	 * Gets the.
	 *
	 * @param <T>
	 *            the generic type
	 * @param restPath
	 *            the rest path
	 * @param clazz
	 *            the clazz
	 * @param queryParams
	 *            the query params
	 * @return the t
	 */
	public <T> T get(String restPath, Class<T> clazz, Map<String, String> queryParams) {
		Builder builder = getRequestBuilder(restPath, queryParams);

		ClientResponse cr = null;
		try {
			cr = builder.get(ClientResponse.class);
		} catch (ClientHandlerException e) {
			LOG.error("Error", e);
		}

		// if the client response is expected back, stop here and return the
		// client response directly
		if (clazz.getName().equals(ClientResponse.class.getName())) {
			return (T) cr;
		}

		return (T) cr.getEntity(clazz);
	}

	/**
	 * Post.
	 *
	 * @param restPath
	 *            the rest path
	 * @param payload
	 *            the payload
	 * @param queryParams
	 *            the query params
	 * @return true, if successful
	 */
	public boolean post(String restPath, Object payload, Map<String, String> queryParams) {
		LOG.debug("POST: {}", restPath);
		return postOrPut(METHOD_POST, restPath, payload, queryParams);
	}

	/**
	 * Put.
	 *
	 * @param restPath
	 *            the rest path
	 * @param payload
	 *            the payload
	 * @param queryParams
	 *            the query params
	 * @return true, if successful
	 */
	public boolean put(String restPath, Object payload, Map<String, String> queryParams) {
		LOG.debug("PUT: {}", restPath);
		return postOrPut(METHOD_PUT, restPath, payload, queryParams);
	}

	/**
	 * Delete.
	 *
	 * @param restPath
	 *            the rest path
	 * @param queryParams
	 *            the query params
	 * @return true, if successful
	 */
	public boolean delete(String restPath, Map<String, String> queryParams) {
		LOG.debug("DELETE: {}", restPath);
		Builder builder = getRequestBuilder(restPath, queryParams);
		ClientResponse cr = null;
		try {
			cr = builder.delete(ClientResponse.class);
		} catch (ClientHandlerException e) {
			LOG.error("ClientHandlerException", e);
		}

		if (cr != null && isStatusCodeOK(cr, restPath)) {
			return true;
		}
		return false;
	}

	/**
	 * Post or put.
	 *
	 * @param method
	 *            the method
	 * @param restPath
	 *            the rest path
	 * @param payload
	 *            the payload
	 * @param queryParams
	 *            the query params
	 * @return true, if successful
	 */
	private boolean postOrPut(String method, String restPath, Object payload,
			Map<String, String> queryParams) {
		Builder builder = getRequestBuilder(restPath, queryParams);
		ClientResponse cr = null;
		try {
			if (method.equals(METHOD_PUT)) {
				cr = builder.put(ClientResponse.class, payload);
			} else if (method.equals(METHOD_POST)) {
				cr = builder.post(ClientResponse.class, payload);
			}
		} catch (ClientHandlerException e) {
			LOG.error("ClientHandlerException", e);
		}

		if (cr != null && isStatusCodeOK(cr, restPath)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if is status code ok.
	 *
	 * @param cr
	 *            the cr
	 * @param uri
	 *            the uri
	 * @return true, if checks if is status code ok
	 */
	private boolean isStatusCodeOK(ClientResponse cr, String uri) {
		if (cr.getStatus() == Status.OK.getStatusCode() || cr.getStatus() == Status.CREATED.getStatusCode()) {
			return true;
		} else if (cr.getStatus() == Status.UNAUTHORIZED.getStatusCode()) {
			LOG.error("UNAUTHORIZED: Your credentials are wrong. Please check your username/password or the secret key");
		} else if (cr.getStatus() == Status.CONFLICT.getStatusCode()
				|| cr.getStatus() == Status.NOT_FOUND.getStatusCode()
				|| cr.getStatus() == Status.FORBIDDEN.getStatusCode()
				|| cr.getStatus() == Status.BAD_REQUEST.getStatusCode()) {
			ErrorResponse errorResponse = (ErrorResponse) cr.getEntity(ErrorResponse.class);
			LOG.error("{} - {} on ressource {}", errorResponse.getException(), errorResponse.getMessage(),
					errorResponse.getRessource());
		} else {
			LOG.error("Unsupported status code: " + cr);
		}
		LOG.error(cr.toString());

		return false;
	}

	/**
	 * Gets the request builder.
	 *
	 * @param restPath
	 *            the rest path
	 * @param queryParams
	 *            the query params
	 * @return the request builder
	 */
	private Builder getRequestBuilder(String restPath, Map<String, String> queryParams) {
		// Convert query params to multivalued map for direct use with web
		// resource
		MultivaluedMapImpl queryParamsMVMap = new MultivaluedMapImpl();
		if (queryParams != null && !queryParams.isEmpty()) {
			for (Map.Entry<String, String> entry : queryParams.entrySet()) {
				if (entry.getKey() != null && entry.getValue() != null) {
					LOG.debug("PARAM: {} = {}", entry.getKey(), entry.getValue());
					queryParamsMVMap.add(entry.getKey(), entry.getValue());
				}
			}
		}

		try {
			URI u = new URI(this.baseURI + "/plugins/restapi/v1/" + restPath);
			Client client = createrRestClient();

			WebResource res = client.resource(u).queryParams(queryParamsMVMap);
			Builder requestBuilder = res.getRequestBuilder();

			for (Map.Entry<String, String> entry : this.headers.entrySet()) {
				LOG.debug("HEADER: {} = {}", entry.getKey(), entry.getValue());
				requestBuilder = res.getRequestBuilder().header(entry.getKey(), entry.getValue());
			}

			requestBuilder.accept(MediaType.APPLICATION_XML);
			return requestBuilder;
		} catch (Exception e) {
			LOG.error("Error", e);
			return null;
		}
	}

	/**
	 * The Constructor.
	 *
	 * @param builder
	 *            the builder
	 */
	private RestClient(RestClientBuilder builder) {
		this.baseURI = builder.baseURI;
		this.connectionTimeout = builder.connectionTimeout;
		this.setHeaders(builder.headers);
		this.token = builder.token;
	}

	/**
	 * Creater rest client.
	 *
	 * @return the client
	 * @throws KeyManagementException
	 *             the key management exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 */
	private Client createrRestClient() throws KeyManagementException, NoSuchAlgorithmException {
		ClientConfig clientConfig = createClientConfiguration();

		Client client = Client.create(clientConfig);

		// Set GZIP
		client.addFilter(new GZIPContentEncodingFilter(false));
		// Set Logging
		client.addFilter(new LoggingFilter());
		// Set connection timeout
		if (this.connectionTimeout != 0) {
			client.setConnectTimeout(this.connectionTimeout);
		}
		// Set HTTP BASIC AUTH
		if (token.getAuthMode() == AuthenticationMode.BASIC_AUTH) {
			client.addFilter(new HTTPBasicAuthFilter(token.getUsername(), token.getPassword()));
		}

		return client;
	}

	/**
	 * Creates the client configuration.
	 *
	 * @return the client config
	 * @throws KeyManagementException
	 *             the key management exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 */
	private ClientConfig createClientConfiguration() throws KeyManagementException, NoSuchAlgorithmException {
		ClientConfig clientConfig;

		if (this.baseURI.startsWith("https")) {
			clientConfig = createSLLClientConfig();
		} else {
			clientConfig = new DefaultClientConfig();
		}
		return clientConfig;
	}

	/**
	 * Creates the sll client config.
	 *
	 * @return the client config
	 * @throws KeyManagementException
	 *             the key management exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 */
	private ClientConfig createSLLClientConfig() throws KeyManagementException, NoSuchAlgorithmException {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(null, trustAllCerts, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		ClientConfig config = new DefaultClientConfig();
		config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES,
				new HTTPSProperties(new HostnameVerifier() {
					public boolean verify(String s, SSLSession sslSession) {
						return true;
					}
				}, sc));

		return config;
	}

	/**
	 * The Class Builder.
	 */
	public static class RestClientBuilder {

		/** The uri. */
		private String baseURI;

		/** The connection timeout. */
		private int connectionTimeout;

		/** The headers. */
		private Map<String, String> headers;

		/** The token. */
		private AuthenticationToken token;

		/**
		 * The Constructor.
		 *
		 * @param baseUri
		 *            the base uri
		 */
		public RestClientBuilder(String baseUri) {
			this.headers = new HashMap<String, String>();
			this.baseURI = baseUri;
		}

		/**
		 * Connection timeout.
		 *
		 * @param connectionTimeout
		 *            the connection timeout
		 * @return the builder
		 */
		public RestClientBuilder connectionTimeout(int connectionTimeout) {
			this.connectionTimeout = connectionTimeout;
			return this;
		}

		/**
		 * Authentication token.
		 *
		 * @param token
		 *            the token
		 * @return the rest client builder
		 */
		public RestClientBuilder authenticationToken(AuthenticationToken token) {
			if (token.getAuthMode() == AuthenticationMode.SHARED_SECRET_KEY) {
				headers.put(HttpHeaders.AUTHORIZATION, token.getSharedSecretKey());
			}
			this.token = token;
			return this;
		}

		/**
		 * Headers.
		 *
		 * @param headers
		 *            the headers
		 * @return the rest client builder
		 */
		public RestClientBuilder headers(Map<String, String> headers) {
			this.headers = headers;
			return this;
		}

		/**
		 * Builds the.
		 *
		 * @return the rest client resource
		 */
		public RestClient build() {
			return new RestClient(this);
		}

	}

	/**
	 * Gets the uri.
	 *
	 * @return the uri
	 */
	public String getUri() {
		return baseURI;
	}

	/**
	 * Sets the uri.
	 *
	 * @param uri
	 *            the new uri
	 */
	public void setUri(String uri) {
		this.baseURI = uri;
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
	 * Gets the connection timeout.
	 *
	 * @return the connection timeout
	 */
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * Sets the connection timeout.
	 *
	 * @param connectionTimeout
	 *            the new connection timeout
	 */
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	/**
	 * Gets the headers.
	 *
	 * @return the headers
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * Sets the headers.
	 *
	 * @param headers
	 *            the headers
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public AuthenticationToken getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token
	 *            the token
	 */
	public void setToken(AuthenticationToken token) {
		this.token = token;
	}

}
