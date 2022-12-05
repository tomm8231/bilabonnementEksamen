package com.example.bilabonnementeksamen.service;

import com.example.bilabonnementeksamen.repository.DamageReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DamageReportService {

  @Autowired
  DamageReportRepo damageReportRepo;

//TODO: Hvordan skal dette skrives? Reservation
  public Object fetchReservationInfo(int id) {
    return damageReportRepo.fetchReservationInfoById(id);
  }
}
