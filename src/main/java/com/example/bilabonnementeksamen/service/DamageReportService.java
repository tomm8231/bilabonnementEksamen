package com.example.bilabonnementeksamen.service;

import com.example.bilabonnementeksamen.repository.DamageReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DamageReportService {

  @Autowired
  DamageReportRepo damageReportRepo;


}
