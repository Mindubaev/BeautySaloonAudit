package com.mycompany.beautysaloonaudit;

import com.mycompany.beautysaloonaudit.Controllers.EnterWindowController;
import com.mycompany.beautysaloonaudit.DAO.Order.OrderService;
import com.mycompany.beautysaloonaudit.DAO.Service.CatalogService;
import com.mycompany.beautysaloonaudit.DAO.User.UserService;
import com.mycompany.beautysaloonaudit.Entities.User;
import com.mycompany.beautysaloonaudit.SpringConfig.Root;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class MainApp extends Application {

    public static ApplicationContext context=new AnnotationConfigApplicationContext(Root.class);
    public static UserService userService=context.getBean("userService",UserService.class);
    public static OrderService orderService=context.getBean("orderService",OrderService.class);
    public static CatalogService catalogService=context.getBean("catalogService",CatalogService.class);
    public static User user;
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fXMLLoader=new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/fxml/EnterWindowScene.fxml"));
        Parent root=fXMLLoader.load();
        EnterWindowController controller=fXMLLoader.getController();
        controller.setPrimaryStage(stage);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/logo.png")));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");        
        stage.setTitle("Вход в аккаунт");
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(400);
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
