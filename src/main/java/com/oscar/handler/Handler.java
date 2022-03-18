package com.oscar.handler;

import com.oscar.dto.Store;
import com.oscar.handler.requests.DeleteRequest;
import com.oscar.handler.requests.GetRequest;
import com.oscar.handler.requests.PostRequest;
import com.oscar.handler.requests.PutRequest;
import com.oscar.helper.StoreHelper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;

public class Handler extends RequestHandler{
    static List<Store> list=StoreHelper.fillStores();
    public static void handleRequests(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        try {
            switch (requestMethod){
                case "GET":
                    GetRequest.handleGetRequests(exchange, list);
                    break;
                case "POST":
                    PostRequest.handlePostRequests(exchange,list);
                    break;
                case "PUT":
                    PutRequest.handlePutRequests(exchange, list);
                    break;
                case "DELETE":
                    DeleteRequest.handleDeleteRequests(exchange,list);
                    break;
                default:
                    System.out.println("Not method allowed");
                    returnResponse(exchange, 405, "This is the response for not allowed method ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
