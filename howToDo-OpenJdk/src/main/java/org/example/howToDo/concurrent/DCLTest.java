/**************************************************************************************** 
 Copyright © 2003-2012 ZTEsoft Corporation. All rights reserved. Reproduction or      
 transmission in whole or in part, in any form or by any means, electronic, mechanical 
 or otherwise, is prohibited without the prior written consent of the copyright owner. 
 ****************************************************************************************/
package org.example.howToDo.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zhangheng.lll
 * @date ：Created in 2020/5/31 18:04
 * @description：${description}
 * @modified By：
 * @version: $version$
 */
public class DCLTest {
    public static volatile DCLTest instance;
    private DCLTest() {};

    public int a = 1;
    public static DCLTest getInstance() {
        if (null == instance) {
            synchronized (DCLTest.class) {
                if (null == instance) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally {
                        instance = new DCLTest();
                    }
                }
            }
        }

        return instance;
    }

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<Thread>();

        for (int i = 0; i < 1000; i++) {
            threads.add(new Thread((Runnable)()-> {System.out.println(getInstance().a);}));
            threads.get(i).start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
