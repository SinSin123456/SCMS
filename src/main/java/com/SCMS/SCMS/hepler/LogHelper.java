package com.SCMS.SCMS.hepler;

import org.slf4j.Logger;

public final class LogHelper {
    public static void info(Logger logger, String msg) {
        logger.info(msg);
    }
    
    public static void err(Logger logger, String msg) {
        logger.error(msg);
    }
}
