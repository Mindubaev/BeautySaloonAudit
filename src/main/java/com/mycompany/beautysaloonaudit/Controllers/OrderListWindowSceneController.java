/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.Controllers;

import com.mycompany.beautysaloonaudit.DAO.Order.OrderService;
import com.mycompany.beautysaloonaudit.DAO.Service.CatalogService;
import com.mycompany.beautysaloonaudit.Entities.Order;
import com.mycompany.beautysaloonaudit.Entities.Service;
import com.mycompany.beautysaloonaudit.Entities.User;
import com.mycompany.beautysaloonaudit.MainApp;
import com.mycompany.beautysaloonaudit.TimeInterval;
import com.mycompany.beautysaloonaudit.TimeManager;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

/**
 * FXML Controller class
 *
 * @author Artur
 */
public class OrderListWindowSceneController implements Initializable, Controller {

    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableView<Service> serviceTable;
    @FXML
    private TableColumn<Order, Long> orderId;
    @FXML
    private TableColumn<Order, String> orderDate;
    @FXML
    private TableColumn<Order, String> serviceName;
    @FXML
    private TableColumn<Service, String> serviceNameForServiceTable;
    @FXML
    private TableColumn<Service, Integer> servicePriceForServiceTable;
    @FXML
    private TableColumn<Service, Integer> serviceDurationForServiceTable;
    @FXML
    private TextField searchField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Label priceLabel;
    @FXML
    private Label durationLabel;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField hourField;
    @FXML
    private TextField minuteField;
    @FXML
    private TextArea timeTableTextArea;
    @FXML
    private Label infoLabel;

    private ObservableList<Order> orderList;
    private ObservableList<Order> allOrders;
    private ObservableList<Service> serviceList;
    private Stage primaryStage;
    private final LocalTime START_OF_WORKINGDAY = new LocalTime(9, 0, 0);
    private final LocalTime END_OF_WORKINGDAY = new LocalTime(18, 30, 0);
    private TimeManager timeManager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orderId.setCellValueFactory(new PropertyValueFactory<Order, Long>("id"));
        orderDate.setCellValueFactory(new PropertyValueFactory<Order, String>("stringDate"));
        serviceName.setCellValueFactory(new PropertyValueFactory<Order, String>("serviceName"));
        serviceNameForServiceTable.setCellValueFactory(new PropertyValueFactory<Service, String>("name"));
        servicePriceForServiceTable.setCellValueFactory(new PropertyValueFactory<Service, Integer>("price"));
        serviceDurationForServiceTable.setCellValueFactory(new PropertyValueFactory<Service, Integer>("duration"));
        this.orderList = getOrderListFromDatabase(MainApp.orderService);
        fillTableByList(orderList, orderTable);
        this.serviceList = getServiceListFromDatabase(MainApp.catalogService);
        fillTableByList(serviceList, serviceTable);
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    orderList = filterOrderList(newValue, allOrders, orderList);
                    fillTableByList(orderList, orderTable);
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
                    selectRightElementInServiceList(newValue);
                    fillDateForm(newValue.getDate(), datePicker, hourField, minuteField);
                    timeTableTextArea.setText(getTimeTable(datePicker.getValue()));
                    infoLabel.setText("");
                } else {
                    priceLabel.setText("");
                    durationLabel.setText("");
                    descriptionTextArea.setText("");
                }
            }

            private void selectRightElementInServiceList(Order newValue) {
                Long serviceId = newValue.getService().getId();
                for (Service service : serviceTable.getItems()) {
                    if (serviceId == service.getId()) {
                        serviceTable.getSelectionModel().select(service);
                        break;
                    }
                }
            }
        });
        datePicker.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                LocalDate date = (LocalDate) newValue;
                if (date != null) {
                    timeTableTextArea.setText(getTimeTable(date));
                }
            }
        });
    }

    private String getTimeTable(LocalDate date) {
        DateTime start = new DateTime(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 0, 0);
        DateTime end = start.plusDays(1);
        List<Order> orders = MainApp.orderService.findByDateBetween(start, end);
        List<TimeInterval> workingTime = new ArrayList<>();
        workingTime.add(new TimeInterval(start.withTime(START_OF_WORKINGDAY), start.withTime(END_OF_WORKINGDAY)));
        timeManager = new TimeManager(workingTime);
        if (orders != null) {
            for (Order order : orders) {
                if (order.getId()!=orderTable.getSelectionModel().getSelectedItem().getId()) {
                    DateTime bookedDate = order.getDate();
                    int duration = order.getService().getDuration();
                    TimeInterval bookedInterval = new TimeInterval(bookedDate, bookedDate.plusMinutes(duration));
                    timeManager.addBookedTimeInterval(bookedInterval);
                }
                else
                    System.out.println("");
            }
        }
        return timeManager.toString();
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
    
    @FXML
    private void editOrderHelper(){
       Order selectedOrder=orderTable.getSelectionModel().getSelectedItem();
       Service selectedService=serviceTable.getSelectionModel().getSelectedItem();
       String hour=hourField.getText();
       String minute=minuteField.getText();
       LocalDate date=datePicker.getValue();
       if (selectedOrder!=null && selectedService!=null && !hour.equals("") && !minute.equals("") && isValidTime(hour, minute, new DateTime(date.getYear(),date.getMonthValue(),date.getDayOfMonth(),0,0), selectedService.getDuration(), timeManager))
       {
           selectedOrder.setDate(new DateTime(date.getYear(),date.getMonthValue(),date.getDayOfMonth(),Integer.parseInt(hour),Integer.parseInt(minute)));
           selectedOrder.setService(selectedService);
           MainApp.orderService.save(selectedOrder);
           infoLabel.setText("Заказ успешно изменён");
           this.orderList = getOrderListFromDatabase(MainApp.orderService);
            fillTableByList(orderList, orderTable);
       }
       else
           infoLabel.setText("Некорректный ввод данных");
    }
    
    private boolean isValidTime(String hour, String minute, DateTime dateTime, int duration, TimeManager timeManager) {
        if (hour != null && minute != null) {
            try {
                int h = Integer.parseInt(hour);
                int m = Integer.parseInt(minute);
                if (h < 24 && m <= 60) {
                    DateTime start = dateTime.withTime(h, m, 0, 0);
                    DateTime end = start.plusMinutes(duration);
                    TimeInterval interval = new TimeInterval(start, end);
                    return timeManager.addBookedTimeInterval(interval);
                }
            } catch (NumberFormatException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return false;
    }

    private void fillDateForm(DateTime date, DatePicker datePicker, TextField hourField, TextField minuteField) {
        datePicker.setValue(LocalDate.of(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth()));
        hourField.setText(String.valueOf(date.getHourOfDay()));
        minuteField.setText(String.valueOf(date.getMinuteOfHour()));
    }

    private ObservableList<Order> getOrderListFromDatabase(OrderService orderService) {
        List<Order> list = orderService.findAll();
        allOrders = FXCollections.observableArrayList();
        allOrders.addAll(list);
        return FXCollections.observableArrayList(list);
    }

    private ObservableList<Service> getServiceListFromDatabase(CatalogService catalogService) {
        List<Service> list = catalogService.findAll();
        return FXCollections.observableArrayList(list);
    }

    private void fillTableByList(ObservableList list, TableView table) {
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

    @Override
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void deleteOrderHendler() {
        Order order = orderTable.getSelectionModel().getSelectedItem();
        if (order != null) {
            MainApp.orderService.delete(order);
            allOrders.remove(order);
            orderList.remove(order);
            MainApp.orderService.delete(order);
            infoLabel.setText("Заказ успешно удалён");
        }
        else
            infoLabel.setText("Заказ для удаления не выбран");
    }

}
