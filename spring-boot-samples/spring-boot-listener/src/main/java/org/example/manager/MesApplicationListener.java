package org.example.manager;

/**
 * mes事件监听器的基础类（只需要实现该接口）
 * @author yct
 * @date Created in 2020/2/7 15:55
 */
public interface MesApplicationListener<E extends ApplicationEvnt> {
    /**
     * 发布事件
     * @param e 事件类
     */
    void onEvent(E e);
}
