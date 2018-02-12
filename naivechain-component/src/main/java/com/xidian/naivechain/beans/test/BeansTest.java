package com.xidian.naivechain.beans.test;

import com.xidian.naivechain.beans.Person;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author LvLiuWei
 * @date 2018/2/9
 */
@Data
@Slf4j
public class BeansTest {

    private Person p1;

    @Before
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println(("ApplicationContext:{}."+ context));
        p1 = (Person) context.getBean("person");
    }

    @Test
    public void test1() {
        System.out.println(p1.toString());
    }
}
