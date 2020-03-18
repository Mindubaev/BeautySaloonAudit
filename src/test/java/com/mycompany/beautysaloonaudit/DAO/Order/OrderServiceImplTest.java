/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.DAO.Order;

import com.mycompany.beautysaloonaudit.DAO.User.UserService;
import com.mycompany.beautysaloonaudit.Entities.Order;
import com.mycompany.beautysaloonaudit.SpringConfig.Root;
import java.sql.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author Artur
 */
public class OrderServiceImplTest {
    
    ApplicationContext context=new AnnotationConfigApplicationContext(Root.class);
    OrderService orderService=context.getBean("orderService",OrderService.class);
    
    public OrderServiceImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testFindByDateBetween() {
        DateTime start=new DateTime(2019, 8, 30, 0, 0);
        DateTime end=new DateTime(2019, 8, 30, 15, 0);
        List<Order> orders=orderService.findByDateBetween(start, end);
        System.out.println(orders.toString());
    }
    
}
