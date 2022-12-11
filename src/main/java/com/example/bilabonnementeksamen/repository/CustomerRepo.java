package com.example.bilabonnementeksamen.repository;

import com.example.bilabonnementeksamen.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class CustomerRepo {

  @Value("${JDBCUrl}")
  private String databaseURL;
  @Value("${JDBCUsername}")
  private String user;
  @Value("${JDBCPassword}")
  private String password;


  public Customer fetchCustomerByMail(String mail) {
    Customer customer = new Customer();
    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = "SELECT * FROM customer where customer_mail = ?";
      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setString(1, mail);

      ResultSet rs = pst.executeQuery();

      while (rs.next()) {

        customer.setCustomer_id(rs.getInt(1));
        customer.setCustomer_name(rs.getString(2));
        customer.setCustomer_mail(rs.getString(3));
        customer.setCustomer_address(rs.getString(4));
        customer.setCustomer_phone_number(rs.getString(5));


      }
    } catch (SQLException e) {
      System.err.println("Cannot add customer");
      e.printStackTrace();
    }
    return customer;
  }

  public void createCustomer(Customer customer) {
    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = "INSERT INTO customer (customer_name, customer_mail, customer_address, customer_phone_number) VALUES (?,?,?,?)";
      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setString(1, customer.getCustomer_name());
      pst.setString(2, customer.getCustomer_mail());
      pst.setString(3, customer.getCustomer_address());
      pst.setString(4, customer.getCustomer_phone_number());
      pst.executeUpdate();

    } catch (SQLException e) {
      System.err.println("Cannot add customer");
      e.printStackTrace();
    }
  }
}
