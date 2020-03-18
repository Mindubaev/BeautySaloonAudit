/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.Controllers;

import com.mycompany.beautysaloonaudit.DAO.Order.OrderService;
import com.mycompany.beautysaloonaudit.DAO.User.UserService;
import com.mycompany.beautysaloonaudit.Entities.Order;
import com.mycompany.beautysaloonaudit.Entities.User;
import com.mycompany.beautysaloonaudit.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class OrderHistoryWindowSceneController implements Initializable, Controller {

    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, Long> orderId;
    @FXML
    private TableColumn<Order, String> orderDate;
    @FXML
    private TableColumn<Order, String> serviceName;
    @FXML
    private TextField searchField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Label priceLabel;
    @FXML
    private Label durationLabel;

    private ObservableList<Order> orderList;
    private ObservableList<Order> allOrders;
    private Stage primaryStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orderId.setCellValueFactory(new PropertyValueFactory<Order, Long>("id"));
        orderDate.setCellValueFactory(new PropertyValueFactory<Order, String>("stringDate"));
        serviceName.setCellValueFactory(new PropertyValueFactory<Order, String>("serviceName"));
        this.orderList = getOrderListFromDatabase(MainApp.user, MainApp.userService);
        fillTableByOrderList(orderList, orderTable);
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    orderList = filterOrderList(newValue, allOrders, orderList);
                    fillTableByOrderList(orderList, orderTable);
                }
            }

        });
        orderTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Order>() {
            @Override
            public void changed(ObservableValue<? extends Order> observable, Order oldValue, Order newValue) {
                if (newValue != null) {
                    priceLabel.setText(String.valueOf(newValue.getService().getPrice()));
                    durationLabel.setText(String.valueOf(newValue.getService().getDuration()));
                    descriptionTextArea.setText(newValue.getService().getDescription());
                } else {
                    priceLabel.setText("");
                    durationLabel.setText("");
                    descriptionTextArea.setText("");
                }
            }
        });
    }

    @FXML
    private void closeHendler() {
        getPrimaryStage().close();
        try {
            Stage stage = new Stage();
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("/fxml/MainMenuWindowScene.fxml"));
            Parent root = fXMLLoader.load();
            MainMenuWindowSceneController controller = fXMLLoader.getController();
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

    private ObservableList<Order> getOrderListFromDatabase(User user, UserService service) {
        user = service.findByLoginAndPassword(user.getLogin(), user.getPassword());
        List<Order> list = user.getOrders();
        allOrders = FXCollections.observableArrayList();
        allOrders.addAll(list);
        return FXCollections.observableArrayList(list);
    }

    private void fillTableByOrderList(ObservableList<Order> list, TableView table) {
        table.setItems(list);
        table.setVisible(false);
        table.refresh();
        table.setVisible(true);
    }

    private ObservableList<Order> filterOrderList(String str, ObservableList<Order> allOrders, ObservableList<Order> filtredList) {
        filtredList.clear();
        if (str.equals("")) {
            filtredList.addAll(allOrders);
        } else {
            for (Order order : allOrders) {
                String info = order.toString();
                if (info.contains(str)) {
                    filtredList.add(order);
                }
            }
        }
        return filtredList;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}
