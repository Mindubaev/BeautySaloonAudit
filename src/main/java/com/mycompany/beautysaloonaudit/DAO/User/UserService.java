
package com.mycompany.beautysaloonaudit.DAO.User;

import com.mycompany.beautysaloonaudit.Entities.User;
import java.util.List;

public interface UserService {
    
    List<User> findAll();
    List<User> findByFirstnameAndLastname( String firstname,String lastname);
    User findByLoginAndPassword(String login,String password);
    User save(User user);
    void delete(User user);
    
}
