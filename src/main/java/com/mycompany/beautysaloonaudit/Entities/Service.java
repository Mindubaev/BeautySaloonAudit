
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

@Table(name = "Service")
@Entity(name = "service")
public class Service implements Serializable{
    
    private Long id;
    private String name;
    private String description;
    private int duration;
    private int price;
    private int version;
    private List<Order> orders;
    private boolean isDeprecated;

    public Service() {
    }

    public Service(String name, String description, int duration, int price) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    @Column(name = "duration")
    public int getDuration() {
        return duration;
    }

    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    @Column(name = "version")
    @Version
    public int getVersion() {
        return version;
    }

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "service",cascade = CascadeType.ALL,orphanRemoval = true)
    public List<Order> getOrders() {
        return orders;
    }

    @Column(name="isDeprecated")
    public boolean getIsDeprecated() {
        return isDeprecated;
    }

    public void setIsDeprecated(boolean isDeprecated) {
        this.isDeprecated = isDeprecated;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return getId()+" "+getName()+" "+getPrice()+"р "+getDuration()+"мин :"+getDescription();
    }
    
}
