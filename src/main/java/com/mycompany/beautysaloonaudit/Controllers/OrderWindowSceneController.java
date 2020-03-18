
package com.mycompany.beautysaloonaudit.Controllers;

import com.mycompany.beautysaloonaudit.DAO.Service.CatalogService;
import com.mycompany.beautysaloonaudit.Entities.Order;
import com.mycompany.beautysaloonaudit.Entities.Service;
import com.mycompany.beautysaloonaudit.MainApp;
import com.mycompany.beautysaloonaudit.TimeInterval;
import com.mycompany.beautysaloonaudit.TimeManager;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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

public class OrderWindowSceneController implements Initializable, Controller {

    private Stage primaryStage;
    @FXML
    private TableView<Service> serviceTable;
    @FXML
    private TableColumn<Service, String> serviceName;
    @FXML
    private TableColumn<Service, Integer> servicePrice;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextArea timeTableTextArea;
    @FXML
    private Label infoLabel;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField hourField;
    @FXML
    private TextField minuteField;
    @FXML
    private TextField searchField;
    private ObservableList<Service> allservices;
    private ObservableList<Service> serviceList;
    private final LocalTime START_OF_WORKINGDAY = new LocalTime(9, 0, 0);
    private final LocalTime END_OF_WORKINGDAY = new LocalTime(18, 30, 0);
    private TimeManager timeManager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serviceName.setCellValueFactory(new PropertyValueFactory<Service, String>("name"));
        servicePrice.setCellValueFactory(new PropertyValueFactory<Service, Integer>("price"));
        this.serviceList = getServiceListFromDatabase(MainApp.catalogService);
        fillTableByServiceList(this.serviceList, serviceTable);
        serviceTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue != null) {
                    String info = ((Service) newValue).getDescription() + "\nДлительность:" + ((Service) newValue).getDuration();
                    descriptionTextArea.setText(info);
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
        searchField.textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String str = (String) newValue;
                if (str != null) {
                    serviceList = CatalogService.filterServiceList(str, allservices, serviceList);
                    fillTableByServiceList(serviceList, serviceTable);
                }
            }

        });
    }

    private ObservableList<Service> getServiceListFromDatabase(CatalogService catalogService) {
        List<Service> list = catalogService.findByIsDeprecated(false);
        allservices = FXCollections.observableArrayList();
        allservices.addAll(list);
        return FXCollections.observableArrayList(list);
    }

    private void fillTableByServiceList(ObservableList<Service> list, TableView table) {
        table.setItems(list);
        table.setVisible(false);
        table.refresh();
        table.setVisible(true);
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
                DateTime bookedDate = order.getDate();
                int duration = order.getService().getDuration();
                TimeInterval bookedInterval = new TimeInterval(bookedDate, bookedDate.plusMinutes(duration));
                timeManager.addBookedTimeInterval(bookedInterval);
            }
        }
        return timeManager.toString();
    }

    @FXML
    private void createOrderHendler() {
        String hour = hourField.getText();
        String minute = minuteField.getText();
        LocalDate date = datePicker.getValue();
        Service service = serviceTable.getSelectionModel().getSelectedItem();
        if (date != null && service != null && isValidTime(hour, minute, new DateTime(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 0, 0), service.getDuration(), timeManager)) {
            Order order = new Order(new DateTime(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), Integer.parseInt(hour), Integer.parseInt(minute)), MainApp.user, service);
            MainApp.orderService.save(order);
            infoLabel.setText("Заказ успешно создан");
            hourField.setText("");
            minuteField.setText("");
        } else {
            infoLabel.setText("Некорректный ввод данных");
        }
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

    @Override
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    @FXML
    private void closeHendler(){
        getPrimaryStage().close();
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

}
