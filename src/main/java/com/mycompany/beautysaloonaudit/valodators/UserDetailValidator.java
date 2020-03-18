/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.valodators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Artur
 */
@Component("userDetailValidator")
public class UserDetailValidator implements Validator{
    
    public static  final int MIN_LENGTH=2;
    public static  final int MAX_LENGTH=30;

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String str=(String)(target);
        if (str==null)
            errors.reject("field.null", target+"field equals null");
        else
        {
            if (str.length()<MIN_LENGTH)
            {
                errors.reject("field.MiN_LENGTH", "Minimal length of field "+target+" "+MIN_LENGTH);
            }
            else
            {
                if (str.length()>MAX_LENGTH)
                    errors.reject("field.NAX_LENGTH", "Maximal length of field "+target+" "+MAX_LENGTH);
                if (numOfSpecialCharactersInString(str)!=0)
                    errors.reject("field.specialCharacters", "field "+target+" shouldn't special characters");
                if (str.contains(" "))
                    errors.reject("field.space", "field "+target+" shouldn't contain empty space");
                if (numOfDigitsInString(str)!=0)
                    errors.reject("field.numOfDigits", "field "+target+" should contain at least 4 digits");
            }
        }
    }
    
    private int numOfDigitsInString(String string){
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
    
    private int numOfSpecialCharactersInString(String string){
        int counter=0;
        for (int i=0;i<string.length();i++){
            if (!(Character.isLetter(string.charAt(i))) && !(Character.isDigit(string.charAt(i))))
                counter++;
        }
        return counter;
    }
    
}
