/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.DAO.Order;

import com.google.common.collect.Lists;
import com.mycompany.beautysaloonaudit.Entities.Order;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Artur
 */
@Repository
@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Order> findByDateBetween(DateTime start, DateTime end) {
        Optional<List<Order>> optional=orderRepository.findByDateBetween(start, end);
        if (optional.isPresent())
            return optional.get();
        else
            return new ArrayList<Order>();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return Lists.newArrayList(orderRepository.findAll());
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }
    
}
