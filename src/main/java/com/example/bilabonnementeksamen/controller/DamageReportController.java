package com.example.bilabonnementeksamen.controller;

import com.example.bilabonnementeksamen.service.BusinessInsightService;
import com.example.bilabonnementeksamen.service.DamageReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DamageReportController {

  @Autowired
  DamageReportService damageReportService;


  @GetMapping("/damage-home-page")
  public String showDamageReportHome() {
    return "damage-home-page";
  }

}
