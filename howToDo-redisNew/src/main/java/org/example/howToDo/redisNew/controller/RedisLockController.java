/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or      
 transmission in whole or in part, in any form or by any means, electronic, mechanical 
 or otherwise, is prohibited without the prior written consent of the copyright owner. 
 ****************************************************************************************/
package org.example.howToDo.redisNew.controller;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zhangheng.lll
 * @date ：Created in 2020/6/6 19:05
 * @description：${description}
 * @modified By：
 * @version: $version$
 */
@RestController
@RequestMapping("redisLock")
public class RedisLockController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    private static final String LOCK_KEY = "LOCK_KEY";
    @RequestMapping("/findvalue")
    public String findRedisValue(@RequestParam(name = "valueKey") String valueKey, @RequestParam(name = "value")String value) {
        stringRedisTemplate.opsForValue().set(valueKey, value);
        return stringRedisTemplate.opsForValue().get(valueKey);
    }

    /**
     * @author zhang.heng
     * @date 2020-06-06 20:24
     * @return java.lang.String
     * 该锁可能出现；  程序未执行完，锁失效
     * 可以通过开启分线程，进行延长失效时间或者使用redisson中的自旋加失效时间
     * @throws
     * @taskId
    */
    @RequestMapping("/deduct")
    public String deductStock() {
        String clientUUID = UUID.randomUUID().toString();
        Boolean lockKey = stringRedisTemplate.opsForValue().setIfAbsent(LOCK_KEY, clientUUID, 30, TimeUnit.SECONDS);

        if (!lockKey) {
            return "waiting";
        }
        int nbrInStore = Integer.parseInt(stringRedisTemplate.opsForValue().get("NBR_STORE"));

        if (nbrInStore > 0) {
            try {
                //执行业务
                int realNbrInStore = nbrInStore - 1;
                stringRedisTemplate.opsForValue().set("NBR_STORE", String.valueOf(realNbrInStore));
                System.out.println("扣减成功，剩余库存： " + realNbrInStore);
            }
            catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
            finally {
                if (clientUUID.equals(stringRedisTemplate.opsForValue().get(LOCK_KEY))) {
                    stringRedisTemplate.delete(LOCK_KEY);
                }
            }
        }
        else {
            System.out.println("扣减失败!!");
        }
        return "end";
    }

    @Autowired
    private Redisson redisson;

    @RequestMapping("/deductRedisson")
    public String deductStoreInRedisson() {
        RLock redissonLock = redisson.getLock(LOCK_KEY);

        int nbrInStore = Integer.parseInt(stringRedisTemplate.opsForValue().get("NBR_STORE"));

        if (nbrInStore > 0) {
            try {
                redissonLock.lock();
                //执行业务
                int realNbrInStore = nbrInStore - 1;
                stringRedisTemplate.opsForValue().set("NBR_STORE", String.valueOf(realNbrInStore));
                System.out.println("扣减成功，剩余库存： " + realNbrInStore);
            }
            catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
            finally {
                redissonLock.unlock();
//                if (clientUUID.equals(stringRedisTemplate.opsForValue().get(LOCK_KEY))) {
//                    stringRedisTemplate.delete(LOCK_KEY);
//                }
            }
        }
        else {
            System.out.println("扣减失败!!");
        }
//        redisson.lock
        return "end";
    }

}
