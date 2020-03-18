/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.DAO.Service;

import com.google.common.collect.Lists;
import com.mycompany.beautysaloonaudit.Entities.Service;
import com.mycompany.beautysaloonaudit.Entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@org.springframework.stereotype.Service("catalogService")
@Transactional
public class CatalogServiceImpl implements CatalogService{

    @Autowired
    ServiceRepository serviceRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Service> findAll() {
        return Lists.newArrayList(serviceRepository.findAll());
    }

    @Override
    public Service save(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public void delete(Service service) {
        serviceRepository.delete(service);
    }

    @Override
    public List<Service> findByIsDeprecated(boolean isDeprecated) {
        Optional<List<Service>> optional=serviceRepository.findByIsDeprecated(isDeprecated);
        if (optional.isPresent() && optional.get().size()>0)
            return optional.get();
        else
            return new ArrayList<Service>();
    }
    
}
