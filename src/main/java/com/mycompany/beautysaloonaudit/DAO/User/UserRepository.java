
package com.mycompany.beautysaloonaudit.DAO.User;

import com.mycompany.beautysaloonaudit.Entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{
    
    Optional<List<User>> findByFirstnameAndLastname( String firstname,String lastname);
    Optional<User> findByLoginAndPassword(String login,String password);
    
}
