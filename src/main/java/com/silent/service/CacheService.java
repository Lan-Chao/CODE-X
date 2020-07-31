package com.silent.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author zhaochangren
 * @Title: CacheService
 * @ProjectName CODE-X
 * @Description:
 * @date 2020/7/10 16:18
 */

@Service
public class CacheService {


    @Cacheable(value = "iris", key = "#s")
    public  String cache(String s){
        System.out.println("进入方法");
        return "sss";
    }


}
