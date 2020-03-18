/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.DAO.Service;

import com.mycompany.beautysaloonaudit.Entities.Service;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Artur
 */
public interface CatalogService {
    
    public List<Service> findAll();
    public List<Service> findByIsDeprecated(boolean isDeprecated);
    public Service save(Service service);
    public void delete(Service service);
    public static ObservableList<Service> filterServiceList(String str, ObservableList<Service> allServices, ObservableList<Service> filtredList) {
        filtredList.clear();
        if (str.equals("")) {
            filtredList.addAll(allServices);
        } else {
            for (Service service : allServices) {
                String info = service.toString();
                if (info.contains(str)) {
                    filtredList.add(service);
                }
            }
        }
        return filtredList;
    }
    
}
