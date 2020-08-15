package com.connor.taotie.dto;

import org.springframework.beans.factory.ObjectProvider;

/**
 * 用于初始化DTO
 */
public class SupperMan {

    private Wenpon gun;

    public SupperMan(ObjectProvider<Wenpon> gunProvider) {

        this.gun = gunProvider.getIfAvailable();
    }

    public void userWenpon() {
        gun.doSomething();
    }
}
