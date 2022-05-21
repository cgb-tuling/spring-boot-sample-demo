package org.example.manager;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * mes事件管理器
 * @author yct
 * @date Created in 2020/2/7 15:53
 */
public class ListenerManage {
    /**
     * 保存所有的监听器
     */
    static List<MesApplicationListener<?>> list = new ArrayList<>();


    /**
     * 添加监听器
     * @param listener 监听器
     */
    public static void   addListener(MesApplicationListener listener){
        list.add(listener);
    }

    /**
     * 判断一下有哪些人对这个事件感兴趣
     * @param event mes事件
     */
    public static void pushEvent(ApplicationEvnt event){
        for (MesApplicationListener applicationListener : list) {
            //拿泛型
            Class tClass = (Class)((ParameterizedType)applicationListener.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
            //判断一下泛型
//            tClass.isAssignableFrom()
            if (tClass.equals(event.getClass())) {
                applicationListener.onEvent(event);
            }
        }
    }
}
