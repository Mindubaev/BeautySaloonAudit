/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.DAO.Order;

import com.mycompany.beautysaloonaudit.Entities.Order;

import java.util.List;
import java.util.Optional;
import org.joda.time.DateTime;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Artur
 */
public interface OrderRepository extends CrudRepository<Order, Long>{
    
    public Optional<List<Order>> findByDateBetween(DateTime start,DateTime end);
    
}
