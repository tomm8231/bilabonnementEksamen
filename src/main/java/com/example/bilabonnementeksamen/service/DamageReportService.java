package com.example.bilabonnementeksamen.service;

import com.example.bilabonnementeksamen.model.Reservation;
import com.example.bilabonnementeksamen.repository.DamageReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DamageReportService {

  @Autowired
  DamageReportRepo damageReportRepo;

//TODO: Hvordan skal dette skrives? Reservation
  public Reservation fetchReservationInfo(int id) {
    return damageReportRepo.fetchReservationInfoById(id);
  }

  public int checkIdInUse(int id) {
    String check = damageReportRepo.checkIdInUse(id);

    if (check != null) {
      System.out.println("1");
      return 1;
    } else {
      System.out.println("2");
      return 2;
    }
  }
}
