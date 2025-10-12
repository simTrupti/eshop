package com.eshop.eshop.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;
     String name;
     String email;
     String password;
     String address;
     String phone;

     @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
     private List<Order> orders;

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

    public String getEmail(){
        return email;
    }
    public void setEmail(String newemail){
        if(newemail != null && !newemail.isEmpty()){
            this.email = newemail;
        }else {
            System.out.println("Enter your email");
        }
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String newpassword){
        if(newpassword != null && !newpassword.isEmpty()){
            this.password = newpassword;
        }else {
            System.out.println("Enter password");
        }
    }

    public String getAddress(){
        return address;
    }
    public void setAddress(String newaddress){
        if(newaddress != null && !newaddress.isEmpty()){
            this.address = newaddress;
        }else {
            System.out.println("Enter your address");
        }
    }

    public String getPhone(){
        return phone;
    }
    public void setPhone(String newaphone){
        if(newaphone != null && !newaphone.isEmpty()){
            this.phone = newaphone;
        }else {
            System.out.println("Enter your phone number");
        }
    }


    public int getId(){
        return id;
    }
    public int setId(int newId){
            this.id = newId;
        return newId;
    }
}
