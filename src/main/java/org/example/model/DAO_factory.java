package org.example.model;

public class DAO_factory {
    private static final DAO_memory dao = new DAO_DB();

    static DAO_memory build(){
        return dao;
    }
}
