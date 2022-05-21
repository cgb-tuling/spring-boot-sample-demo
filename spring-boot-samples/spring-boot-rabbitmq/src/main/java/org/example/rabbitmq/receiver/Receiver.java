package org.example.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 消息消费者
 * User: yuanct
 * Date: 2018/12/11 1:41 PM
 */
@Component
public class Receiver {

    @RabbitListener(queues = "topic.messages")
    public void process2(String str1) throws ClassNotFoundException {
        System.out.println("messages ：" + str1);
        System.out.println(Thread.currentThread().getName() + "接收到来自topic.message队列的消息: " + str1);
    }


    public static boolean isNumber(String str){
        Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match=pattern.matcher(str);
        if(!match.matches()){
            return false;
        }else{
            return true;
        }
    }

    public static void main(String[] args) {
        System.out.println(isNumber("zz"));
    }
}