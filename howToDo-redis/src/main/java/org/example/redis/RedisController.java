/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or      
 transmission in whole or in part, in any form or by any means, electronic, mechanical 
 or otherwise, is prohibited without the prior written consent of the copyright owner. 
 ****************************************************************************************/
package org.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zhangheng.lll
 * @date ：Created in 2020/6/5 22:52
 * @description：${description}
 * @modified By：
 * @version: $version$
 */
@SpringBootApplication
@RestController
@RequestMapping("redis")
public class RedisController {

//    @Autowired(required = true)
//    private StringRedisTemplate stringRedisTemplate;

    public static void main (String[] args) {
        SpringApplication.run(RedisController.class, args);
    }

    @RequestMapping("/findvalue")
    public String findRedisValue() {
//        stringRedisTemplate.opsForValue().set("zh", "zhangHeng");
//        return stringRedisTemplate.opsForValue().get("zh");
        return "ok";
    }
}
