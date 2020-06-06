/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or      
 transmission in whole or in part, in any form or by any means, electronic, mechanical 
 or otherwise, is prohibited without the prior written consent of the copyright owner. 
 ****************************************************************************************/
package org.example.howToDo.redisNew.controller.Bean;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：zhangheng.lll
 * @date ：Created in 2020/6/6 20:32
 * @description：${description}
 * @modified By：
 * @version: $version$
 */
@Configuration
public class RedissonBean {

    @Bean
    public Redisson redisson () {
        //单机模式
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379").setDatabase(1);
        return (Redisson) Redisson.create(config);
    }
}
