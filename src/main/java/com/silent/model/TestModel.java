package com.silent.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author zhao
 * @date 2021/11/24 18:01
 */

@Data
@Slf4j
public class TestModel implements Serializable {

    private String name;

    private int age;

    public TestModel(String name) {

    }

    @Override
    protected void finalize() throws Throwable {
        log.info("TestModel exec finalize");
    }
}
