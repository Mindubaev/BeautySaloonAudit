/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.DAO.Service;

import com.mycompany.beautysaloonaudit.Entities.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Artur
 */
public interface ServiceRepository extends CrudRepository<Service, Long>{
    
    Optional<List<Service>> findByIsDeprecated(boolean isDeprecated);
    
}
