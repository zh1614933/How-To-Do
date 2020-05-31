package org.example.redisson.lock;
/**
 * @author zhangheng
 * @date 2020/5/26 0:12
 */

import org.redisson.Redisson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhangheng
 * @description
 * @date 2020/5/26
 */
public class LockApplication {

    @Autowired
    private Redisson redisson;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public static void main (String[] args) {
        SpringApplication.run(LockApplication.class, args);
    }

    @GetMapping("/springTest")
    public String response(@RequestParam(required = false, name = "request") String request) {
        if (request == null || request.length() == 0) {
            request = "request";
        }
        return request;
    }
}
