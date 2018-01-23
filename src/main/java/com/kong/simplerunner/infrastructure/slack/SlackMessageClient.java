package com.kong.simplerunner.infrastructure.slack;

import com.kong.simplerunner.infrastructure.http.KongHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlackMessageClient {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * https://slack.com/apps/A0F81R8ET-slackbot
	 * using slackbot api
	 * add configuration into your slack.
	 * @param teamName
	 * @param token
	 * @param channel
	 * @param message
	 */
	public void sendMessage(String teamName, String token, String channel, String message) {
		KongHttpClient httpClient = new KongHttpClient();
		String url = String.format("https://%s.slack.com/services/hooks/slackbot?token=%s&channel=%%23%s", teamName, token, channel);
		HttpPost http = new HttpPost(url);
		http.addHeader("Content-type", "apllication/json");
		StringEntity body = new StringEntity(message, "UTF-8");
		http.setEntity(body);
		KongHttpClient.HttpResult response = httpClient.execute(http);
		logger.debug("{}", response);
	}
}