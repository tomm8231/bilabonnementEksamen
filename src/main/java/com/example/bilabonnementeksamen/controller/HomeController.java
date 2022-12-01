package com.example.bilabonnementeksamen.controller;

import com.example.bilabonnementeksamen.model.*;
import com.example.bilabonnementeksamen.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

// @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

@Controller
public class HomeController {

  @Autowired
  RegistrationService registrationService;


  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/registration")
  public String showRegistrationPage() {
    return "registration-home-page";
  }


  @GetMapping("/lease-contract")
  public String showNewContract() {
    return "lease-start-new-contract";
  }

  //Find biler ud fra dato og lejetype og send videre
  @PostMapping("/lease-limited-contract")
  public String findLimitedCarsByDate(@RequestParam("limited-start-date") String startDate,
                               RedirectAttributes redirectAttributes) {
    redirectAttributes.addAttribute("rd-start-date", startDate);

    return "redirect:/lease-available-cars";
  }

  @PostMapping("/lease-unlimited-contract")
  public String findUnlimitedCarsByDate(@RequestParam("unlimited-start-date") String startDate, @RequestParam("unlimited-end-date") String endDate,
                               RedirectAttributes redirectAttributes) {
    redirectAttributes.addAttribute("rd-start-date", startDate);
    redirectAttributes.addAttribute("rd-end-date", endDate);

    return "redirect:/lease-available-cars";
  }


    @GetMapping("/lease-available-cars")
    public String showAvailableCars(Model model, @RequestParam ("rd-start-date") String startDate,
                                    @RequestParam(value = "rd-end-date", required = false) String endDate) {
      model.addAttribute("car", registrationService.fetchCarsByDate());
      return "lease-available-cars";
    }
  /*
  @GetMapping("/lease-available-cars")
  public String showAvailableCars(Model model, @RequestParam ) {
    model.addAttribute("car", registrationService.fetchCarsByDate());
    return "lease-available-cars";
  }

   */

  //sebastian
  @PostMapping("/lease-available-cars")
  public String chooseCar(@RequestParam("id") int id, HttpSession session) {

    //If-statement der tjekker om bilen er reserveret eller ej
    boolean carReservedStatus = registrationService.fetchCarReservedStatus(id);
    if (carReservedStatus) {
      return "redirect:/lease-available-cars";
    }
    registrationService.reserveCarById(id);
    session.setAttribute("car", registrationService.fetchCarById(id));

    session.setAttribute("subscription-type", registrationService.fetchSubscriptionById(id)); // fetchSubscriptionTypeById

    session.setAttribute("fuel-type", registrationService.fetchFuelTypeById(id));

    session.setAttribute("car-model", registrationService.fetchCarModelById(id));


    return "redirect:/lease-find-or-create-customer";
  }


  @GetMapping("/lease-find-or-create-customer")
  public String showFindOrdCreateCustomer() {
    return "lease-find-or-create-customer";
  }

  @PostMapping("/lease-create-customer")
  public String leaseAddCustomer(@ModelAttribute Customer customer, HttpSession session) {
    registrationService.createCustomer(customer);
    session.setAttribute("lease-customer", customer);
    return "redirect:/lease-form/";
  }

  @PostMapping("/lease-find-returning-customer")
  public String leaseFindCustomer(@RequestParam("returning-costumer-mail") String mail, HttpSession session) {

    Customer customer = registrationService.fetchCustomerByMail(mail);

    if (customer.getCustomer_name() == null) {
      return "redirect:/lease-find-or-create-customer";
    }
    session.setAttribute("lease-customer", customer);
    return "redirect:/lease-form";
  }

  @GetMapping("/lease-form")
  public String showLeaseContract(HttpSession session, Model model) {
    Customer customer = (Customer) session.getAttribute("lease-customer");
    model.addAttribute("customer", customer);


    Car car = (Car) session.getAttribute("car");
    model.addAttribute("car", car);
    model.addAttribute("subscription-type", car.getSubscription_type_id());
    model.addAttribute("fuel-type", car.getCar_model_id().getCar_fuel_type());
    model.addAttribute("car-model", car.getCar_model_id());

    return "lease-final-form";
  }

  @PostMapping("/cancel-lease-contract")
  public String cancelLeaseContract(HttpSession session) {
    Car car = (Car) session.getAttribute("car");
    registrationService.unreserveCarById(car.getCar_vehicle_number());
    session.invalidate();
    return "redirect:/lease-contract";
  }

  @PostMapping("/unreserve-all-cars-session")
  public String unreserveAllCarsFromSession(HttpSession session) {
    registrationService.unreserveAllCarsFromSession();
    session.invalidate();
    return "redirect:/registration";
  }
}
