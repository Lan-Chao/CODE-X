package com.silent.model;


import lombok.Data;

@Data
public class Person {

    private String name;

    private Integer age;

    @Override
    public String toString(){
        return super.toString();
    }

}
