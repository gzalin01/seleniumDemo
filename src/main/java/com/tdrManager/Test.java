package com.tdrManager;

import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        String  uuid = UUID.randomUUID().toString();
        System.out.println("...."+uuid);
        String name="success";
        if (name!="success"){
            uuid = UUID.randomUUID().toString();
            System.out.println("************8"+uuid);
        }
    }
}
