package com.kong.simplerunner.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSoupClientRunner implements SimpleRunner {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void execute(String[] args) throws Exception {
		Document document = Jsoup.connect("https://www.daum.net/").get();
		Elements elements = document.select(".tit_item");
		logger.debug("{}", elements.text());
	}
}
