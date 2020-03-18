/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.valodators;

import com.mycompany.beautysaloonaudit.Entities.User;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

/**
 *
 * @author Artur
 */
public class UserDetailValidatorTest {
    
    public UserDetailValidatorTest() {
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
    public void testSupports1() {
        System.out.println("supports1");
        Class clazz = String.class;
        UserDetailValidator instance = new UserDetailValidator();
        boolean expResult = true;
        boolean result = instance.supports(clazz);
        assertEquals(expResult, result);
    }

    @Test
    public void testSupports2() {
        System.out.println("supports2");
        Class clazz = User.class;
        UserDetailValidator instance = new UserDetailValidator();
        boolean expResult = false;
        boolean result = instance.supports(clazz);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidate1() {
        System.out.println("validate field test 1");
        Object target = "Artur";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        UserDetailValidator instance = new UserDetailValidator();
        instance.validate(target, errors);
        boolean result=true;
        String info="|";
        if (errors.hasErrors())
        {
            for(ObjectError error:errors.getAllErrors())
                info=info+error.getDefaultMessage()+"|";
            result=false;
        }
        assertTrue(result);
    }
    
    @Test
    public void testValidate2() {
        System.out.println("validate field field test 2");
        Object target = "@rtur";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        UserDetailValidator instance = new UserDetailValidator();
        instance.validate(target, errors);
        boolean result=true;
        String info="|";
        if (errors.hasErrors())
        {
            for(ObjectError error:errors.getAllErrors())
                info=info+error.getDefaultMessage()+"|";
            result=false;
        }
        assertFalse(result);
    }
    
    @Test
    public void testValidate3() {
        System.out.println("validate field field test 3");
        Object target = "Artur111";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        UserDetailValidator instance = new UserDetailValidator();
        instance.validate(target, errors);
        boolean result=true;
        String info="|";
        if (errors.hasErrors())
        {
            for(ObjectError error:errors.getAllErrors())
                info=info+error.getDefaultMessage()+"|";
            result=false;
        }
        assertFalse(result);
    }
    
    @Test
    public void testValidate4() {
        System.out.println("validate field field test 4");
        Object target = "Artu r";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        UserDetailValidator instance = new UserDetailValidator();
        instance.validate(target, errors);
        boolean result=true;
        String info="|";
        if (errors.hasErrors())
        {
            for(ObjectError error:errors.getAllErrors())
                info=info+error.getDefaultMessage()+"|";
            result=false;
        }
        assertFalse(result);
    }
    
    @Test
    public void testValidate5() {
        System.out.println("validate field field test 5");
        Object target = "A_r_t_u_r";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        UserDetailValidator instance = new UserDetailValidator();
        instance.validate(target, errors);
        boolean result=true;
        String info="|";
        if (errors.hasErrors())
        {
            for(ObjectError error:errors.getAllErrors())
                info=info+error.getDefaultMessage()+"|";
            result=false;
        }
        assertFalse(result);
    }
    
    @Test
    public void testValidate6() {
        System.out.println("validate field field test 6");
        Object target = "A";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        UserDetailValidator instance = new UserDetailValidator();
        instance.validate(target, errors);
        boolean result=true;
        String info="|";
        if (errors.hasErrors())
        {
            for(ObjectError error:errors.getAllErrors())
                info=info+error.getDefaultMessage()+"|";
            result=false;
        }
        assertFalse(result);
    }
    
    @Test
    public void testValidate7() {
        System.out.println("validate field field test 7");
        Object target = "Aaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrttttttttttttttttttuuuuuuuuuuuuuuuuuuuuuuuurrrrrrrrrrrrrrr";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        UserDetailValidator instance = new UserDetailValidator();
        instance.validate(target, errors);
        boolean result=true;
        String info="|";
        if (errors.hasErrors())
        {
            for(ObjectError error:errors.getAllErrors())
                info=info+error.getDefaultMessage()+"|";
            result=false;
        }
        assertFalse(result);
    }
    
}
