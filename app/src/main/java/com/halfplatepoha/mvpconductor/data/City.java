package com.halfplatepoha.mvpconductor.data;

/**
 * Created by surajkumarsau on 25/01/17.
 */

public class City {
    private long id;
    private String name;

    public City(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}