package com.oscar.httpServer;

import com.oscar.handler.Handler;
import com.sun.net.httpserver.HttpContext;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServer {

    public static void main(String[] args) throws IOException {
        com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(8500), 0);
        HttpContext context = server.createContext("/example");
        context.setHandler(Handler::handleRequests);
        server.start();
    }

}
