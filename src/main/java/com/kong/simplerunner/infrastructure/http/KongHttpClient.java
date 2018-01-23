package com.kong.simplerunner.infrastructure.http;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KongHttpClient {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public KongHttpClient(String proxyHost, Integer proxyPort) {
		httpClient = CloseableHttpClientFactory.getInstance().create(proxyHost, proxyPort);
	}
	
	protected CloseableHttpClient httpClient;
	
	public HttpResult execute(HttpRequestBase httpRequestBase) {
		try {
			logger.debug("uri : {}", httpRequestBase.getURI());
			logger.debug("method : {}", httpRequestBase.getMethod());
			logger.debug("headers : {}", ArrayUtils.toString(httpRequestBase.getAllHeaders()));
			HttpResponse httpResponse = httpClient.execute(httpRequestBase);
			return new HttpResult(httpResponse);
		} catch (Exception e) {
			logger.error("{}", e);
		} finally {
			httpRequestBase.releaseConnection();
		}
		
		return null;
	}
	
	public class HttpResult {
		public String body;
		public int statusCode;
		
		public HttpResult(HttpResponse httpResponse) {
			statusCode = httpResponse.getStatusLine().getStatusCode();
			try {
				body = EntityUtils.toString(httpResponse.getEntity());
			} catch (Exception e) {
				logger.error("{}", e);
			}
		}
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}
	}
}
