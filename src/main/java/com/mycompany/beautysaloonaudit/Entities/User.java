
package com.mycompany.beautysaloonaudit.Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Table(name = "SaloonUser")
@Entity(name = "user")
public class User implements Serializable{
    
    private Long id;
    private String firstname;
    private String lastname;
    private String login;
    private String password;
    private int version;
    private boolean isAdmin;
    private List<Order> orders;

    public User() {
    }

    public User(String firstname, String lastname, String login, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name="firstname")
    public String getFirstname() {
        return firstname;
    }

    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    @Column(name = "version")
    @Version
    public int getVersion() {
        return version;
    }

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Column(name = "isAdmin")
    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return getId()+" "+getFirstname()+" "+getLastname()+" "+getLogin()+" "+getPassword()+" ["+getOrders().toString()+"]";
    }
    
}
