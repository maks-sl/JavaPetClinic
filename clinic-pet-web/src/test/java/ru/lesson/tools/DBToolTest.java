package ru.lesson.tools;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.lesson.store.IHTClientStorage;

import static org.junit.Assert.assertNotNull;

/**
 * Created by User on 05.07.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
@WebAppConfiguration
public class DBToolTest {

    @Autowired
    private IHTClientStorage s;

    @Test
    public void doTest(){
        assertNotNull(s);
    }

}