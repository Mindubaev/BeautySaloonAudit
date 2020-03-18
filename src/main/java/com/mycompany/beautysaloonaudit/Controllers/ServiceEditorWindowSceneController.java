package com.mycompany.beautysaloonaudit.Controllers;

import com.mycompany.beautysaloonaudit.DAO.Service.CatalogService;
import com.mycompany.beautysaloonaudit.Entities.Service;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ServiceEditorWindowSceneController implements Initializable, Controller {

    @FXML
    private TableView<Service> serviceTable;
    @FXML
    private TableColumn<Service, String> serviceName;
    @FXML
    private TableColumn<Service, Integer> servicePrice;
    @FXML
    private TableColumn<Service, Integer> serviceDuration;
    @FXML
    private TextField searchField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField durationField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Label infoLabel;
    @FXML
    private CheckBox isDeprecatedCheckBox;

    private Stage primaryStage;
    private ObservableList<Service> allservices;
    private ObservableList<Service> serviceList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serviceName.setCellValueFactory(new PropertyValueFactory<Service, String>("name"));
        servicePrice.setCellValueFactory(new PropertyValueFactory<Service, Integer>("price"));
        serviceDuration.setCellValueFactory(new PropertyValueFactory<Service, Integer>("duration"));
        this.serviceList = getServiceListFromDatabase(MainApp.catalogService);
        fillTableByServiceList(this.serviceList, serviceTable);
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
        serviceTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Service>() {
            @Override
            public void changed(ObservableValue observable, Service oldValue, Service newValue) {
                if (newValue != null) {
                    descriptionTextArea.setText(newValue.getDescription());
                    priceField.setText(String.valueOf(newValue.getPrice()));
                    durationField.setText(String.valueOf(newValue.getDuration()));
                    nameField.setText(newValue.getName());
                    isDeprecatedCheckBox.setSelected(newValue.getIsDeprecated());
                } else {
                    descriptionTextArea.setText("");
                    priceField.setText("");
                    durationField.setText("");
                    nameField.setText("");
                    isDeprecatedCheckBox.setSelected(false);
                }
            }
        });
    }

    private ObservableList<Service> getServiceListFromDatabase(CatalogService catalogService) {
        List<Service> list = catalogService.findAll();
        allservices = FXCollections.observableArrayList();
        allservices.addAll(list);
        return FXCollections.observableArrayList(list);
    }

    private void fillTableByServiceList(ObservableList<Service> list, TableView table) {
        table.setItems(list);
        table.setVisible(false);
        //table.refresh();
        table.setVisible(true);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
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
    private void deleteServiceHendler(){
        Service service=serviceTable.getSelectionModel().getSelectedItem();
        if (service!=null)
        {
            try{
            MainApp.catalogService.delete(service);
            infoLabel.setText("услуга успешно удалена");
            allservices.remove(service);
            serviceList.remove(service);
            }catch(Exception ex){
                infoLabel.setText("Удаление невозможно:заказы ссылаются на данную услугу");
            }
        }
        else
            infoLabel.setText("Выберетк услугу для удаления");
    }

    @FXML
    private void saveNewServiceHendler() {
        String name = nameField.getText();
        String priceStr = priceField.getText();
        String durationStr = durationField.getText();
        String description = descriptionTextArea.getText();
        boolean isDepricated=isDeprecatedCheckBox.isSelected();
        Service savedService =saveService(MainApp.catalogService, null, name, priceStr, durationStr, description, isDepricated,infoLabel);
        serviceList=getServiceListFromDatabase(MainApp.catalogService);
        fillTableByServiceList(serviceList, serviceTable);
    }
    
    @FXML
    private void editServiceHendler() {
        String name = nameField.getText();
        String priceStr = priceField.getText();
        String durationStr = durationField.getText();
        String description = descriptionTextArea.getText();
        boolean isDepricated=isDeprecatedCheckBox.isSelected();
        Service service=serviceTable.getSelectionModel().getSelectedItem();
        if (service!=null)
        {
            saveService(MainApp.catalogService, service, name, priceStr, durationStr, description,isDepricated, infoLabel);
            serviceList=getServiceListFromDatabase(MainApp.catalogService);
            fillTableByServiceList(serviceList, serviceTable);
        }
        else
            infoLabel.setText("Не выбрана услуга для изменения");
    }
    
    private Service saveService(CatalogService catalogService,Service service,String name,String priceStr,String durationStr,String description,boolean isDepricated,Label infoLabel){
        if (service==null)
            service=new Service();
        if (!name.equals("") && !priceStr.equals("") && !durationStr.equals("") && !description.equals("") && isNum(priceStr) && isNum(durationStr) && Integer.parseInt(priceStr)>0 && Integer.parseInt(durationStr)>0) {
            service.setName(name);
            service.setDescription(description);
            service.setPrice(Integer.parseInt(priceStr));
            service.setDuration(Integer.parseInt(durationStr));
            service.setIsDeprecated(isDepricated);
            catalogService.save(service);
            infoLabel.setText("Услуга успешно сохранена");
            return service;
        }
        else
        {
            infoLabel.setText("Некорректный ввод данных");
            return null;
        }
    }

    private static boolean isNum(String str) {
        try {
            int i = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

}
