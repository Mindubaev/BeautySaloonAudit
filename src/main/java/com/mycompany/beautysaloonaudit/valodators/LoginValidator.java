/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.valodators;

import static com.mycompany.beautysaloonaudit.valodators.PasswordValidator.MAX_LENGTH;
import static com.mycompany.beautysaloonaudit.valodators.PasswordValidator.MIN_LENGTH;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Artur
 */
@Component("loginValidator")
public class LoginValidator implements Validator{
    
    public static  final int MIN_LENGTH=4;
    public static  final int MAX_LENGTH=20;

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String str=(String)(target);
        if (str==null)
            errors.reject("login.null", "login equals null");
        else
        {
            if (str.length()<MIN_LENGTH)
            {
                errors.reject("login.MiN_LENGTH", "Minimal length of login "+MIN_LENGTH);
            }
            else
            {
                if (str.length()>MAX_LENGTH)
                    errors.reject("login.NAX_LENGTH", "Maximal length of login "+MAX_LENGTH);
                if (str.startsWith("@") || str.startsWith("."))
                errors.reject("login.startWith", "incorrect start of login");
                if (numOfSpecialCharactersInLogin(str)!=0)
                    errors.reject("login.specialCharacters", "login shouldn't special characters");
                if (str.contains(" "))
                    errors.reject("login.space", "login shouldn't contain empty space");
            }
        }
    }
    
    private int numOfSpecialCharactersInLogin(String string){
        int counter=0;
        for (int i=0;i<string.length();i++){
            if (!(Character.isAlphabetic(string.charAt(i))) && !(Character.isDigit(string.charAt(i))) && string.charAt(i)!='@' && string.charAt(i)!='.')
                counter++;
        }
        return counter;
    }
    
}
