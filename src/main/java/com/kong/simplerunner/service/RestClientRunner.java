package com.kong.simplerunner.service;

import com.kong.simplerunner.infrastructure.http.KongHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClientRunner implements SimpleRunner {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void execute(String[] args) throws Exception {
		String proxyHost = null;
		Integer proxyPort = null;
		
		KongHttpClient client = new KongHttpClient(proxyHost, proxyPort);
		HttpRequestBase http = new HttpGet("https://google.com");
	
		logger.info("### result : " + client.execute(http));
    }
}