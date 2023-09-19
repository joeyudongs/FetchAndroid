package com.example.myapplication;

public class HiringEntity {
    String listId;
    String id;
    String name;

    public HiringEntity(String listId, String id, String name) {
        this.listId = listId;
        this.id = id;
        this.name = name;
    }

    public HiringEntity() {
    }
    public String getlistId() {
        return listId;
    }

    public void setlistId(String listId) {
        this.listId = listId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
