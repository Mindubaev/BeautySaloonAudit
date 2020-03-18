/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.DAO.User;

import com.mycompany.beautysaloonaudit.Entities.User;
import com.mycompany.beautysaloonaudit.SpringConfig.Root;
import java.util.List;
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
public class UserServiceImplTest {
    
    ApplicationContext context=new AnnotationConfigApplicationContext(Root.class);
    UserService userService=context.getBean("userService",UserService.class);
    
    public UserServiceImplTest() {
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

    /**
     * Test of findAll method, of class UserServiceImpl.
     */
    @Test
    public void testFindAll() {
        List<User> users=userService.findAll();
        assertNotNull(context);
        System.out.println("FindAll test: "+users.toString());
    }

    @Test
    public void testFindByFirstnameAndLastname() {
        List<User> users=userService.findByFirstnameAndLastname("Иван","Иванов");
        System.out.println("FindByFirstnameAndLastname test: "+users.toString());
        assertNotNull(users);
    }
    
    @Test
    public void testFindByLoginAndPassword() {
        User user=userService.findByLoginAndPassword("admin", "admin");
        System.out.println("FindByLoginAndPassword test: "+user.toString());
        assertNotNull(user);
    }
    
}
