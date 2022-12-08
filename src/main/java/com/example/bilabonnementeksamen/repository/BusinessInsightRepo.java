package com.example.bilabonnementeksamen.repository;

import com.example.bilabonnementeksamen.model.*;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.swing.text.DateFormatter;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
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

  // Sebastian og lidt Marcus og Daniel
// Henter alle reservationer som strækker sig over hele indeværende måned
  public int fetchFullCurrentMonthReservations() {
/*
    int firstDayOfCurrentMonth = 1;
    int lastDayOfCurrentMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);

    int currentMonth = Calendar.getInstance().get(Calendar.MONTH); //TODO check for 0
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    Calendar startDayOfMonth = Calendar.getInstance();
    startDayOfMonth.set(currentYear, currentMonth, firstDayOfCurrentMonth);

    Calendar endDayOfMonth = Calendar.getInstance();
    endDayOfMonth.set(currentMonth, currentMonth, lastDayOfCurrentMonth);


 */

    // Bruge LocalDate
    LocalDate today = LocalDate.now();
    LocalDate startDayOfMonthDate = today.with(TemporalAdjusters.firstDayOfMonth());
    LocalDate endDayOfMonthDate = today.with(TemporalAdjusters.lastDayOfMonth());

   /* DateFormatter df = new DateFormat("yyyy/MM/dd");

    LocalDate date = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM dd");
    String text = date.format(formatter);
    LocalDate parsedDate = LocalDate.parse(text, formatter);
*/
//  Problem: caste vores calender om til dato.
    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM dd");

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




}
