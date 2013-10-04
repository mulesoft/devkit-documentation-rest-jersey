/**
 * This file was automatically generated by the Mule Development Kit
 */
package org.mule.examples.restjerseyconnector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.lifecycle.Start;
import org.mule.api.annotations.oauth.OAuth;
import org.mule.api.annotations.oauth.OAuthAccessToken;
import org.mule.api.annotations.oauth.OAuthAccessTokenSecret;
import org.mule.api.annotations.oauth.OAuthConsumerKey;
import org.mule.api.annotations.oauth.OAuthConsumerSecret;
import org.mule.api.annotations.oauth.OAuthProtected;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.examples.restjerseyconnector.client.DropboxClient;
import org.mule.examples.restjerseyconnector.entities.AccountInfo;
import org.mule.examples.restjerseyconnector.exception.RestJerseyConnectorException;
import org.mule.examples.restjerseyconnector.exception.RestJerseyConnectorTokenExpiredException;

/**
 * Cloud Connector
 * 
 * @author MuleSoft, Inc.
 */
@Connector(name = "rest-jersey", schemaVersion = "1.0", friendlyName = "REST Jersey Example")
@OAuth(
		requestTokenUrl = "https://api.dropbox.com/1/oauth/request_token", 
		accessTokenUrl = "https://api.dropbox.com/1/oauth/access_token", 
		authorizationUrl = "https://www.dropbox.com/1/oauth/authorize",
		verifierRegex = "oauth_token=([^&]+)")

// If an Exception with message "OAuth authorization code could not be extracted from:" is thrown after authenticate, check the parameter returned in the response
// By default is looking for a "oauth_verifier=([^&]+)" verifierRegex
// Example of one of my responses "http://localhost:8090/callbackresponse?uid=SOME_ID&oauth_token=SOME_TOKEN"
public class RestJerseyConnector {

	static final private Log logger = LogFactory.getLog(RestJerseyConnector.class);

    /**
     * Dropbox API Url
     */
    @Configurable
    @Optional
    @Default("https://api.dropbox.com")
    private String apiUrl;

    /**
     * Dropbox API version
     */
    @Configurable
    @Optional
    @Default("1")
    private String apiVersion;

	/**
	 * The ApiKey
	 */
	@Configurable
	@OAuthConsumerKey
	private String consumerKey;

	/**
	 * The consumerSecret
	 */
	@Configurable
	@OAuthConsumerSecret
	private String consumerSecret;
	
	@OAuthAccessToken
	private String accessToken;
	
	@OAuthAccessTokenSecret
	private String accessTokenSecret;
	
	private DropboxClient client;

    @Start
    public void init() {
        setClient(new DropboxClient(this));
    }

	/**
	 * Logs as INFO the OAuthAccessToken & OAuthAccessTokenSecret
	 * 
	 * {@sample.xml ../../../doc/rest-jersey-connector.xml.sample rest-jersey:log-info}
	 */
	@OAuthProtected
	@Processor
	public void logInfo() {
		logger.info(String.format("OAuthAccessToken=%s", getAccessToken()));
		logger.info(String.format("OAuthAccessTokenSecret=%s", getAccessTokenSecret()));
	}
	
	/**
	 * Returns the Account Information of the user
	 * 
	 * {@sample.xml ../../../doc/rest-jersey-connector.xml.sample rest-jersey:get-account-info}
	 * 
	 * @return The AccountInfo
	 * @throws org.mule.examples.restjerseyconnector.exception.RestJerseyConnectorException If the response is an error or the response cannot be parsed as an AccountInfo
	 * @throws org.mule.examples.restjerseyconnector.exception.RestJerseyConnectorTokenExpiredException If the current token used for the call to the service is no longer valid
	 */
	@OAuthProtected
	@Processor
	public AccountInfo getAccountInfo() throws RestJerseyConnectorException, RestJerseyConnectorTokenExpiredException {
		return getClient().getAccountInfo();
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

    public static Log getLogger() {
        return logger;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public DropboxClient getClient() {
        return client;
    }

    public void setClient(DropboxClient client) {
        this.client = client;
    }
}