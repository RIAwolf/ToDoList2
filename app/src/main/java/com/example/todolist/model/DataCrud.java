package com.example.todolist.model;

public interface DataCrud {
    void get();
    void post(ItemVO item);
    void put(ItemVO item);
    void delete(ItemVO item);
}
