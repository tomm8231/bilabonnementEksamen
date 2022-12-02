package com.example.bilabonnementeksamen.controller;

import com.example.bilabonnementeksamen.service.BusinessInsightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BusinessInsightController {

  @Autowired
  BusinessInsightService businessInsightService;

  @GetMapping("/business-home-page")
  public String showBusinessHome() {
    return "business-home-page";
  }





}
