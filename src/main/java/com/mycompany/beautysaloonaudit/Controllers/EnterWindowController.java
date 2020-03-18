package com.mycompany.beautysaloonaudit.Controllers;

import com.mycompany.beautysaloonaudit.DAO.User.UserService;
import com.mycompany.beautysaloonaudit.MainApp;
import com.mycompany.beautysaloonaudit.SpringConfig.Root;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

public class EnterWindowController implements Initializable {
    
    @FXML
    private Label infoLabel;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    private Stage primaryStage;
    @Qualifier("passwordValidator")
    @Autowired
    private Validator passwordValidator=MainApp.context.getBean("passwordValidator",Validator.class);
    @Qualifier("loginValidator")
    @Autowired
    private Validator loginValidator=MainApp.context.getBean("loginValidator",Validator.class);
    
    @FXML
    private void enterToSystemHendler(ActionEvent event) {
        String login=loginField.getText();
        String password=passwordField.getText();
        Errors errors=new MapBindingResult(new HashMap<String,String>(), "loginForm");
        loginValidator.validate(login, errors);
        passwordValidator.validate(password, errors);
        if (errors.hasErrors())
        {
            String info="";
            List<ObjectError> exList=errors.getAllErrors();
            for (ObjectError error:exList)
                info=info+"\n"+error.getDefaultMessage();
            infoLabel.setText(info);
        }
        else
        {
            MainApp.user=MainApp.userService.findByLoginAndPassword(login, password);
            if (MainApp.user!=null)
                ChangeSceneToMainWindow();
            else
                infoLabel.setText("incorrect login or password");
        }
//        if (login!=null && !login.equals("")&& password!=null && !password.equals(""))
//        {
//            MainApp.user=MainApp.userService.findByLoginAndPassword(login, password);
//            if (MainApp.user!=null)
//                ChangeSceneToMainWindow();
//            else
//                infoLabel.setText("Некоректные логин и пароль");
//        }
//        else
//            infoLabel.setText("Заполните поля ввода данных");
    }
    
    @FXML
    private void invokeRegistrationWindowHendler(){
        invokeRegistrationWindow();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    private void ChangeSceneToMainWindow() {
        try {
            Stage stage=new Stage();
            FXMLLoader fXMLLoader=new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("/fxml/MainMenuWindowScene.fxml"));
            Parent root=fXMLLoader.load();
            MainMenuWindowSceneController controller=fXMLLoader.getController();
            controller.setPrimaryStage(stage);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/logo.png")));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            stage.setTitle("Главное меню");
            stage.setScene(scene);
            stage.setHeight(720);
            stage.setWidth(1280);
            stage.show();
            stage.setResizable(false);
            getPrimaryStage().close();
        } catch (IOException ex) {
            System.err.println("catch error during loading MainMenuWindowScene.fxml:");
            System.err.println(ex.getMessage());
        }
    }

    private void invokeRegistrationWindow() {
        try {
            Stage stage=new Stage();
            FXMLLoader fXMLLoader=new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("/fxml/RegistrationWindowScene.fxml"));
            Parent root=fXMLLoader.load();
            RegistrationWindowController controller=fXMLLoader.getController();
            controller.setPrimaryStage(stage);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/logo.png")));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            stage.setTitle("Регистрация");
            stage.setScene(scene);
            stage.setHeight(500);
            stage.setWidth(400);
            stage.initOwner(primaryStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
            stage.setResizable(false);
        } catch (IOException ex) {
            System.err.println("catch error during loading RegistarionWindowScene.fxml:");
            System.err.println(ex.getMessage());
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage aPrimaryStage) {
        primaryStage = aPrimaryStage;
    }
    
}
