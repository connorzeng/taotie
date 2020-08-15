package com.connor.taotie.dto;


import org.springframework.beans.factory.ObjectProvider;

/**
 * 用于初始化DTO
 */
public class SupperMan {

    private String name = "superMan";
    public SupperMan(ObjectProvider<Wenpon> gunProvider) {

        this.gun = gunProvider.getIfAvailable();
    }

    public void userWenpon() {
        gun.doSomething();
    }

    private Wenpon gun;

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
