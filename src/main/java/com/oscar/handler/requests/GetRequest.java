package com.oscar.handler.requests;

import com.oscar.dto.Store;
import com.oscar.handler.RequestHandler;
import com.oscar.helper.StoreHelper;
import com.sun.net.httpserver.HttpExchange;

import java.net.URI;
import java.util.List;
import java.util.Map;

public class GetRequest extends RequestHandler {

    public static void handleGetRequests(HttpExchange exchange, List<Store> list) throws Exception {
        //printRequestInfo(exchange);
        URI requestURI = exchange.getRequestURI();
        String response = "";
        System.out.println("Get method");
        System.out.println("Query:  " + requestURI.getQuery());
        if (requestURI.getQuery() != null) {
            Map<String, String> queryMap = StoreHelper.queryToMap(requestURI.getQuery());
            System.out.println("Query map size " + queryMap.size());
            response = StoreHelper.getObjectFilteredJson(queryMap, list);
        } else {
            System.out.println("List size " + list.size());
            response = StoreHelper.fillStoresJson(list);
        }
        returnResponse(exchange, response!=null?200:204, response);
    }

}
