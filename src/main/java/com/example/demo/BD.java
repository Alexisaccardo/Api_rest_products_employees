package com.example.demo;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BD {
    public BD() {
    }

    String error_employee = "No se puedo registrar el empleado";
    String error_edit = "No se puedo editar el empleado";
    String date_employe = "La fecha especificada excede el limite permitido.";
    String edit_products = "No se pudo editar el producto";
    String error_products = "No se pudo registrar el producto";
    String search_products = "La fecha especificada excede el limite permitido.";
    public Employee Register(String code, String name, String date_contract, String cellphone) {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/product";
        String username = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");

            // Sentencia INSERT
            String sql = "INSERT INTO employee (code , name, date_contract, cellphone) VALUES (?, ?, ?, ?)";

            // Preparar la sentencia
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, date_contract);
            preparedStatement.setString(4, cellphone);

            // Ejecutar la sentencia
            int files = preparedStatement.executeUpdate();

            if (files > 0) {
                System.out.println("Empleado registrado de manera exitosa.");
                return new Employee(code, name, date_contract, cellphone);
            } else {
                System.out.println(error_employee);
            }

            preparedStatement.close();
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Employee(null, null, null, null);

    }

    public Employee Edit(String code_employee, String name, String date_contract, String cellphone) throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/product";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        String consult = "UPDATE employee SET name = ?, date_contract = ?, cellphone = ? WHERE code = ?";

        PreparedStatement preparedStatement = connection2.prepareStatement(consult);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, date_contract);
        preparedStatement.setString(3, cellphone);
        preparedStatement.setString(4, code_employee);

        int files = preparedStatement.executeUpdate();
        if (files > 0) {
            System.out.println("Empleado actualizado de manera exitosa");
            return new Employee(code_employee, name, date_contract, cellphone);
        } else {
            System.out.println(edit_products);
        }
        preparedStatement.close();
        connection2.close();
        return new Employee(null, null, null, null);
    }

    public List<Employee> Search() throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/product";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM employee");

        List<Employee> list = new ArrayList<>();

        while (resultSet2.next()) {
            String code = resultSet2.getString("code");
            String name = resultSet2.getString("name");
            String date_contract = resultSet2.getString("date_contract");
            String cellphone = resultSet2.getString("cellphone");

            Employee employee = new Employee(code, name, date_contract, cellphone);
            list.add(employee);
        }

        return list;
    }

    public Products Edit_products(String code, String name, String expiration, String description) throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/product";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        String consult = "UPDATE products SET name = ?, expiration = ?, description = ? WHERE code = ?";

        PreparedStatement preparedStatement = connection2.prepareStatement(consult);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, expiration);
        preparedStatement.setString(3, description);
        preparedStatement.setString(4, code);

        int files = preparedStatement.executeUpdate();
        if (files > 0) {
            System.out.println("Operador actualizado de manera exitosa");
            return new Products(code, name, null, expiration, description);
        } else {
            System.out.println(error_edit);
        }
        preparedStatement.close();
        connection2.close();
        return new Products(null, null, null, null, null);
    }

    public String Select_employee(String prod_employee) throws ClassNotFoundException, SQLException, ParseException {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/product";
        String username = "root";
        String password = "";

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);

        String consultaSQL = "SELECT * FROM employee WHERE code = ?";

        PreparedStatement statement = connection.prepareStatement(consultaSQL);
        statement.setString(1, prod_employee); // Establecer el valor del parámetro

        // Ejecutar la consulta
        ResultSet resultSet = statement.executeQuery();

        // Procesar el resultado si existe
        if (resultSet.next()) {
            String code = resultSet.getString("code");
            String name = resultSet.getString("name");
            String date_contract = resultSet.getString("date_contract");
            String cellphone = resultSet.getString("cellphone");
            long milliseconds = System.currentTimeMillis();
            Date date_now = new Date(milliseconds);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date_expiration = format.parse(date_contract);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date_expiration);
            calendar.add(Calendar.DAY_OF_YEAR, 30);
            java.util.Date new_date_expiration = calendar.getTime();
            if (date_now.compareTo(new_date_expiration)>0) {
            return code;
            } else {
            System.out.println(date_employe);
            }

            System.out.println("Estes es el codigo del empleado: " + code + " Nombre: " + name + " Fecha de contrato: " + date_contract + " Por un valor de: " + cellphone);

        }

        // Cerrar recursos
        resultSet.close();
        statement.close();
        connection.close();

        return "";
    }

    public Products Register_products(String code, String name, String prod_employee, String expiration, String description) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/product";
        String username = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");

            // Sentencia INSERT
            String sql = "INSERT INTO products (code , name, prod_employee, expiration, description) VALUES (?, ?, ?, ?, ?)";

            // Preparar la sentencia
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, prod_employee);
            preparedStatement.setString(4, expiration);
            preparedStatement.setString(5, description);

            // Ejecutar la sentencia
            int files = preparedStatement.executeUpdate();

            if (files > 0) {
                System.out.println("Empleado registrado de manera exitosa.");
                return new Products(code, name, prod_employee, expiration, description);
            } else {
                System.out.println(error_products);
            }

            preparedStatement.close();
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Products(null, null, null, null, null);
    }

    public List<Products> Search_products() throws ClassNotFoundException, SQLException, ParseException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/product";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM products");

        List<Products> list = new ArrayList<>();

        while (resultSet2.next()) {
            String code = resultSet2.getString("code");
            String name = resultSet2.getString("name");
            String prod_employee = resultSet2.getString("prod_employee");
            String expiration = resultSet2.getString("expiration");
            String description = resultSet2.getString("description");
            long milliseconds = System.currentTimeMillis();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date_expiration = format.parse(expiration);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date_expiration);
            long difference_In_Milliseconds = calendar.getTimeInMillis() - milliseconds;
            long difference_In_Days = difference_In_Milliseconds / (24 * 60 * 60 * 1000);
            if (difference_In_Days >= 1 && difference_In_Days <= 30){
                Products products = new Products(code, name, prod_employee, expiration, description);
                list.add(products);
            } else {
                System.out.println(search_products);    }
            }

            return list;
        }
    }





