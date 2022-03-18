package com.oscar.repository;

import com.oscar.dto.Store;

import java.util.ArrayList;
import java.util.List;

public class StoreRepository {

    private static List<Store> stores = init();

    private static List<Store> init() {
        List<Store> storeList = new ArrayList<>();
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

    public List<Store> getStores() {
        return stores;
    }

    public void delete(int idStore) {
        stores.removeIf(aStore -> idStore == aStore.getId());
    }

    public Store create(Store storeCreated) {
        stores.add(storeCreated);
        System.out.println("New size save " + stores.size());
        return storeCreated;
    }

    public Store update(Store storeToUpdate) {
        Store store = null;
        for (Store s : stores) {
            if (s.getId() == storeToUpdate.getId()) {
                s.setName(storeToUpdate.getName());
                s.setAddress(storeToUpdate.getAddress());
                s.setOpeningYear(storeToUpdate.getOpeningYear());
                s.setMarketValue(storeToUpdate.getMarketValue());
                store = s;
            }
        }
        System.out.println("New size after updated " + stores.size());
        return store;
    }

    public Store getStore(int idStore) {
        return stores.stream()
                .filter(store -> store.getId() == idStore)
                .findFirst()
                .orElse(null);
    }
}
