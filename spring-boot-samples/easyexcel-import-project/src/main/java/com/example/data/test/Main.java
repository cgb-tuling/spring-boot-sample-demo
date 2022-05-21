//测试读取List的线程类，大概34秒
package com.example.data.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        List<String> list = new ArrayList<String>();
        Map<Long,Integer> map = new HashMap<Long,Integer>();

        for(int i = 0;i<1000;i++){
            list.add(""+i);
        }

        int pcount = Runtime.getRuntime().availableProcessors();
        long start = System.currentTimeMillis();

        for(int i=0;i<pcount;i++){

           Thread t = new MyThread1(list,map);
            map.put(t.getId(),Integer.valueOf(i));
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           // System.out.println(list.get(i));
        }
        System.out.println("----"+(System.currentTimeMillis() - start));
    }
}

