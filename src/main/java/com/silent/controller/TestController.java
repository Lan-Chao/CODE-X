package com.silent.controller;

import com.silent.other.MailDemo;
import com.silent.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhaochangren
 * @Title: TestController
 * @ProjectName CODE-X
 * @Description: test
 * @date 2020/5/28 13:26
 */

@Slf4j
@RestController
@RequestMapping(value = "/zhao")
@Controller
public class TestController {

    @Autowired
    private MailDemo mailDemo;

    @Autowired
    private CacheService cacheService;

    @PostMapping("/hello")
    public Object get111(@RequestBody String ss) throws Exception {
        System.out.println(ss);

        return "hhhh";
    }

    @GetMapping("/cache")
    public Object get1(Integer t){
        if (t == 1){
            return 1/0;
        }
        return cacheService.cache("11");
    }




}
