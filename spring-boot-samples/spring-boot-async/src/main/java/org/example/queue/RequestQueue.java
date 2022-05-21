package org.example.queue;

import org.example.model.AsyncVo;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Description: 存放所有异步处理接口请求队列的对象,一个接口对应一个队列
 * User: yuanct
 * Date: 2019-08-19 08:08
 */
@Component
public class RequestQueue {
    /**
     * 处理下订单接口的队列，设置缓冲容量为50
     */
    private BlockingQueue<AsyncVo<String, Object>> orderQueue = new LinkedBlockingQueue<>();

    public BlockingQueue<AsyncVo<String, Object>> getOrderQueue() {
        return orderQueue;
    }
}
