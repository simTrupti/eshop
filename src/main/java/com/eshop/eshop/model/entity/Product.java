package com.eshop.eshop.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    String name;
    String description;
    Double price;
    int quantity;
    String category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String newName){
        if(newName != null && !newName.isEmpty()){
            this.name = newName;
        }else {
            System.out.println("Enter Name");
        }
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String newdescription){
        if(newdescription != null && !newdescription.isEmpty()){
            this.description = newdescription;
        }else {
            System.out.println("Give description");
        }
    }

    public double getPrice(){
        return price;
    }
    public void  setPrice(double newPrice){
        this.price = newPrice;

    }

    public int getQuantity(){
        return quantity;
    }
    public void  setQuantity(int newQuantity){
        this.quantity = newQuantity;

    }

    public String getCategory(){
        return category;
    }
    public void setCategory(String newcategory){
        if(newcategory != null && !newcategory.isEmpty()){
            this.category = newcategory;
        }else {
            System.out.println("Category");
        }
    }
}
