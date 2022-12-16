package com.example.bilabonnementeksamen.repository;

import com.example.bilabonnementeksamen.model.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class EmployeeRepo {

  @Value("${JDBCUrl}")
  private String databaseURL;
  @Value("${JDBCUsername}")
  private String user;
  @Value("${JDBCPassword}")
  private String password;

  public Employee fetchEmployeeById(int id) {

    Employee employee = new Employee();

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = "SELECT * FROM employee where employee_id = ?";
      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setInt(1, id);

      ResultSet rs = pst.executeQuery();

      while (rs.next()) {

        employee.setEmployee_id(rs.getInt(1));
        employee.setEmployee_initials(rs.getString(2));
        employee.setEmployee_name(rs.getString(3));
      }

    } catch (SQLException e) {
      System.err.println("Cannot add customer");
      e.printStackTrace();
    }
    return employee;

  }

  // Tommy
  public String checkForDuplicateInitialsEmployee(Employee employee) {

    Employee newEmployee = new Employee();
    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = "SELECT * FROM employee where employee_initials = ?";
      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setString(1, employee.getEmployee_initials());

      ResultSet rs = pst.executeQuery();

      while (rs.next()) {

        newEmployee.setEmployee_id(rs.getInt(1));
        newEmployee.setEmployee_initials(rs.getString(2));
        newEmployee.setEmployee_name(rs.getString(3));
      }

    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }

    return newEmployee.getEmployee_initials();
  }

  // Marcus og Tommy
  public void createEmployee(Employee employee) {

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = "INSERT INTO employee (employee_id, employee_initials, employee_name) VALUES (?,?,?)";
      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setInt(1, employee.getEmployee_id());
      pst.setString(2, employee.getEmployee_initials());
      pst.setString(3, employee.getEmployee_name());
      pst.executeUpdate();

    } catch (SQLException e) {
      System.err.println("Cannot add employee");
      e.printStackTrace();
    }
  }

  // Tommy
  public Employee fetchEmployeeByInitials(String employee_initials) {
    Employee employee = new Employee();

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = "SELECT * FROM employee where employee_initials = ?";
      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setString(1, employee_initials);

      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        employee.setEmployee_id(rs.getInt(1));
        employee.setEmployee_initials(rs.getString(2));
        employee.setEmployee_name(rs.getString(3));
      }

    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }
    return employee;
  }

  //Sebastian
  public ArrayList<Employee> fetchAllEmplyees() {
    ArrayList<Employee> employees = new ArrayList<>();

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = "SELECT * FROM employee";
      PreparedStatement pst = conn.prepareStatement(sql);

      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        int employee_id = rs.getInt(1);
        String employee_initials = rs.getString(2);
        String employee_name = rs.getString(3);

        Employee employee = new Employee(employee_id, employee_initials, employee_name);
        employees.add(employee);
      }

    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }
    return employees;
  }
}
