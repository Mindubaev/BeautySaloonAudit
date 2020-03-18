/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.valodators;

import com.mycompany.beautysaloonaudit.Entities.User;
import java.util.HashMap;
import java.util.HashSet;
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
public class LoginValidatorTest {
    
    public LoginValidatorTest() {
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
        LoginValidator instance = new LoginValidator();
        boolean expResult = true;
        boolean result = instance.supports(clazz);
        assertEquals(expResult, result);
    }

    @Test
    public void testSupports2() {
        System.out.println("supports2");
        Class clazz = User.class;
        LoginValidator instance = new LoginValidator();
        boolean expResult = false;
        boolean result = instance.supports(clazz);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidate1() {
        System.out.println("validate login test 1");
        Object target = "someMail@gmail.com";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        LoginValidator instance = new LoginValidator();
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
        System.out.println("validatelogin login test 2");
        Object target = "@gmail.com";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        LoginValidator instance = new LoginValidator();
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
        System.out.println("validatelogin login test 3");
        Object target = "mail@gm#ail.com";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        LoginValidator instance = new LoginValidator();
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
        System.out.println("validatelogin login test 4");
        Object target = "mail@gm ail.com";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        LoginValidator instance = new LoginValidator();
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
        System.out.println("validatelogin login test 5");
        Object target = "";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        LoginValidator instance = new LoginValidator();
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
        System.out.println("validatelogin login test 6");
        Object target = "gh";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        LoginValidator instance = new LoginValidator();
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
        System.out.println("validatelogin login test 7");
        Object target = "fgdfgdfgdgdfgdfgdfgfhdfhgfuufgjkgfjdgfgqwfsdjgfkqwgefiuqgweufgeuiqgfuqewgfjsdgfuiwegtfuwgsfuwqefwugiqgfkqkwghfgk";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        LoginValidator instance = new LoginValidator();
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
