package com.example.bilabonnementeksamen.repository;

import com.example.bilabonnementeksamen.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepo {

  @Value("${JDBCUrl}")
  private String databaseURL;
  @Value("${JDBCUsername}")
  private String user;
  @Value("${JDBCPassword}")
  private String password;

  // Sebastian og lidt Marcus og Daniel
// Henter alle reservationer som strækker sig over hele indeværende måned
  public int fetchFullCurrentMonthReservationsIncome(LocalDate startDayOfMonthDate, LocalDate endDayOfMonthDate) {

    int totalIncome = 0;

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);

      String sql = """
		SELECT SUM(car_price_month) AS totalPrice
               FROM car_leasing.reservation
               INNER JOIN car
               USING (car_vehicle_number)
  	           WHERE (pickup_date not between ? and ?) AND
               (return_date not between ? and ?) AND
               (? between pickup_date and return_date);          
             """;
      // Henter summen af prisen for reservationerne som går over hele indeværende måned

      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setDate(1, Date.valueOf(startDayOfMonthDate));
      pst.setDate(2, Date.valueOf(endDayOfMonthDate));
      pst.setDate(3, Date.valueOf(startDayOfMonthDate));
      pst.setDate(4, Date.valueOf(endDayOfMonthDate));
      pst.setDate(5, Date.valueOf(startDayOfMonthDate));

      ResultSet rs = pst.executeQuery();

      while (rs.next()) {

        totalIncome = rs.getInt(1);

      }
    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }
    return totalIncome;
  }

  public ArrayList<Reservation> fetchStartCurrentMonthReservations(LocalDate startDayOfMonthDate, LocalDate endDayOfMonthDate) {

    ArrayList<Reservation> startCurrentMonthreservations = new ArrayList<Reservation>();

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
             USING (car_fuel_type)
             WHERE (pickup_date between ? and ?) AND
             (return_date not between ? and ?);
             ;           
             """;

      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setDate(1, Date.valueOf(startDayOfMonthDate));
      pst.setDate(2, Date.valueOf(endDayOfMonthDate));
      pst.setDate(3, Date.valueOf(startDayOfMonthDate));
      pst.setDate(4, Date.valueOf(endDayOfMonthDate));
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

        startCurrentMonthreservations.add(reservation);

      }
    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }

    return startCurrentMonthreservations;

  }

  public ArrayList<Reservation> fetchEndCurrentMonthReservations(LocalDate startDayOfMonthDate, LocalDate endDayOfMonthDate) {
    ArrayList<Reservation> endCurrentMonthreservations = new ArrayList<Reservation>();

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
             USING (car_fuel_type)
             WHERE (return_date between ? and ?) AND
             (pickup_date not between ? and ?);
             ;           
             """;

      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setDate(1, Date.valueOf(startDayOfMonthDate));
      pst.setDate(2, Date.valueOf(endDayOfMonthDate));
      pst.setDate(3, Date.valueOf(startDayOfMonthDate));
      pst.setDate(4, Date.valueOf(endDayOfMonthDate));
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

        endCurrentMonthreservations.add(reservation);

      }
    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }

    return endCurrentMonthreservations;

  }


  public ArrayList<Reservation> fetchAllOngingContracts(LocalDate startDayOfMonthDate, LocalDate endDayOfMonthDate) {
    ArrayList<Reservation> ongoingCurrentMonthreservations = new ArrayList<Reservation>();

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
             USING (car_fuel_type)
             WHERE (pickup_date not between ? and ?) AND
             (return_date not between ? and ?) AND
              (? between pickup_date and return_date);
             ;           
             """;

      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setDate(1, Date.valueOf(startDayOfMonthDate));
      pst.setDate(2, Date.valueOf(endDayOfMonthDate));
      pst.setDate(3, Date.valueOf(startDayOfMonthDate));
      pst.setDate(4, Date.valueOf(endDayOfMonthDate));
      pst.setDate(5, Date.valueOf(startDayOfMonthDate));
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

        ongoingCurrentMonthreservations.add(reservation);

      }
    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }

    return ongoingCurrentMonthreservations;
  }

  // Tommy
  public String checkIfReservationExist(int reservationId) {

    String check = null;
    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);
      String sql = "SELECT * FROM reservation where reservation_id = ?";
      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setInt(1, reservationId);

      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        check = rs.getString(4);
      }

    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }
    return check;
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
             USING (car_fuel_type)
             ORDER BY reservation_id;           
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

  public void createReservation(Car car_vehicle_number, Customer customer_id, Location location_address, LocalDate pickup_date,
                                LocalDate return_date, String pickup_time, String return_time, double reservation_payment,
                                String reservation_comment, Employee employee_id) {

    try {
      Connection conn = DriverManager.getConnection(databaseURL, user, password);

      String sql = "INSERT INTO reservation(car_vehicle_number, customer_id, location_address, pickup_date, " +
          "return_date, pickup_time, return_time, reservation_payment, reservation_comment, employee_id)\n" +
          "VALUES(?,?,?,?,?,?,?,?,?,?)";
      PreparedStatement pst = conn.prepareStatement(sql);

      pst.setInt(1, car_vehicle_number.getCar_vehicle_number());
      pst.setInt(2, customer_id.getCustomer_id());
      pst.setString(3, location_address.getLocation_address());
      pst.setDate(4, Date.valueOf(pickup_date));
      pst.setDate(5, Date.valueOf(return_date));
      pst.setTime(6, Time.valueOf(pickup_time));
      pst.setTime(7, Time.valueOf(return_time));
      pst.setDouble(8, reservation_payment);
      pst.setString(9, reservation_comment);
      pst.setInt(10, employee_id.getEmployee_id());
      pst.executeUpdate();

    } catch (SQLException e) {
      System.err.println("Cannot add customer");
      e.printStackTrace();
    }
  }

  public Reservation fetchReservationById(int reservationId) {
    Reservation reservation = new Reservation();

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
             USING (car_fuel_type)
             WHERE reservation_id = ?        
             """;

      PreparedStatement pst = conn.prepareStatement(sql);
      pst.setInt(1,reservationId);
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

        reservation = new Reservation(reservation_id, car, customer,location,pickup_date,return_date,
            pickup_time,return_time,reservation_payment,reservation_comment,employee );


      }
    } catch (SQLException e) {
      System.err.println("Cannot connect to database");
      e.printStackTrace();
    }

    return reservation;
  }

}
