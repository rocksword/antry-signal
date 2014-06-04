package com.an.antry.signal;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ServerHandler implements HttpHandler {
    private static final Log logger = LogFactory.getLog(ServerHandler.class);

    @Override
    public void handle(HttpExchange arg0) throws IOException {
        logger.info("req uri: " + arg0.getRequestURI().toString());
        logger.info("req method: " + arg0.getRequestMethod());
    }
}
