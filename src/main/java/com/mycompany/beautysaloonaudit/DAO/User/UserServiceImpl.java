/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.DAO.User;

import com.google.common.collect.Lists;
import com.mycompany.beautysaloonaudit.Entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return Lists.newArrayList(userRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByFirstnameAndLastname(String firstname, String lastname) {
        Optional<List<User>> optional=userRepository.findByFirstnameAndLastname(firstname, lastname);
        if (optional.isPresent() && optional.get().size()>0)
            return optional.get();
        else
            return new ArrayList<User>();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        Optional<User> optional=userRepository.findByLoginAndPassword(login, password);
        if (optional.isPresent())
            return optional.get();
        else
            return null;
    }
    
}
