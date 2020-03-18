/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.valodators;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Artur
 */
@Component("passwordValidator")
public class PasswordValidator implements Validator{
    
    public static  final int MIN_LENGTH=8;
    public static  final int MAX_LENGTH=50;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String str=(String)(target);
        if (str==null)
            errors.reject("pasword.null", "password equals null");
        else
        {
            if (str.length()<MIN_LENGTH)
            {
                errors.reject("password.MiN_LENGTH", "Minimal length of password -"+MIN_LENGTH);
            }
            else
            {
                if (str.length()>MAX_LENGTH)
                    errors.reject("password.NAX_LENGTH", "Maximal length of password -"+MAX_LENGTH);
                if (numOfSpecialCharactersInPassword(str)!=0)
                    errors.reject("password.specialCharacters", "password shouldn't special characters");
                if (str.contains(" "))
                    errors.reject("password.space", "password shouldn't contain empty space");
                if (numOfDigitsInPassword(str)<4)
                    errors.reject("password.numOfDigits", "password should contain at least 4 digits");
                if (numOfLetterInPassword(str)<4)
                    errors.reject("password.numOfLetter", "password should contain at least 4 letters");
            }
        }
    }
    
    private int numOfDigitsInPassword(String string){
        char[] digits={'1','2','3','4','5','6','7','8','9','0'};
        int counter=0;
        for (int i=0;i<string.length();i++){
            boolean found=false;
            for(char digit:digits)
                if (string.charAt(i)==digit)
                {
                    found=true;
                    break;
                }
            if (found)
                counter=counter+1;
        }
        return counter;        
    }
    
    private int numOfLetterInPassword(String string){
        int counter=0;
        for (int i=0;i<string.length();i++){
            if (Character.isLetter(string.charAt(i)))
                counter++;
        }
        return counter;
    }
    
    private int numOfSpecialCharactersInPassword(String string){
        int counter=0;
        for (int i=0;i<string.length();i++){
            if (!(Character.isLetter(string.charAt(i))) && !(Character.isDigit(string.charAt(i))))
                counter++;
        }
        return counter;
    }
    
}
