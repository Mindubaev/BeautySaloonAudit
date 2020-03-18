/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.SpringConfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author Artur
 */
@Configuration
@ComponentScan(basePackages = "com.mycompany.beautysaloonaudit")
@Import(DataSource_tx_emf_context.class)
public class Root {
    
}
