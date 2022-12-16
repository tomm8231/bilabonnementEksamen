package com.example.bilabonnementeksamen.repository;


import com.example.bilabonnementeksamen.model.Car;
import com.example.bilabonnementeksamen.model.CarModel;
import com.example.bilabonnementeksamen.model.Fuel;
import com.example.bilabonnementeksamen.model.Subscription_type;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CarRepo {

  @Value("${JDBCUrl}")
  private String databaseURL;
  @Value("${JDBCUsername}")
  private String user;
  @Value("${JDBCPassword}")
  private String password;


  public Car fetchCarById(int id) {
    Car car = new Car();

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = """
          SELECT *
            FROM car
            INNER JOIN car_model
            USING (car_model_id)
            INNER JOIN subscription_type
            USING (subscription_type_id)
            INNER JOIN fuel
            USING (car_fuel_type)
            WHERE car_vehicle_number = ?;
            """;


      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setInt(1, id);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {

        String car_fuel_type = rs.getString(1);
        int subscription_type_id = rs.getInt(2);
        int car_model_id = rs.getInt(3);
        int car_vehicle_number = rs.getInt(4);
        int car_chassis_number = rs.getInt(5);
        double car_price_month = rs.getDouble(6);
        int car_is_reserved = rs.getInt(7);
        String car_model = rs.getString(8);
        int car_hp = rs.getInt(9);
        String car_brand = rs.getString(10);
        String car_gearbox_type = rs.getString(11);
        double car_co2_km = rs.getDouble(12);
        String car_energy_label = rs.getString(13);
        double car_distance_amount = rs.getDouble(14);
        String car_description = rs.getString(15);
        String subscription_type_name = rs.getString(16);
        String distance_unit = rs.getString(17);

        // Opretter et fuel objekt
        Fuel fuel = new Fuel(car_fuel_type, distance_unit);

        // Opretter et objekt af sub.type
        Subscription_type subscription_type = new Subscription_type(subscription_type_id, subscription_type_name);

        // Et carModel objekt skal have et fuel-objekt
        CarModel carModel = new CarModel(car_model_id, car_brand, car_model,
            car_hp, fuel, car_gearbox_type,
            car_co2_km, car_energy_label, car_distance_amount,
            car_description);

        car = new Car(car_vehicle_number, car_chassis_number, carModel, car_price_month,
            subscription_type, car_is_reserved);

      }
    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }

    return car;
  }

  public List<Car> fetchCarsByDate(Date startDate, Date endDate, String typeLease) {

    ArrayList<Car> cars = new ArrayList<Car>();


    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = """
            SELECT *
             FROM car
             INNER JOIN car_model
             USING (car_model_id)
             INNER JOIN subscription_type
             USING (subscription_type_id)
             INNER JOIN fuel
             USING (car_fuel_type)
             WHERE car_is_reserved = 0
             AND subscription_type_name = ?
             AND car_vehicle_number NOT IN
            (SELECT car_vehicle_number
             FROM reservation
              WHERE (pickup_date BETWEEN ? AND ?)
              OR (return_date BETWEEN ? AND ? ));
            """;
/*
    Jeg vil se alle biler
    joined med alt indhold
    HVOR bilen ikke er i en reservationsprocess
    OG subsciption_type_name er lig typen jeg ønsker
    OG car_vehicle_number ikke matcher med nogen fra følgende liste af car_vehicle_number:
    -- Jeg vil se alle car_vehicle_number FRA reservation
    -- Hvor (min ønskede startdato og slutdato, ikke ligger i en peiode med en pick_update)
    -- ELLER
    --(Hvor min ønskede startdato og slutdato, ikke ligger i en peiode med en pick_update)
*/

      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setString(1,typeLease);
      pst.setDate(2,startDate);
      pst.setDate(3,endDate);
      pst.setDate(4,startDate);
      pst.setDate(5,endDate);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {

        String car_fuel_type = rs.getString(1);
        int subscription_type_id = rs.getInt(2);
        int car_model_id = rs.getInt(3);
        int car_vehicle_number = rs.getInt(4);
        int car_chassis_number = rs.getInt(5);
        double car_price_month = rs.getDouble(6);
        int car_is_reserved = rs.getInt(7);
        String car_model = rs.getString(8);
        int car_hp = rs.getInt(9);
        String car_brand = rs.getString(10);
        String car_gearbox_type = rs.getString(11);
        double car_co2_km = rs.getDouble(12);
        String car_energy_label = rs.getString(13);
        double car_distance_amount = rs.getDouble(14);
        String car_description = rs.getString(15);
        String subscription_type_name = rs.getString(16);
        String distance_unit = rs.getString(17);

        // Opretter et fuel objekt
        Fuel fuel = new Fuel(car_fuel_type, distance_unit);

        // Opretter et objekt af sub.type
        Subscription_type subscription_type = new Subscription_type(subscription_type_id, subscription_type_name);

        // Et carModel objekt skal have et fuel-objekt
        CarModel carModel = new CarModel(car_model_id, car_brand, car_model,
            car_hp, fuel, car_gearbox_type,
            car_co2_km, car_energy_label, car_distance_amount,
            car_description);

        Car car = new Car(car_vehicle_number, car_chassis_number, carModel, car_price_month,
            subscription_type, car_is_reserved);

        cars.add(car);

      }
    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }

    return cars;


  }

  public int fetchCarReservedStatus(int id) {
    int car_is_reserved = 0;

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = """
          SELECT car_is_reserved FROM car
          WHERE car_vehicle_number = ?;
                              """;


      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setInt(1, id);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {

        car_is_reserved = rs.getInt(1);

      }
    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }
    return car_is_reserved;
  }

  public void reserveCarById(int id) {

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = """
          UPDATE car
          SET car_is_reserved = 1
          WHERE car_vehicle_number = ?;
          """;


      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setInt(1, id);
      pst.executeUpdate();

    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }
  }

  public void unreserveCarById(int car_vehicle_number) {
    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = """
          UPDATE car
          SET car_is_reserved = 0
          WHERE car_vehicle_number = ?;
          """;


      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setInt(1, car_vehicle_number);
      pst.executeUpdate();

    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }
  }

  // Tommy
  public void unreserveAllCarsFromSession() {
    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = """
          UPDATE car
          SET car_is_reserved = 0
          WHERE car_is_reserved = 1;
          """;

      PreparedStatement pst = conn.prepareStatement(sql);
      pst.executeUpdate();

    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }
  }

}
