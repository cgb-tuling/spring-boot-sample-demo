package org.example.aop.cglib.dynamic.proxy;

import org.springframework.cglib.proxy.Enhancer;

public class Main {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        //被代理类：Panda
        enhancer.setSuperclass(Panda.class);
        //设置回调
        enhancer.setCallback(new PandaMethodInterceptor());
        //生成代理对象
        Panda panda = (Panda) enhancer.create();
        //调用代理类的方法
        panda.run();
    }
}
