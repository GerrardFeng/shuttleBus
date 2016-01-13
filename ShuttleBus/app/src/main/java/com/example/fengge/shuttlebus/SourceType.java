package com.example.fengge.shuttlebus;

/**
 * Created by GUOFR2 on 1/13/2016.
 */
public enum SourceType {
    ROUTE(1, "选择路线"), STOP(2,"选择站点"), TIME(3, "选择有效日期");
    private int id;
    private String name;

    private SourceType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
