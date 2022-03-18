package com.oscar.repository;

import com.oscar.dto.Store;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StoreRepository {

    private static List<Store> stores= init();

    private static List<Store> init(){
        List<Store> storeList= new ArrayList<>();
        storeList.add(new Store(1, "Store one", "Street one", 2001, 101));
        storeList.add(new Store(2, "Store two", "Street two", 2002, 201));
        storeList.add(new Store(3, "Store three", "Street three", 2003, 301));
        storeList.add(new Store(4, "Store four", "Street four", 2004, 401));
        storeList.add(new Store(5, "Store five", "Street five", 2005, 501));
        storeList.add(new Store(6, "Store six", "Street six", 2006, 601));
        storeList.add(new Store(7, "Store seven", "Street seven", 2007, 701));
        storeList.add(new Store(8, "Store eight", "Street eight", 2008, 801));
        storeList.add(new Store(9, "Store nine", "Street nine", 2009, 901));
        storeList.add(new Store(10, "Store ten", "Street ten", 2010, 111));
        return storeList;
    }

    public List<Store> getStores(){
        return stores;
    }

    public boolean deleteEntry(Map<String, String> queryMap) {
        boolean isRemoved = false;
        System.out.println("Query map size "+queryMap.size());
        for(String param: queryMap.keySet()) {
            for (Iterator<Store> it = stores.iterator(); it.hasNext(); ) {
                Store aStore = it.next();
                if (Integer.parseInt(queryMap.get(param)) == aStore.getId()) {
                    it.remove();
                   // del.append(queryMap.get(param) + " ");
                    isRemoved = true;
                }
            }
        }
        return isRemoved;
    }

    public void create(Store storeCreated) {
        stores.add(storeCreated);
        System.out.println("New size save "+stores.size());
    }

    public boolean update(Store storeToUpdate) {
        boolean recordFound = false;
        for(Store s: stores){
            if(s.getId()==storeToUpdate.getId()){
                s.setName(storeToUpdate.getName());
                s.setAddress(storeToUpdate.getAddress());
                s.setOpeningYear(storeToUpdate.getOpeningYear());
                s.setMarketValue(storeToUpdate.getMarketValue());
                recordFound=true;
            }
        }
        System.out.println("New size after updated "+stores.size());
        return recordFound;
    }
}
