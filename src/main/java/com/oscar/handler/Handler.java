package com.oscar.handler;

import com.oscar.dto.Store;
import com.oscar.helper.StoreHelper;
import com.oscar.repository.StoreRepository;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class Handler extends RequestHandler{
    private static StoreRepository storeRepository = new StoreRepository();
    public static void handleRequests(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        try {
            switch (requestMethod){
                case "GET":
                    handleGetRequests(exchange);
                    break;
                case "POST":
                    handlePostRequests(exchange);
                    break;
                case "PUT":
                    handlePutRequests(exchange);
                    break;
                case "DELETE":
                    handleDeleteRequests(exchange);
                    break;
                default:
                    System.out.println("Not method allowed");
                    returnResponse(exchange, 405, "This is the response for not allowed method ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleDeleteRequests(HttpExchange exchange) throws Exception {
        //printRequestInfo(exchange);
        boolean isRemoved=false;
        URI requestURI = exchange.getRequestURI();
        System.out.println("Delete method");
        System.out.println("Query:  "+requestURI.getQuery());
        StringBuilder del= new StringBuilder();
        if(requestURI.getQuery()!=null) {
            Map<String, String> queryMap = StoreHelper.queryToMap(requestURI.getQuery());
            isRemoved = storeRepository.deleteEntry(queryMap);
            del.append("removed");
        }else {
            System.out.println("No data to delete ");
            //System.out.println("List size with no deleted elements " + list.size());
        }
        returnResponse(exchange, isRemoved?200:204, isRemoved?del.toString():null);
    }

    public static void handleGetRequests(HttpExchange exchange) throws Exception {
        //printRequestInfo(exchange);
        List<Store> list = storeRepository.getStores();
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

    public static void handlePostRequests(HttpExchange exchange) throws Exception {
        //printRequestInfo(exchange);
        System.out.println("Post method");
        StringBuilder sb = new StringBuilder();
        InputStream ios = exchange.getRequestBody();
        int i;
        while ((i = ios.read()) != -1)
            sb.append((char) i);
        System.out.println("Body " + sb.toString());
        Store storeCreated =StoreHelper.saveData(sb.toString());
        storeRepository.create(storeCreated);
        returnResponse(exchange, 201, StoreHelper.fillStoreJson(storeCreated));
    }

    public static void handlePutRequests(HttpExchange exchange) throws Exception {
        //printRequestInfo(exchange);
        boolean recordFound=false;
        System.out.println("Put method");
        StringBuilder sbPut = new StringBuilder();
        InputStream iosPut = exchange.getRequestBody();
        int j;
        while ((j = iosPut.read()) != -1)
            sbPut.append((char) j);
        System.out.println("Body " + sbPut.toString());
        Store storeToUpdate =StoreHelper.saveData(sbPut.toString());
        storeRepository.update(storeToUpdate);

        returnResponse(exchange, recordFound?200:204, recordFound?StoreHelper.fillStoreJson(storeToUpdate):null);
    }
}
