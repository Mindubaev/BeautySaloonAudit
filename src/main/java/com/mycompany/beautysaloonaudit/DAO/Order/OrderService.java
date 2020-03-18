
package com.mycompany.beautysaloonaudit.DAO.Order;

import com.mycompany.beautysaloonaudit.Entities.Order;
import java.util.List;
import org.joda.time.DateTime;

public interface OrderService {
    
    public List<Order> findByDateBetween(DateTime start,DateTime end);
    public List<Order> findAll();
    public void delete(Order order);
    public Order save(Order order);
    
}
