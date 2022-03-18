package com.oscar.helper;

import com.oscar.dto.Store;
import com.oscar.serializer.JsonSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreHelper {

    public static String fillStoresJson(List<Store> stores) throws Exception {
        return serializeList(stores);
    }

    public static String fillStoreJson(Store store) throws Exception {
        return serialize(store);
    }

    public static List<Store> fillStores(){
        List stores= new ArrayList<>();
        stores.add(new Store(1, "Store one", "Street one", 2001, 101));
        stores.add(new Store(2, "Store two", "Street two", 2002, 201));
        stores.add(new Store(3, "Store three", "Street three", 2003, 301));
        stores.add(new Store(4, "Store four", "Street four", 2004, 401));
        stores.add(new Store(5, "Store five", "Street five", 2005, 501));
        stores.add(new Store(6, "Store six", "Street six", 2006, 601));
        stores.add(new Store(7, "Store seven", "Street seven", 2007, 701));
        stores.add(new Store(8, "Store eight", "Street eight", 2008, 801));
        stores.add(new Store(9, "Store nine", "Street nine", 2009, 901));
        stores.add(new Store(10, "Store ten", "Street ten", 2010, 111));
        return stores;
    }

    public static Map<String, String> queryToMap(String query) {
        System.out.println("Parameters "+query); //x=10&y=100
        Map<String, String> result = new HashMap<>();

        if(query == null)
            return null;

        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            result.put(entry[0], (entry.length>1)?entry[1]:null);
        }
        return result;
    }

    public static String getObjectFilteredJson(Map<String, String> parametersMap, List<Store> stores) throws Exception {
        //Integer total=0;
        List<Integer> ids= new ArrayList<>();
        List<Store> storesFiltered= new ArrayList<>();

        for(String param: parametersMap.keySet())
            ids.add((parametersMap.get(param)!=null)?Integer.valueOf(parametersMap.get(param)):0);

        for(Integer i: ids){
            listStores:
            for(Store s: stores){
                if(s.getId()==i) {
                    storesFiltered.add(s);
                    break listStores;
                }
            }
        }

        System.out.println("List filtered size "+storesFiltered.size());
        return storesFiltered.size()>0?StoreHelper.serializeList(storesFiltered):null;
    }

    public static Store saveData(String body) throws Exception {
        Store newStore= unSerialize(body);
        System.out.println("Store created");
        return newStore;
    }

    public static String serialize(Store s) throws Exception {
        String json = JsonSerializer.serilaize(s);
        System.out.println(json);
        return json;
    }

    public static String serializeList(List<Store> s2) throws Exception {
        String jsonList = JsonSerializer.serializeList(s2);
        System.out.println(jsonList);
        return jsonList;
    }

    public static Store unSerialize(String s) throws Exception {
        Store st= JsonSerializer.deserilaize(Store.class, s);
        System.out.println(st);
        return st;
    }

}
