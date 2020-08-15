package com.connor.taotie.log;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Log4jTest {

    @Test
    public void testLog4j() {
        // default file name : log4j2.xml
        Logger logger = LoggerFactory.getLogger(Log4jTest.class);
        logger.debug("hello");
        logger.info("hello-debug");
    }

}
