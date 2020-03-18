/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.Controllers;

import com.mycompany.beautysaloonaudit.Entities.User;
import com.mycompany.beautysaloonaudit.MainApp;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class RegistrationWindowController implements Initializable {

    private Stage primaryStage;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label infoLabel;
    @Qualifier("passwordValidator")
    @Autowired
    private Validator passwordValidator=MainApp.context.getBean("passwordValidator",Validator.class);
    @Qualifier("loginValidator")
    @Autowired
    private Validator loginValidator=MainApp.context.getBean("loginValidator",Validator.class);
    @Qualifier("userDetailValidator")
    @Autowired
    private Validator textFieldValidator=MainApp.context.getBean("userDetailValidator",Validator.class);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    
    @FXML
    private void registrationHendler(){
        String firstname=firstnameField.getText();
        String lastname=lastnameField.getText();
        String login=loginField.getText();
        String password=passwordField.getText();
        Errors errors=new MapBindingResult(new HashMap<String,String>(), "loginForm");
        loginValidator.validate(login, errors);
        passwordValidator.validate(password, errors);
        textFieldValidator.validate(firstname, errors);
        textFieldValidator.validate(lastname, errors);
        if (errors.hasErrors()){
            String info="";
            List<ObjectError> exList=errors.getAllErrors();
            for (ObjectError error:exList)
                info=info+"\n"+error.getDefaultMessage();
            infoLabel.setText(info);
        }
        else
        {
            User user=new User(firstname, lastname, login, password);
            MainApp.userService.save(user);
            closeWindowHendler();
        }
//        if (!isEmptyString(firstname) && !isEmptyString(lastname) && !isEmptyString(login) && !isEmptyString(password))
//        {
//            User user=new User(firstname, lastname, login, password);
//            MainApp.userService.save(user);
//            closeWindowHendler();
//        }
//        else
//            infoLabel.setText("Некорректный ввод данных");
    }
    @FXML
    private void closeWindowHendler(){
        primaryStage.close();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    private boolean isEmptyString(String str){
        if (str!=null && !str.equals(""))
            return false;
        else
            return true;
    }
    
}
