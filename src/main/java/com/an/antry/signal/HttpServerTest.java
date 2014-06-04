package com.an.antry.signal;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.Signal;
import sun.misc.SignalHandler;

import com.sun.net.httpserver.HttpServer;

//http://tessykandy.iteye.com/blog/2005767
public class HttpServerTest implements SignalHandler {
    private static final Log logger = LogFactory.getLog(HttpServerTest.class);

    private HttpServer server;
    private ExecutorService httpThreadPool;

    @Override
    public void handle(Signal sn) {
        logger.info("Signal [" + sn.getName() + "] is received, stopServer soon...");
        stopServer();
        logger.info("Stop successfully.");
    }

    public static void main(String[] args) {
        logger.info("main");
        HttpServerTest main = new HttpServerTest();
        // 捕捉USR2信号
        Signal.handle(new Signal("TERM"), main);
        Signal.handle(new Signal("INT"), main);
        Signal.handle(new Signal("USR2"), main);
        main.startServer();
    }

    public void startServer() {
        logger.info("startServer");
        int port = 5555;
        String context = "/KillTest";
        int maxConnections = 50;
        try {
            InetSocketAddress addr = new InetSocketAddress(port);
            server = HttpServer.create(addr, maxConnections);
            server.createContext(context, new ServerHandler());
            httpThreadPool = Executors.newCachedThreadPool();
            server.setExecutor(httpThreadPool);
            server.start();
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * 安全的关闭HttpServer监听服务
     */
    private void stopServer() {
        logger.info("stopServer");
        server.stop(1);
        httpThreadPool.shutdown();
    }
}
