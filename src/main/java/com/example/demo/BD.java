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

    public String Select_employee(String prod_employee) throws ClassNotFoundException, SQLException {

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
            long milisegundos = System.currentTimeMillis();
            Date fechaActual = new Date(milisegundos);
            String cellphone = resultSet.getString("cellphone");

            if (!fechaActual.equals("")) {
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
            long milisegundos = System.currentTimeMillis();
            Date fechaActual = new Date(milisegundos);
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fechaexpiration = formato.parse(expiration);
            Calendar calendar = Calendar.getInstance();calendar.setTime(fechaexpiration);// Agregar 30 días a la fecha de expiracióncalendar.add(Calendar.DAY_OF_YEAR, 30);
            java.util.Date nuevaFechaExpiration = calendar.getTime();
            fechaActual.compareTo(fechaexpiration);
            String description = resultSet2.getString("description");

            if (fechaActual.compareTo(nuevaFechaExpiration)<0){
                Products products = new Products(code, name, prod_employee, expiration, description);
                list.add(products);
            } else {
                System.out.println(search_products);
            }
        }
            return list;
        }
    }





