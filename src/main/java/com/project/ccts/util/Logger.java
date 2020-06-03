package com.project.ccts.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Singleton class for logs.
 */
public class Logger {
    private static Logger logger;
    private Log log;

    private Logger() { }

    /**
     *
     * @return Logger - the instance of the singleton
     */
    public static Logger getInstance() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    /**
     *
     * @param type class type that going to the log
     * @return Log object
     */
    public Log getLog(Class<?> type) {
        log = LogFactory.getLog(type);
        return log;
    }
}
