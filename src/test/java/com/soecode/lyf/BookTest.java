package com.soecode.lyf;

import com.soecode.lyf.entity.Book;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BookTest {

    @Test
    public void bookTest() {

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-service.xml");
        Book b = (Book) ac.getBean("test");
        b.setBookId(666);
        System.out.println(b);
    }
}
