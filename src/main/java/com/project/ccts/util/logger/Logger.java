package com.project.ccts.util.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Singleton class for logs.
 */
public class Logger {
    private static Logger logger = new Logger();
    private Log log;

    private Logger() { }

    /**
     *
     * @return Logger - the instance of the singleton
     */
    public static Logger getInstance() {
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
