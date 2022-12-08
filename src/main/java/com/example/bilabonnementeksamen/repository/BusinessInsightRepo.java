package com.example.bilabonnementeksamen.repository;

import com.example.bilabonnementeksamen.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.swing.text.DateFormatter;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@Repository
public class BusinessInsightRepo {

  @Value("${JDBCUrl}")
  private String databaseURL;
  @Value("${JDBCUsername}")
  private String user;
  @Value("${JDBCPassword}")
  private String password;

  // Sebastian og lidt Marcus
// Henter alle reservationer som strækker sig over hele indeværende måned
  public int fetchFullCurrentMonthReservations() {

    int firstDayOfCurrentMonth = 1;
    int lastDayOfCurrentMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH); //TODO check for 0
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    Calendar startDayOfMonth = Calendar.getInstance();
    startDayOfMonth.set(currentYear, currentMonth, firstDayOfCurrentMonth);
    // Konvertere fra Calendar to Date
    // SimpleDateFormat sf = startDayOfMont


    Date startDayOfMonthDate = (Date) startDayOfMonth.getTime();
    Date endDayOfMonthDate = (Date) startDayOfMonth.getTime();

   /* DateFormatter df = new DateFormat("yyyy/MM/dd");

    LocalDate date = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM dd");
    String text = date.format(formatter);
    LocalDate parsedDate = LocalDate.parse(text, formatter);
*/

    Calendar endDayOfMonth = Calendar.getInstance();
    endDayOfMonth.set(currentMonth, currentMonth, lastDayOfCurrentMonth);

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
      pst.setDate(1, startDayOfMonthDate);
      pst.setDate(2, endDayOfMonthDate);
      pst.setDate(3, startDayOfMonthDate);
      pst.setDate(4, endDayOfMonthDate);
      pst.setDate(5, startDayOfMonthDate);

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




}
