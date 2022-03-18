package com.oscar.controller;

import com.oscar.context.annotations.GET;
import com.oscar.context.annotations.RestController;

import java.util.List;

@RestController("/samples")
public class Controller {
    @GET(consumes = "application/json", produces = "application/json")
    public List<Object> getAll(){

        return null;
    }
}