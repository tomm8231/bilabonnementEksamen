package com.example.bilabonnementeksamen.controller;

import com.example.bilabonnementeksamen.model.*;
import com.example.bilabonnementeksamen.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

// Før man har en database
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
  public String showNewContract(Model model) {
    model.addAttribute("today", LocalDate.now());
    return "lease-start-new-contract";
  }

  // Fælles  - Find biler ud fra dato og lejetype og send videre
  @PostMapping("/lease-limited-contract")
  public String findLimitedCarsByDate(@RequestParam("limited-start-date") String startDate,
                                      @RequestParam ("type-leasing") String typeLeasing,
                                      RedirectAttributes redirectAttributes) {
    redirectAttributes.addAttribute("rd-start-date", startDate);
    redirectAttributes.addAttribute("type-leasing", typeLeasing);

    return "redirect:/lease-available-cars";
  }

  // Fælles
  @PostMapping("/lease-unlimited-contract")
  public String findUnlimitedCarsByDate(@RequestParam("unlimited-start-date") String startDate, @RequestParam("unlimited-end-date") String endDate,
                                        @RequestParam ("type-leasing") String typeLeasing, RedirectAttributes redirectAttributes) {

    redirectAttributes.addAttribute("rd-start-date", startDate);
    redirectAttributes.addAttribute("rd-end-date", endDate);
    redirectAttributes.addAttribute("type-leasing", typeLeasing);

    return "redirect:/lease-available-cars";
  }

// Fælles
    @GetMapping("/lease-available-cars")
    public String showAvailableCars(Model model, @RequestParam ("rd-start-date") String startDate,
                                    @RequestParam(value = "rd-end-date", required = false) String endDate,
                                    @RequestParam ("type-leasing") String typeLeasing, HttpSession session) {

    session.setAttribute("start-date", registrationService.modifyStartDate(startDate));
    session.setAttribute("end-date", registrationService.modifyEndDate(startDate, endDate));
    model.addAttribute("car", registrationService.fetchCarsByDate(startDate, endDate, typeLeasing));
      return "lease-available-cars";
    }


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
    return "redirect:/pickup-place";
  }

  @GetMapping("/lease-find-employee")
  public String showFindOrdCreateEmployee() {
    return "lease-find-employee";
  }

  @PostMapping("/lease-find-employee")
  public String leaseAddEmployee(@RequestParam ("employee-id") int id, HttpSession session) {
   // registrationService.createEmployee(employee);
    Employee employee = registrationService.fetchEmployeeById(id);

    if (employee.getEmployee_name() == null) {
      return "redirect:/lease-find-employee";
    }

    session.setAttribute("lease-employee", employee);

    return "redirect:/lease-final-form/";
  }

  // Sebastian
  @PostMapping("/lease-find-returning-customer")
  public String leaseFindCustomer(@RequestParam("returning-costumer-mail") String mail, HttpSession session) {

    Customer customer = registrationService.fetchCustomerByMail(mail);

    if (customer.getCustomer_name() == null) {
      return "redirect:/lease-find-or-create-customer";
    }
    session.setAttribute("lease-customer", customer);
    return "redirect:/pickup-place";
  }

// Sebastian og Marcus
  @GetMapping("/lease-final-form")
  // Skal man bruge @RequestParam for at
  public String showLeaseContract(HttpSession session, Model model) {

    Customer customer = (Customer) session.getAttribute("lease-customer");
    model.addAttribute("customer", customer);

    Car car = (Car) session.getAttribute("car");
    model.addAttribute("car", car);

    Location location = (Location) session.getAttribute("lease-location");
    model.addAttribute("location", location);

    Employee employee = (Employee) session.getAttribute("lease-employee");
    model.addAttribute("employee", employee);

    LocalDate bookingStartDate = (LocalDate) session.getAttribute("start-date");
    model.addAttribute("startDate",bookingStartDate);

    LocalDate bookingEndDate = (LocalDate) session.getAttribute("end-date");
    model.addAttribute("endDate",bookingStartDate);

    return "lease-final-form";
  }

  // Marcus og Tommy
  @PostMapping("/lease-form")
  public String makeLeaseContract(@ModelAttribute Reservation reservation,
                                  HttpSession session, @RequestParam("reservation-comment") String reservationComment){
    registrationService.createReservation(reservation);
    session.setAttribute("reservation", reservation);
    session.setAttribute("reservation-comment", reservationComment);
        return "redirect:/lease-form-finished";
  }

  /*    Reservation reservation = (Reservation) session.getAttribute("id");
    model.addAttribute("reservation", reservation);*/

  // Sebastian
  @PostMapping("/cancel-lease-contract")
  public String cancelLeaseContract(HttpSession session) {
    Car car = (Car) session.getAttribute("car");
    registrationService.unreserveCarById(car.getCar_vehicle_number());
    session.invalidate();
    return "redirect:/lease-contract";
  }

 // Tommy
  @PostMapping("/unreserve-all-cars-session")
  public String unreserveAllCarsFromSession(HttpSession session) {
    registrationService.unreserveAllCarsFromSession();
    session.invalidate();
    return "redirect:/registration";
  }

  // Sebastian
  @GetMapping("/show-reserved-cars")
  public String showReservedCars(Model model){
    model.addAttribute("reservations",registrationService.fetchAllReservations());
    return "lease-show-rented-out-cars";
  }

  @GetMapping("/pickup-place")
  public String showSelectPickupPlace(Model model) {
    model.addAttribute("locations", registrationService.fetchAllLocations());
    return "lease-select-location";
  }

// Tommy
  @PostMapping("/pickup-place")
  public String selectPickupPlace(@RequestParam ("location_address") String locationAddress, HttpSession session) {
    // flyttes til meny: registrationService.createLocation(location);
    Location location = registrationService.fetchLocationByAddress(locationAddress);
    session.setAttribute("lease-location", location);
    return "redirect:/lease-find-employee";
  }

}
