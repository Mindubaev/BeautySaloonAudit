/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.Controllers;

import com.mycompany.beautysaloonaudit.Entities.User;
import com.mycompany.beautysaloonaudit.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainMenuWindowSceneController implements Initializable,Controller {

    private Stage primaryStage;
    @FXML
    private Button ServiceEditButton;
    @FXML
    private Button OrderListButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ckeckAdmineRules(MainApp.user);
    }    

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void ckeckAdmineRules(User user) {
        if (!user.isIsAdmin())
        {
            ServiceEditButton.setDisable(true);
            OrderListButton.setDisable(true);
            ServiceEditButton.setVisible(false);
            OrderListButton.setVisible(false);
        }
    }
    
    @FXML
    private void changeSceneToOrderWindowHendler(){
        changeWindow("OrderWindowScene.fxml","Услуги",new OrderWindowSceneController());
    }
    
    @FXML
    private void changeSceneToOrderHistoryWindowHendler(){
        changeWindow("OrderHistoryWindowScene.fxml","История заказов",new OrderHistoryWindowSceneController());
    }
    
    @FXML
    private void changeSceneToServiceEditWindowHendler(){
        changeWindow("ServiceEditorWindowScene.fxml", "Перечень услуг", new ServiceEditorWindowSceneController());
    }
    
    @FXML
    private void changeSceneToOrderListHendler(){
        changeWindow("OrderListWindowScene.fxml", "Перечень заказов", new OrderListWindowSceneController());
    }
    
    @FXML
    private void closeHendler(){
        getPrimaryStage().close();
    }
    
    private void changeWindow(String fxmlName,String windowTitle,Controller controller){
        try {
            Stage stage=new Stage();
            FXMLLoader fXMLLoader=new FXMLLoader();
            fXMLLoader.setController(controller);
            fXMLLoader.setLocation(getClass().getResource("/fxml/"+fxmlName));
            Parent root=fXMLLoader.load();
            controller.setPrimaryStage(stage);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/logo.png")));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            stage.setTitle(windowTitle);
            stage.setScene(scene);
            stage.setHeight(720);
            stage.setWidth(1280);
            stage.show();
            getPrimaryStage().close();
        } catch (IOException ex) {
            System.err.println("catch error during loading "+fxmlName+":");
            System.err.println(ex.getMessage());
        }
    }
    
}
