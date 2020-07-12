package com.connor.taotie.dto;


/**
 * 用于初始化DTO
 */
public class SupperMan {

    private String name = "superMan";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SupperMan{" +
                "name='" + name + '\'' +
                '}';
    }
}
