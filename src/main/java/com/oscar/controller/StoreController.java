package com.oscar.controller;

import com.oscar.context.annotations.*;
import com.oscar.dto.Store;
import com.oscar.repository.StoreRepository;

import java.util.List;

@RestController("/stores")
public class StoreController {
    StoreRepository storeRepository = new StoreRepository();

    @DELETE(consumes = "application/json", produces = "application/json")
    public void deleteStore(int idStore){
        storeRepository.delete(idStore);
    }

    @POST(consumes = "application/json", produces = "application/json")
    public Store createStore(Store store){
        return storeRepository.create(store);
    }

    @PUT(consumes = "application/json", produces = "application/json")
    public Store updateStore(Store store){
        return storeRepository.update(store);
    }

    @GET(consumes = "application/json", produces = "application/json")
    public Store getStore(int idStore){
        return storeRepository.getStore(idStore);
    }
    @GET(consumes = "application/json", produces = "application/json")
    public List<Store> getAllStores(){
        return storeRepository.getStores();
    }
}