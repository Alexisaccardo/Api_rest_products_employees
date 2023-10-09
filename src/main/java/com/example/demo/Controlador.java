package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

@RestController
public class Controlador {

    @PostMapping("/register_employee")
    public Employee register_employee(@RequestBody Employee employee) throws SQLException, ClassNotFoundException {

        String code_employee = employee.getCode_employee();
        String name = employee.getName();
        String date_contract = employee.getDate_contract();
        String cellphone = employee.getCellphone();

        if (code_employee == null || code_employee.equals("") || code_employee.length() < 0 || name == null || name.equals("") || name.length() < 0 ||
                date_contract == null || date_contract.equals("") || date_contract.length() < 0 || cellphone == null || cellphone.equals("") ||
                cellphone.length() < 0) {

            return new Employee(null, null, null, null);
        } else {
            BD bd = new BD();
            employee = bd.Register(code_employee, name, date_contract, cellphone);
        }

        return employee;
    }

    @PostMapping("/edit_employee")
    public Employee edit_employee(@RequestBody Employee employee) throws SQLException, ClassNotFoundException {

        String code_employee = employee.getCode_employee();
        String name = employee.getName();
        String date_contract = employee.getDate_contract();
        String cellphone = employee.getCellphone();

        if (code_employee == null || code_employee.equals("") || code_employee.length() < 0 || name == null || name.equals("") || name.length() < 0 ||
                date_contract == null || date_contract.equals("") || date_contract.length() < 0 || cellphone == null || cellphone.equals("") ||
                cellphone.length() < 0) {

            return new Employee(null, null, null, null);
        } else {
            BD bd = new BD();
            employee = bd.Edit(code_employee, name, date_contract, cellphone);
        }
        return employee;
    }

    @GetMapping("/search_employee")
    public List<Employee> search_employee() throws SQLException, ClassNotFoundException {
        BD bd = new BD();
        List<Employee> list = bd.Search();

        return list;
    }

    @PostMapping("/register_products")
    public Products register_products(@RequestBody Products products) throws SQLException, ClassNotFoundException, ParseException {

        String code = products.getCode();
        String name = products.getName();
        String prod_employee = products.getProd_employee();
        String expiration = products.getExpiration();
        String description = products.getDescription();

        if (code == null || code.equals("") || code.length() < 0 || name == null || name.equals("") || name.length() < 0 ||
                prod_employee == null || prod_employee.equals("") || prod_employee.length() < 0 || expiration == null || expiration.equals("") ||
                expiration.length() < 0 || description == null || description.equals("") || description.length() < 0) {

            return new Products(null, null, null, null, null);
        } else {
            BD bd = new BD();
            String code_employee = bd.Select_employee(prod_employee);

            if (code_employee.equals("")) {
                return new Products(null, null, "La fecha de inicio no es mayor a 30 días", null, null);
            } else {
                products = bd.Register_products(code, name, prod_employee, expiration, description);
            }
        }
        return products;
    }

    @PostMapping("/edit_product")
    public Products edit_product(@RequestBody Products products) throws SQLException, ClassNotFoundException {

        String code = products.getCode();
        String name = products.getName();
        String prod_employee = products.getProd_employee();
        String expiration = products.getExpiration();
        String description = products.getDescription();

        if (code == null || code.equals("") || code.length() < 0 || name == null || name.equals("") || name.length() < 0 ||
                expiration == null || expiration.equals("") || expiration.length() < 0 || description == null || description.equals("") ||
                description.length() < 0){

            return new Products(null, null, null, null, null);
        } else {
            BD bd = new BD();

            products = bd.Edit_products(code, name, expiration, description);

        }
        return products;
    }

    @GetMapping("/search_products")
    public List<Products> search_products() throws SQLException, ClassNotFoundException, ParseException {
        BD bd = new BD();
        List<Products> list = bd.Search_products();
        if (list.isEmpty()){
            Products products = new Products(null, null, null, "La fecha de expiración supera los 30 días", null);
            list.add(products);
        }
        return list;
    }
}

