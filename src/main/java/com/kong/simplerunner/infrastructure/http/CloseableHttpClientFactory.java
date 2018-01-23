package com.kong.simplerunner.infrastructure.http;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class CloseableHttpClientFactory {
	
	private static final CloseableHttpClientFactory INSTANCE = new CloseableHttpClientFactory();
	
	private final int connectTimeout = 2000;
	private final int socketTimeout = 3000;
	private final int maxTotal = 50;
	private final int defaultMaxPerRoute = 10;
	
	public static CloseableHttpClientFactory getInstance() {
		return INSTANCE;
	}
	
	public CloseableHttpClient create(String proxyHost, Integer proxyPort) {
		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
				.setConnectTimeout(connectTimeout)
				.setConnectionRequestTimeout(connectTimeout)
				.setSocketTimeout(socketTimeout);
		
		if (proxyHost != null && proxyPort != null) {
			requestConfigBuilder.setProxy(new HttpHost(proxyHost, proxyPort));
		}
		
		CloseableHttpClient httpClient = HttpClients.custom().disableContentCompression()
				.setConnectionManager(getConnectionManager())
				.setDefaultRequestConfig(requestConfigBuilder.build())
				.build();
		return httpClient;
	}
	
	public HttpClientConnectionManager getConnectionManager() {
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", SSLConnectionSocketFactory.getSocketFactory())
				.register("http", PlainConnectionSocketFactory.getSocketFactory()).build();
		
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		connectionManager.setMaxTotal(maxTotal);
		connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
		return connectionManager;
	}
}
