package com.silent.config;

import com.silent.properties.ThreadPoolProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhao
 * @date 2021/11/11 17:02
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "monitor.thread-pool")
public class ThreadPoolConfig {

    private List<ThreadPoolProperties> executors = new ArrayList<>();

}
