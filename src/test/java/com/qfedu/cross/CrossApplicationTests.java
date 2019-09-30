package com.qfedu.cross;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrossApplicationTests {

    //注入StringRedisTemplate对象
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void contextLoads() {
        //操作类型是string的redis数据
        stringRedisTemplate.opsForValue().set("name","你好");
        System.out.println("==="+stringRedisTemplate.opsForValue().get("name"));
        stringRedisTemplate.opsForValue().set("age","20");
        //设置过期时间
        stringRedisTemplate.expire("age",100, TimeUnit.SECONDS);

    }

}
