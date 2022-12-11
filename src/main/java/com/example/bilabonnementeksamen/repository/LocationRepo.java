package com.example.bilabonnementeksamen.repository;

import com.example.bilabonnementeksamen.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LocationRepo {

 @Value("${JDBCUrl}")
  private String databaseURL;
  @Value("${JDBCUsername}")
  private String user;
  @Value("${JDBCPassword}")
  private String password;

  public void createLocation(Location location) {
    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = "INSERT INTO location (location_address, location_phone_number, location_name) VALUES (?,?,?)";
      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setString(1, location.getLocation_address());
      pst.setInt(2, location.getLocation_phone());
      pst.setString(3, location.getLocation_name());
      pst.executeUpdate();



    } catch (SQLException e) {
      System.err.println("Cannot add location");
      e.printStackTrace();
    }
  }

  public List<Location> fetchAllLocations() {
    ArrayList<Location> locations = new ArrayList<>();

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = "SELECT * FROM location";
      PreparedStatement pst = conn.prepareStatement(sql);

      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        Location location = new Location();

        location.setLocation_address(rs.getString(1));
        location.setLocation_phone(rs.getInt(2));
        location.setLocation_name(rs.getString(3));

        locations.add(location);

      }
    } catch (SQLException e) {
      System.err.println("Cannot fetch locations");
      e.printStackTrace();
    }
    return locations;
  }

  public Location fetchLocationByAddress(String locationAddress) {

    Location location = new Location();

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = "SELECT * FROM location where location_address = ?";
      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setString(1, locationAddress);

      ResultSet rs = pst.executeQuery();

      while (rs.next()) {

        location.setLocation_address(rs.getString(1));
        location.setLocation_phone(rs.getInt(2));
        location.setLocation_name(rs.getString(3));
      }
    } catch (SQLException e) {
      System.err.println("Cannot add customer");
      e.printStackTrace();
    }
    return location;
    }

}