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
public class PasswordValidatorTest {
    
    public PasswordValidatorTest() {
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
        PasswordValidator instance = new PasswordValidator();
        boolean expResult = true;
        boolean result = instance.supports(clazz);
        assertEquals(expResult, result);
    }

    @Test
    public void testSupports2() {
        System.out.println("supports2");
        Class clazz = User.class;
        PasswordValidator instance = new PasswordValidator();
        boolean expResult = false;
        boolean result = instance.supports(clazz);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidate1() {
        System.out.println("validate password test 1");
        Object target = "admin12345";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        PasswordValidator instance = new PasswordValidator();
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
        System.out.println("validatelogin password test 2");
        Object target = "@admin12345";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        PasswordValidator instance = new PasswordValidator();
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
        System.out.println("validatelogin password test 3");
        Object target = "admin#12345";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        PasswordValidator instance = new PasswordValidator();
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
        System.out.println("validatelogin password test 4");
        Object target = "admin 12345";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        PasswordValidator instance = new PasswordValidator();
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
        System.out.println("validatelogin password test 5");
        Object target = "";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        PasswordValidator instance = new PasswordValidator();
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
        System.out.println("validatelogin password test 6");
        Object target = "ad";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        PasswordValidator instance = new PasswordValidator();
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
        System.out.println("validatelogin password test 7");
        Object target = "fgdfgdfgdgdfgdfgdfgfhdfhgfuufgjkgfjdgfgqwfsdjgfkqwgefiuqgweufgeuiqgfuqewgfjsdgfuiwegtfuwgsfuwqefwugiqgfkqkwghfgk";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        PasswordValidator instance = new PasswordValidator();
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
    public void testValidate8() {
        System.out.println("validatelogin password test 2");
        Object target = "adm@in.12345";
        Errors errors = new MapBindingResult(new HashMap<String,String>(), "login");
        PasswordValidator instance = new PasswordValidator();
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
