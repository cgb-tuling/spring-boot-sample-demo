package com.itmuch.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Slf4j
@SpringBootApplication
@RestController
public class SpringBootLockApplication {
  @Autowired
  private ZookeeperLockRegistry zookeeperLockRegistry;

  @GetMapping("test")
  public void test() throws InterruptedException {
    Lock lock = zookeeperLockRegistry.obtain("lock1");
    boolean b1 = lock.tryLock(3, TimeUnit.SECONDS);
    log.info("b1 is : {}", b1);

    TimeUnit.SECONDS.sleep(5);

    boolean b2 = lock.tryLock(3, TimeUnit.SECONDS);
    log.info("b2 is : {}", b2);

    if (b1){
      lock.unlock();
    }
    if (b2){
      lock.unlock();
    }
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringBootLockApplication.class, args);
  }

}
