package com.example.bilabonnementeksamen.controller;

import com.example.bilabonnementeksamen.model.*;
import com.example.bilabonnementeksamen.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
public class RegistrationController {

  @Autowired
  RegistrationService registrationService;



  @GetMapping("/registration")
  public String showRegistrationPage() {
    return "/registration/registration-home-page";
  }


  @GetMapping("/lease-contract")
  public String showNewContract(Model model) {
    model.addAttribute("today", LocalDate.now());
    return "/registration/lease-start-new-contract";
  }

  // Fælles - Find biler ud fra dato og lejetype og send videre
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

    //Der tilrettes så begge datoer kan brues fremad
    LocalDate startReservationDate = registrationService.modifyStartDate(startDate);
    LocalDate returnReservationDate = registrationService.modifyEndDate(startDate,endDate);

    //Datoerne gemmes
    session.setAttribute("start-date", startReservationDate);
    session.setAttribute("end-date", returnReservationDate);

    //alt der renderes i view
    model.addAttribute("bookingStart", startReservationDate);
    model.addAttribute("bookingRetur", returnReservationDate);
    model.addAttribute("car", registrationService.fetchCarsByDate(startDate, endDate, typeLeasing));
    return "/registration/lease-available-cars";
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
    return "/registration/lease-find-or-create-customer";
  }

  @PostMapping("/lease-create-customer")
  public String leaseAddCustomer(@ModelAttribute Customer customer, HttpSession session) {
    registrationService.createCustomer(customer);
    session.setAttribute("lease-customer", customer);
    return "redirect:/pickup-place";
  }

  @GetMapping("/lease-find-employee")
  public String showFindOrdCreateEmployee() {
    return "/registration/lease-find-employee";
  }

  @PostMapping("/lease-find-employee")
  public String leaseAddEmployee(@RequestParam ("employee-id") int id, HttpSession session) {

    Employee employee = registrationService.fetchEmployeeById(id);

    //If-statement der tjekker om medarbejderen findes i systemet
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

    //If-statement der tjekker om kunden findes i systemet
    if (customer.getCustomer_name() == null) {
      return "redirect:/lease-find-or-create-customer";
    }
    session.setAttribute("lease-customer", customer);
    return "redirect:/pickup-place";
  }

  // Sebastian og Marcus
  @GetMapping("/lease-final-form")
  public String showLeaseContract(HttpSession session, Model model) {

    //Alle objekterne fra processen hentes fra session og tilføjes til view
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
    model.addAttribute("endDate",bookingEndDate);

    //TODO: ændre til double?
    int paymentTotal = (int) registrationService.calculatePaymentTotal("startDate", "endDate", car);
    model.addAttribute("paymentTotal", paymentTotal);

    return "/registration/lease-final-form";
  }

  // Marcus, Tommy og Sebastian
  //TODO: fjernet   @RequestParam ("reservation_payment")int reservationPayment for en metode
  @PostMapping("/lease-form")
  public String makeLeaseContract(HttpSession session,
                                  @RequestParam ("pickup-time") String pickupTime,
                                  @RequestParam ("return-time") String returnTime,
                                  @RequestParam ("paymentTotal") int paymentTotal,
                                  @RequestParam ("reservation_comment")String reservationComment ){

    //gemte sessionsobjekter til oprettelse af reservation
    Customer customer = (Customer) session.getAttribute("lease-customer");
    Car car = (Car) session.getAttribute("car");
    Location location = (Location) session.getAttribute("lease-location");
    Employee employee = (Employee) session.getAttribute("lease-employee");
    LocalDate bookingStartDate = (LocalDate) session.getAttribute("start-date");
    LocalDate bookingEndDate = (LocalDate) session.getAttribute("end-date");

    //TODO: ændre til double?
    // paymentTotal = (int) registrationService.calculatePaymentTotal(pickupTime, returnTime, car);

    //reservationen oprettes
    registrationService.createReservation(car,customer,location,bookingStartDate,bookingEndDate, pickupTime, returnTime,
        paymentTotal, reservationComment,employee);

    //bil er ikke længere i process, og status ændres
    registrationService.unreserveCarById(car.getCar_vehicle_number());

    //nulstil session da oprettelsen er færdig
    session.invalidate();

    return "redirect:/show-reserved-cars";
  }

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
    return "/registration/lease-show-rented-out-cars";
  }

  @GetMapping("/pickup-place")
  public String showSelectPickupPlace(Model model) {
    model.addAttribute("locations", registrationService.fetchAllLocations());
    return "/registration/lease-select-location";
  }

  // Tommy
  @PostMapping("/pickup-place")
  public String selectPickupPlace(@RequestParam ("location_address") String locationAddress, HttpSession session) {
    // flyttes til meny: registrationService.createLocation(location);
    Location location = registrationService.fetchLocationByAddress(locationAddress);
    session.setAttribute("lease-location", location);
    return "redirect:/lease-find-employee";
  }

  @GetMapping("/lease-economy")
  public String showEconomy(Model model){
    List<Reservation> reservations = registrationService.fetchAllReservations();
    model.addAttribute(reservations);

    double totalLeaseSum = registrationService.calculateIncome(reservations);
    model.addAttribute("totalSum", totalLeaseSum);
    return "/lease-income";
  }

  //Giver det mening at mappings vedr. oprettelse af medarbejdere ligger i registrationController?
  @GetMapping("/create-employee")
  public String showCreateEmployee( @RequestParam (value = "message", required = false) String message, Model model) {
    model.addAttribute("message", message);
    return "/registration/create-employee";
  }

  @PostMapping("/create-employee")
  public String createEmployee(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {

    //TODO: tjekke at medarbejders initialer ikke allerede er oprettet


    int messageOption = registrationService.checkForDuplicateInitialsEmployee(employee);

    switch (messageOption) {

      case 1 -> {
        redirectAttributes.addAttribute("message", "Initialer findes allerede");
      }
      case 2 -> {
        registrationService.createEmployee(employee);
        Employee newEmployee = registrationService.fetchEmployeeByInitials(employee.getEmployee_initials());
        redirectAttributes.addAttribute("message", "Medarbejder " + newEmployee.getEmployee_name() + " (" + newEmployee.getEmployee_initials() + ") er oprettet med ID #" + newEmployee.getEmployee_id());
      }
    }


    return "redirect:/create-employee";
  }


  @GetMapping("/lease-new-location")
  public String showCreateNewLocation(@RequestParam (value = "message", required = false) String message, Model model){
    model.addAttribute("message", message);
    return "/registration/lease-new-location";
  }


  @PostMapping ("/lease-new-location")
  public String createNewLocation(@ModelAttribute Location location, RedirectAttributes redirectAttributes){

    if(registrationService.checkIfLocationExist(location)){
      redirectAttributes.addAttribute("message", "Lokalitet findes allerede");
    } else {
      registrationService.createLocation(location);
      redirectAttributes.addAttribute("message", "Du har oprettet en ny lokalitet: "  +
          location.getLocation_name()+ ", " + location.getLocation_address() + ". Telefonnummer: " + location.getLocation_phone());
    }
// redirect til en get eller post (ikke html!)
    return "redirect:/lease-new-location";
  }
}
