package com.silent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author zhaochangren
 * @Title: CODE
 * @ProjectName CODE-X
 * @Description:
 * @date 2020/5/28 11:45
 */

@SpringBootApplication
public class CODE {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {

        SpringApplication.run(CODE.class, args);

        System.out.println("hello");


    }

}
