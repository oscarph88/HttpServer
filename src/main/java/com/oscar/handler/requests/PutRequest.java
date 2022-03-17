package com.oscar.handler.requests;

import com.oscar.dto.Store;
import com.oscar.handler.RequestHandler;
import com.oscar.helper.StoreHelper;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;
import java.util.List;

public class PutRequest extends RequestHandler {
    public static void handlePutRequests(HttpExchange exchange, List<Store> list) throws Exception {
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
        update:
        for(Store s: list){
            if(s.getId()==storeToUpdate.getId()){
                s.setName(storeToUpdate.getName());
                s.setAddress(storeToUpdate.getAddress());
                s.setOpeningYear(storeToUpdate.getOpeningYear());
                s.setMarketValue(storeToUpdate.getMarketValue());
                recordFound=true;
                break update;
            }
        }
        System.out.println("New size after updated "+list.size());
        returnResponse(exchange, recordFound?200:204, recordFound?StoreHelper.fillStoreJson(storeToUpdate):null);
    }
}
