package uk.co.fleabay.demo.analytics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class Log4jTest {
    private static final Logger logger = LogManager.getLogger(Log4jTest.class);

    @Test
    public void testLog4j() {
        logger.info("This is a test log message");
    }
}