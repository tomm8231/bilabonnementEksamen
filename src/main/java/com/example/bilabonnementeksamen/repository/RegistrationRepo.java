package com.example.bilabonnementeksamen.repository;

import com.example.bilabonnementeksamen.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RegistrationRepo {


 @Value("${JDBCUrl}")
  private String databaseURL;
  @Value("${JDBCUsername}")
  private String user;
  @Value("${JDBCPassword}")
  private String password;

 /*

  private final String databaseURL = "jdbc:mysql://localhost:3306/car_leasing";
  private final String user = "car_leasing_user";
  private final String password = "1234";


  */
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
              WHERE (( ? < pickup_date)
              AND (? < pickup_date))
              OR ((? > return_date) AND (? > return_date)));
            """;


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

  public List<Reservation> fetchAllReservations() {

    ArrayList<Reservation> reservations = new ArrayList<Reservation>();

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = """
			SELECT *
             FROM car_leasing.reservation
             INNER JOIN location
             USING (location_address)
             INNER JOIN employee
             USING (employee_id)
             INNER JOIN car
             USING (car_vehicle_number)
             INNER JOIN customer
             USING (customer_id)
             INNER JOIN car_model
             USING (car_model_id)
             INNER JOIN subscription_type
             USING (subscription_type_id)
             INNER JOIN fuel
             USING (car_fuel_type);           
             """;

      PreparedStatement pst = conn.prepareStatement(sql);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {

        String car_fuel_type = rs.getString(1);
        int subscription_type_id = rs.getInt(2);
        int car_model_id = rs.getInt(3);
        int customer_id = rs.getInt(4);
        int car_vehicle_number = rs.getInt(5);
        int employee_id = rs.getInt(6);
        String location_address = rs.getString(7);
        int reservation_id = rs.getInt(8);
        Date pickup_date = rs.getDate(9);
        Date return_date = rs.getDate(10);
        Time pickup_time = rs.getTime(11);
        Time return_time = rs.getTime(12);
        int reservation_payment = rs.getInt(13);
        String reservation_comment = rs.getString(14);
        int location_phone_number = rs.getInt(15);
        String location_name = rs.getString(16);
        String employee_initials = rs.getString(17);
        String employee_name = rs.getString(18);
        int car_chassis_number = rs.getInt(19);
        double car_price_month = rs.getDouble(20);
        int car_is_reserved = rs.getInt(21);
        String customer_name = rs.getString(22);
        String customer_mail = rs.getString(23);
        String customer_address = rs.getString(24);
        String customer_phone_number = rs.getString(25);
        String car_model = rs.getString(26);
        int car_hp = rs.getInt(27);
        String car_brand = rs.getString(28);
        String car_gearbox_type = rs.getString(29);
        double car_co2_km = rs.getDouble(30);
        String car_energy_label = rs.getString(31);
        double car_distance_amount = rs.getDouble(32);
        String car_description = rs.getString(33);
        String subscription_type_name = rs.getString(34);
        String distance_unit = rs.getString(35);

        // Opretter et fuel objekt
        Fuel fuel = new Fuel(car_fuel_type, distance_unit);

        // Opretter et objekt af sub.type
        Subscription_type subscription_type = new Subscription_type(subscription_type_id, subscription_type_name);

        // Et carModel objekt skal have et fuel-objekt
        CarModel carModel = new CarModel(car_model_id, car_brand, car_model,
            car_hp, fuel, car_gearbox_type,
            car_co2_km, car_energy_label, car_distance_amount,
            car_description);

        //reservation kræver customer
        Customer customer = new Customer(customer_id,customer_name,customer_mail,customer_address,customer_phone_number);

        //reservation kræver car
        Car car = new Car(car_vehicle_number, car_chassis_number, carModel, car_price_month,
            subscription_type, car_is_reserved);

        //reservation kræver employee
        Employee employee = new Employee(employee_id,employee_initials,employee_name);

        //reservation kræver location
        Location location = new Location(location_address,location_phone_number,location_name);

        Reservation reservation = new Reservation(reservation_id, car, customer,location,pickup_date,return_date,
                                  pickup_time,return_time,reservation_payment,reservation_comment,employee );

        reservations.add(reservation);

      }
    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }

    return reservations;
  }

  public void createReservation(Reservation reservation) {

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = "INSERT INTO reservation () VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      PreparedStatement pst = conn.prepareStatement(sql);

      pst.setInt(1, reservation.getReservation_id());
      pst.setObject(2, reservation.getCar_vehicle_number());
      pst.setObject(3, reservation.getCustomer_id());
      pst.setObject(4, reservation.getLocation_address());
      pst.setObject(5, reservation.getPickup_date());
      pst.setObject(6, reservation.getReturn_date());
      pst.setObject(7, reservation.getPickup_time());
      pst.setObject(8, reservation.getReturn_time());
      pst.setDouble(9, reservation.getReservation_payment());
      pst.setString(10, reservation.getReservation_comment());
      pst.setObject(11, reservation.getEmployee_id());
      pst.executeUpdate();

    } catch (SQLException e) {
      System.err.println("Cannot add customer");
      e.printStackTrace();
    }
  }

  // marcus
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
      System.err.println("Cannot add customer");
      e.printStackTrace();
    }
  }

}