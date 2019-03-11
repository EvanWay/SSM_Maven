package com.soecode.lyf;

import com.soecode.lyf.entity.Book;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BookTest {

    @Test
    public void bookTest() {
    	//使用ClassPathXmlApplicationContext加载bean 
        ApplicationContext ac = new ClassPathXmlApplicationContext(new String[] {"classpath:spring/spring-service.xml","classpath:spring/spring-dao.xml"});
        Book book = (Book) ac.getBean("test");
        book.setBookId(666);
        System.out.println(book);
    }
}
