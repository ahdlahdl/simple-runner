package com.kong.simplerunner;

import com.kong.simplerunner.service.RestClientRunner;
import com.kong.simplerunner.service.SimpleRunner;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
	private static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws Exception {
		logger.info("args : " + ArrayUtils.toString(args));
		
		SimpleRunner runner = new RestClientRunner();
		runner.execute(args);
    }
}