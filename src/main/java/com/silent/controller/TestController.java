package com.silent.controller;

import com.silent.threadpool.ThreadPoolMonitorManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author zhao
 * @date 2021/11/12 13:24
 */


@Slf4j
@Controller
@ResponseBody
@RequestMapping("/test")
public class TestController {


    @Resource
    ThreadPoolMonitorManager threadPoolMonitorManager;


    @GetMapping("/test")
    public Object test() {
        threadPoolMonitorManager.getDefaultThreadPoolExecutor().submit(() -> {
            log.info("running");
        });
        return "success";
    }



}
