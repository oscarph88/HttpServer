package com.oscar.handler.requests;

import com.oscar.dto.Store;
import com.oscar.handler.RequestHandler;
import com.oscar.helper.StoreHelper;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class PostRequest extends RequestHandler {
    public static void handlePostRequests(HttpExchange exchange, List<Store> list) throws Exception {
        //printRequestInfo(exchange);
        System.out.println("Post method");
        StringBuilder sb = new StringBuilder();
        InputStream ios = exchange.getRequestBody();
        int i;
        while ((i = ios.read()) != -1)
            sb.append((char) i);
        System.out.println("Body " + sb.toString());
        Store storeCreated =StoreHelper.saveData(sb.toString());
        list.add(storeCreated);
        System.out.println("New size save "+list.size());
        returnResponse(exchange, 201, StoreHelper.fillStoreJson(storeCreated));
    }
}
