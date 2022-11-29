package com.example.bilabonnementeksamen.repository;

import com.example.bilabonnementeksamen.model.Car;
import com.example.bilabonnementeksamen.model.CarModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RegistrationRepo {

  @Value("${spring.datasource.url}")
  private String databaseURL;
  @Value("${spring.datasource.username}")
  private String user;
  @Value("${spring.datasource.password}")
  private String password;


  public List<Car> fetchCarsByDate(Date startDate, Date endDate) {

    ArrayList<Car> cars = new ArrayList<Car>();

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = """
          SELECT * FROM car c
          LEFT JOIN car_model cm
          ON c.car_model_id = cm.car_model_id
          LEFT JOIN fuel f
          ON cm.car_fuel_type = f.car_fuel_type
          """;
      PreparedStatement pst = conn.prepareStatement(sql);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        int car_vehicle_number = rs.getInt(1);
        int car_chassis_number = rs.getInt(2);
        int car_model_id = rs.getInt(3);
        int car_price_month = rs.getInt(4);
        int subscription_type_id = rs.getInt(5);
        int car_is_reserved = rs.getInt(6);

        //int car_model_id = rs.getInt(7);
        String car_model = rs.getString(8);
        int car_hp = rs.getInt(9);
        String car_brand = rs.getString(10);
        String car_fuel_type = rs.getString(11);
        String car_gearbox_type = rs.getString(12);
        double car_co2_km = rs.getDouble(13);
        String car_energy_label = rs.getString(14);
        int car_distance_amount = rs.getInt(15);
        String car_description = rs.getString(16);

        /*
        CarModel carModel = new CarModel()
         = new Car(car_vehicle_number, car_chassis_number, car_model_id, car_price_month, subscription_type_id, car_is_reserved);
        car.add(cars);
         */

      }
    } catch (SQLException e) {
      System.err.println("Cannot connect");
      e.printStackTrace();
    }

    return cars;


  }
}