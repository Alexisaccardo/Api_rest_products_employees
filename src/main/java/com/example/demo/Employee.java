package com.example.demo;

public class Employee {

    public String code_employee;
    public String name;
    public String date_contract;
    public String cellphone;

    public Employee(String code_employee, String name, String date_contract, String cellphone) {
        this.code_employee = code_employee;
        this.name = name;
        this.date_contract = date_contract;
        this.cellphone = cellphone;
    }

    public String getCode_employee() {
        return code_employee;
    }

    public void setCode_employee(String code_employee) {
        this.code_employee = code_employee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_contract() {
        return date_contract;
    }

    public void setDate_contract(String date_contract) {
        this.date_contract = date_contract;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}
