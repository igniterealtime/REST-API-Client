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

import org.igniterealtime.restclient.entity.AuthenticationMode;
import org.igniterealtime.restclient.entity.AuthenticationToken;
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

	public <Token> Token get(String restPath, Class<Token> clazz, Map<String, String> queryParams) {
		Builder builder = getRequestBuilder(restPath);

		// Convert query params to multivalued map for direct use with web
		// resource
		MultivaluedMapImpl queryParamsMVMap = new MultivaluedMapImpl();
		if (queryParams != null && !queryParams.isEmpty()) {
			for (Map.Entry<String, String> entry : queryParams.entrySet()) {
				if (entry.getKey() != null && entry.getValue() != null) {
					LOG.debug("PARAM: {} = {}", entry.getKey(),
							entry.getValue());
					queryParamsMVMap.add(entry.getKey(), entry.getValue());
				}
			}
		}

		ClientResponse cr = null;
		try {
			cr = builder.get(ClientResponse.class);
		} catch (ClientHandlerException e) {
			LOG.error("Error", e);
		}

		// if the client response is expected back, stop here and return the
		// client response directly
		if (clazz.getName().equals(ClientResponse.class.getName())) {
			return (Token) cr;
		}

		return (Token) cr.getEntity(clazz);
	}

	/**
	 * Gets the web resource.
	 * 
	 * @param queryParams
	 *
	 * @return the web resource
	 */
	private Builder getRequestBuilder(String restPath) {
		try {

			URI u = new URI(this.baseURI + "/plugins/restapi/v1/" + restPath);
			Client client = createrRestClient();

			WebResource res = client.resource(u);
			Builder requestBuilder = res.getRequestBuilder();

			for (Map.Entry<String, String> entry : this.headers.entrySet()) {
				LOG.error("SET HEADER: " + entry.getKey() + " - " + entry.getValue());
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
	private Client createrRestClient() throws KeyManagementException,
			NoSuchAlgorithmException {
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
			client.addFilter(new HTTPBasicAuthFilter(token.getUsername(),
					token.getPassword()));
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
	private ClientConfig createClientConfiguration()
			throws KeyManagementException, NoSuchAlgorithmException {
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
	private ClientConfig createSLLClientConfig() throws KeyManagementException,
			NoSuchAlgorithmException {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs,
					String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs,
					String authType) {
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

	public AuthenticationToken getToken() {
		return token;
	}

	public void setToken(AuthenticationToken token) {
		this.token = token;
	}

}
