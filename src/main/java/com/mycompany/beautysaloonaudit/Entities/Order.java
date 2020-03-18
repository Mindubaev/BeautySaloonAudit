
package com.mycompany.beautysaloonaudit.Entities;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
//import org.jadira.usertype.dateandtime.joda.Persistent

@Table(name = "orders")
@Entity(name = "order")
public class Order implements Serializable{
    
    private Long id;
    private DateTime date;
    private User user;
    private Service service;
    private int version;

    public Order() {
    }

    public Order(DateTime date, User user, Service service) {
        this.date = date;
        this.user = user;
        this.service = service;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public Long getId() {
        return id;
    }

    @Column(name="date")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime getDate() {
        return date;
    }
    
    @Transient
    public String getStringDate(){
        return date.toString();
    }
    
    @Transient
    public String getServiceName(){
        return getService().getName();
    }

    @ManyToOne
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    @ManyToOne
    @JoinColumn(name = "serviceId")
    public Service getService() {
        return service;
    }

    @Column(name="version")
    @Version
    public int getVersion() {
        return version;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    @Override
    public String toString() {
        return getId()+" "+getDate()+" "+getService().getName()+" ";
    }
    
}
