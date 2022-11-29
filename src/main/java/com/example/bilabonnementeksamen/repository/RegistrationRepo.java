package com.example.bilabonnementeksamen.repository;

import com.example.bilabonnementeksamen.model.Car;
import com.example.bilabonnementeksamen.model.CarModel;
import com.example.bilabonnementeksamen.model.Fuel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RegistrationRepo {

/*
 @Value("${JDBCUrl}")
  private String databaseURL;
  @Value("${JDBCUsername}")
  private String user;
  @Value("${JDBCPassword}")
  private String password;

 */

  private final String databaseURL = "jdbc:mysql://localhost:3306/car_leasing";
private final String user = "car_leasing_user";
  private final String password =  "1234";



  public List<CarModel> fetchCarsByDate() { // Date startDate, Date endDate

    ArrayList<Car> cars = new ArrayList<Car>();

    ArrayList<CarModel> carModels = new ArrayList<CarModel>(); // TODO: TEST-liste


    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      /*String sql = """
          SELECT * FROM car c
          LEFT JOIN car_model cm
          ON c.car_model_id = cm.car_model_id
          LEFT JOIN fuel f
          ON cm.car_fuel_type = f.car_fuel_type
          """;
*/

      PreparedStatement pst = conn.prepareStatement("SELECT * FROM car_model");
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {

        int car_model_id = rs.getInt(1);
        String car_brand = rs.getString(2);
        String car_model = rs.getString(3);
        int car_hp = rs.getInt(4);
        String car_fuel_type =  rs.getString(5);
        String car_gearbox_type = rs.getString(6);
        double car_co2_km = rs.getDouble(7);
        String car_energy_label = rs.getString(8);
        int car_distance_amount = rs.getInt(9);
        String car_description = rs.getString(10);


        carModels.add(new CarModel(car_model_id, car_brand, car_model,
                car_hp, car_fuel_type, car_gearbox_type,
                car_co2_km, car_energy_label, car_distance_amount,
                car_description));

       /* int car_vehicle_number = rs.getInt(1);
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


        CarModel carModel = new CarModel()
         = new Car(car_vehicle_number, car_chassis_number, car_model_id, car_price_month, subscription_type_id, car_is_reserved);
        car.add(cars);
         */

      }
    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      System.out.println(databaseURL);
      e.printStackTrace();
    }

    return carModels;


  }

}