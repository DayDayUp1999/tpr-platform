package com.zyz.demo;

import com.zyz.demo.mytools.EncryptUtil;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("com.zyz.demo.mapper")
class TprPlatformApplicationTests {



    @Test
    public void testSelect() {
        System.out.println(EncryptUtil.md5("123"));

    }



}
