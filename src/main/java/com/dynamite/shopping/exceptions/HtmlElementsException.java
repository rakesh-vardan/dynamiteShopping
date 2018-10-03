package com.dynamite.shopping.exceptions;

import org.apache.log4j.Logger;

public class HtmlElementsException extends RuntimeException {
   
	private static final long serialVersionUID = 1L;

    public static Logger log = Logger.getLogger("errorLogger");

	public HtmlElementsException() {
        super();
    }

    public HtmlElementsException(String message) {
        super(message);
        log.fatal(message);
    }

    public HtmlElementsException(String message, Throwable cause) {
        super(message, cause);
        log.fatal(message, cause);
    }

    public HtmlElementsException(Throwable cause) {
        super(cause);
        log.fatal(cause);
    }
}
