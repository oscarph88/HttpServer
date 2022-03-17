package com.oscar.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class RequestHandler {
    protected static void returnResponse(HttpExchange httpExchange, int responseCode, String result) throws IOException {
        int responseLength = result != null ? result.length() : -1;
        httpExchange.sendResponseHeaders(responseCode, responseLength);
        OutputStream outputStream = httpExchange.getResponseBody();
        if(204 != responseCode)
            outputStream.write(result.getBytes());
        outputStream.close();
    }

    public static void printRequestInfo(HttpExchange exchange) {
        System.out.println("-- headers --");
        Headers requestHeaders = exchange.getRequestHeaders();
        requestHeaders.entrySet().forEach(System.out::println);

        System.out.println("-- principle --");
        HttpPrincipal principal = exchange.getPrincipal();
        System.out.println(principal);

        System.out.println("-- HTTP method --");
        String requestMethod = exchange.getRequestMethod();
        System.out.println(requestMethod);

        System.out.println("-- query --");
        URI requestURI = exchange.getRequestURI();
        String query = requestURI.getQuery();
        System.out.println(query);
    }
}
