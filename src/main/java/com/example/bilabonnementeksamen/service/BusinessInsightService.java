package com.example.bilabonnementeksamen.service;

import com.example.bilabonnementeksamen.repository.BusinessInsightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessInsightService {

  @Autowired
  BusinessInsightRepo businessInsightRepo;


}
