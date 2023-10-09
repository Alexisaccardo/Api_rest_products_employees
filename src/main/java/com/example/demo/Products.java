package com.example.demo;

public class Products {
    public String code;
    public String name;
    public String prod_employee;
    public String expiration;
    public String description;

    public Products(String code, String name, String prod_employee, String expiration, String description) {
        this.code = code;
        this.name = name;
        this.prod_employee = prod_employee;
        this.expiration = expiration;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProd_employee() {
        return prod_employee;
    }

    public void setProd_employee(String prod_employee) {
        this.prod_employee = prod_employee;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
