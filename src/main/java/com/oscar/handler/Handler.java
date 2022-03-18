package com.oscar.handler;

import com.oscar.context.ApplicationContext;
import com.oscar.context.annotations.Component;
import com.oscar.controller.StoreController;
import com.oscar.dto.Store;
import com.oscar.helper.StoreHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;
import java.util.Map;
@Component
public class Handler implements HttpHandler {

    private Map<String, Class<?>> controllersPath;
    private Map<String, Method> controllerMethods;
    private Map<String, Object> map;

    public Handler(){
        controllersPath = ApplicationContext.getMappingControllers();
        controllerMethods = ApplicationContext.getMappingMethods();
    }

    public void handle(HttpExchange exchange) {
        try {
            URI requestURI = exchange.getRequestURI();
            Class<?> controllerClass = controllersPath.get(requestURI.toString());
            Object controllerObject = ApplicationContext.getBean(controllerClass);
            Method method = controllerMethods.get(requestURI.toString());
            method.invoke(controllerObject, null);
            String requestMethod = exchange.getRequestMethod();
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

    public void handleDeleteRequests(HttpExchange exchange) throws Exception {
        //printRequestInfo(exchange);
        boolean isRemoved=false;
        URI requestURI = exchange.getRequestURI();
        System.out.println("Delete method");
        System.out.println("Query:  "+requestURI.getQuery());
        StringBuilder del= new StringBuilder();
        if(requestURI.getQuery()!=null) {
            Map<String, String> queryMap = StoreHelper.queryToMap(requestURI.getQuery());
            //storeRepository.delete(0);
            isRemoved = true;
            del.append("removed");
        }else {
            System.out.println("No data to delete ");
            //System.out.println("List size with no deleted elements " + list.size());
        }
        returnResponse(exchange, isRemoved?200:204, isRemoved?del.toString():null);
    }

    public void handleGetRequests(HttpExchange exchange) throws Exception {
        //printRequestInfo(exchange);
        StoreController storeController = (StoreController)map.get("/stores");
        List<Store> list = storeController.getAllStores();
        URI requestURI = exchange.getRequestURI();
        String response = StoreHelper.fillStoresJson(list);
        returnResponse(exchange, 200, response);
    }

    public void handlePostRequests(HttpExchange exchange) throws Exception {
        //printRequestInfo(exchange);
        System.out.println("Post method");
        StringBuilder sb = new StringBuilder();
        InputStream ios = exchange.getRequestBody();
        int i;
        while ((i = ios.read()) != -1)
            sb.append((char) i);
        System.out.println("Body " + sb.toString());
        Store storeCreated =StoreHelper.saveData(sb.toString());
        StoreController storeController = (StoreController)map.get("/stores");
        storeController.createStore(storeCreated);
        returnResponse(exchange, 201, StoreHelper.fillStoreJson(storeCreated));
    }

    public void handlePutRequests(HttpExchange exchange) throws Exception {
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
        StoreController storeController = (StoreController)map.get("/stores");
        storeController.updateStore(storeToUpdate);

        returnResponse(exchange, recordFound?200:204, recordFound?StoreHelper.fillStoreJson(storeToUpdate):null);
    }

    public void returnResponse(HttpExchange httpExchange, int responseCode, String result) throws IOException {
        int responseLength = result != null ? result.length() : -1;
        httpExchange.sendResponseHeaders(responseCode, responseLength);
        OutputStream outputStream = httpExchange.getResponseBody();
        if(204 != responseCode)
            outputStream.write(result.getBytes());
        outputStream.close();
    }
}
