package com.oscar.handler.requests;

import com.oscar.dto.Store;
import com.oscar.handler.RequestHandler;
import com.oscar.helper.StoreHelper;
import com.sun.net.httpserver.HttpExchange;

import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DeleteRequest extends RequestHandler {
    public static void handleDeleteRequests(HttpExchange exchange, List<Store> list) throws Exception {
        //printRequestInfo(exchange);
        boolean isRemoved=false;
        URI requestURI = exchange.getRequestURI();
        System.out.println("Delete method");
        System.out.println("Query:  "+requestURI.getQuery());
        StringBuilder del= new StringBuilder();
        if(requestURI.getQuery()!=null) {
            Map<String, String> queryMap = StoreHelper.queryToMap(requestURI.getQuery());
            System.out.println("Query map size "+queryMap.size());
            for(String param: queryMap.keySet()) {
                for (Iterator<Store> it = list.iterator(); it.hasNext(); ) {
                    Store aStore = it.next();
                    if (Integer.parseInt(queryMap.get(param)) == aStore.getId()) {
                        it.remove();
                        del.append(queryMap.get(param) + " ");
                        isRemoved=true;
                    }
                }
            }
            del.append("removed");
        }else {
            System.out.println("No data to delete ");
            System.out.println("List size with no deleted elements " + list.size());
        }
        returnResponse(exchange, isRemoved?200:204, isRemoved?del.toString():null);
    }
}
