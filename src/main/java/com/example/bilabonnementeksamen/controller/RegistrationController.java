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
import java.util.ArrayList;

@Controller
public class RegistrationController {

  @Autowired
  private RegistrationService registrationService;



  @GetMapping("/registration")
  public String showRegistrationPage() {
    return "/registration/registration-home-page";
  }


  @GetMapping("/lease-contract")
  public String showNewContract(Model model) {
    model.addAttribute("today", LocalDate.now());
    return "/registration/lease-start-new-contract";
  }

  // Limited biler ud fra dato og lejetype og send videre
  @PostMapping("/lease-limited-contract")
  public String findLimitedCarsByDate(@RequestParam("limited-start-date") String startDate,
                                      @RequestParam ("type-leasing") String typeLeasing,
                                      RedirectAttributes redirectAttributes) {
    redirectAttributes.addAttribute("rd-start-date", startDate);
    redirectAttributes.addAttribute("type-leasing", typeLeasing);

    return "redirect:/lease-available-cars";
  }

  // Unlimited biler ud fra dato og lejetype og send videre
  @PostMapping("/lease-unlimited-contract")
  public String findUnlimitedCarsByDate(@RequestParam("unlimited-start-date") String startDate, @RequestParam("months") String months,
                                        @RequestParam ("type-leasing") String typeLeasing, RedirectAttributes redirectAttributes, HttpSession session) {

    String endDate = registrationService.addMonthsToStartReservationDate(startDate, months);
    session.setAttribute("months", months);

    redirectAttributes.addAttribute("rd-start-date", startDate);
    redirectAttributes.addAttribute("rd-end-date", endDate);
    redirectAttributes.addAttribute("type-leasing", typeLeasing);

    return "redirect:/lease-available-cars";
  }


  @GetMapping("/lease-available-cars")
  public String showAvailableCars(Model model, @RequestParam ("rd-start-date") String startDate,
                                  @RequestParam(value = "rd-end-date", required = false) String endDate,
                                  @RequestParam ("type-leasing") String typeLeasing, HttpSession session) {

    //Der tilrettes s?? begge datoer kan bruges fremad
    LocalDate startReservationDate = registrationService.modifyStartDate(startDate);
    LocalDate returnReservationDate = registrationService.modifyEndDateLimited(startDate,endDate);

    //Datoerne gemmes
    session.setAttribute("start-date", startReservationDate);
    session.setAttribute("end-date", returnReservationDate);

    //Alt der renderes i view
    model.addAttribute("bookingStart", startReservationDate);
    model.addAttribute("bookingRetur", returnReservationDate);
    model.addAttribute("car", registrationService.fetchCarsByDate(startReservationDate, returnReservationDate, typeLeasing));
    return "/registration/lease-available-cars";
  }


  // Sebastian
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
    Customer newCustomer = registrationService.fetchCustomerByMail(customer.getCustomer_mail());
    session.setAttribute("lease-customer", newCustomer);

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

    //Alle objekterne fra processen hentes fra session og tilf??jes til view
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

    String months = (String) session.getAttribute("months");

    double paymentTotal = registrationService.calculatePaymentTotal(months, car);
    model.addAttribute("paymentTotal", paymentTotal);

    return "/registration/lease-final-form";
  }

  // Marcus, Tommy og Sebastian
  @PostMapping("/lease-form")
  public String makeLeaseContract(HttpSession session,
                                  @RequestParam ("pickup-time") String pickupTime,
                                  @RequestParam ("return-time") String returnTime,
                                  @RequestParam ("paymentTotal") double paymentTotal,
                                  @RequestParam ("reservation_comment")String reservationComment ){

    //Gemte sessionsobjekter til oprettelse af reservation
    Customer customer = (Customer) session.getAttribute("lease-customer");
    Car car = (Car) session.getAttribute("car");
    Location location = (Location) session.getAttribute("lease-location");
    Employee employee = (Employee) session.getAttribute("lease-employee");
    LocalDate bookingStartDate = (LocalDate) session.getAttribute("start-date");
    LocalDate bookingEndDate = (LocalDate) session.getAttribute("end-date");
    bookingEndDate = bookingEndDate.plusDays(3); // Check p?? v??rkstedet etc. Klarg??res

    //reservationen oprettes
    registrationService.createReservation(car,customer,location,bookingStartDate,bookingEndDate, pickupTime, returnTime,
        paymentTotal, reservationComment,employee);

    //bil er ikke l??ngere i process, og status ??ndres
    registrationService.unreserveCarById(car.getCar_vehicle_number());

    //nulstil session da oprettelsen er f??rdig
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

  @GetMapping("/pickup-place")
  public String showSelectPickupPlace(Model model) {
    model.addAttribute("locations", registrationService.fetchAllLocations());
    return "/registration/lease-select-location";
  }

  // Tommy
  @PostMapping("/pickup-place")
  public String selectPickupPlace(@RequestParam ("location_address") String locationAddress, HttpSession session) {
    Location location = registrationService.fetchLocationByAddress(locationAddress);
    session.setAttribute("lease-location", location);
    return "redirect:/lease-find-employee";
  }

  @GetMapping("/create-employee")
  public String showCreateEmployee(@RequestParam (value = "message", required = false) String message, Model model) {
    model.addAttribute("message", message);
    return "/registration/create-employee";
  }

  @PostMapping("/cancel-problem-contract")
  public String cancelProblemContract() {
    return "redirect:/problem/problem-home-page";
  }


  @PostMapping("/create-employee")
  public String createEmployee(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes ) {

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
    return "redirect:/lease-new-location";
  }

  //Sebastian
  @GetMapping("/show-all-locations")
  public String showAllLocations(Model model){
    ArrayList<Location> locations = (ArrayList<Location>) registrationService.fetchAllLocations();
    model.addAttribute("locations", locations);
    return "/registration/lease-show-locations";
  }

  //Sebastian
  @GetMapping("/show-all-employees")
  public String showAllEmployees(Model model){
    ArrayList<Employee> employees = registrationService.fetchAllEployees();
    model.addAttribute("employees", employees);
    return "/registration/lease-show-employees";
  }

  //Sebastian
  @GetMapping("/show-specifik-reservation/{reservationId}")
  public String showSpecifikReservation(@PathVariable ("reservationId") int reservationId, Model model){
    Reservation reservation = registrationService.fetchReservationById(reservationId);
    model.addAttribute("reservation", reservation);
    return "/registration/lease-show-specific-reservation";
  }

  // Marcus
  @GetMapping ("/search-reservation-by-id")
  public String searchLeaseContract(@RequestParam (value = "message", required = false) String message, Model model){
    model.addAttribute("message", message);

    return "/registration/search-reservation";
  }

  // Marcus
  @PostMapping("/search-reservation-by-id")
  public String fetchReservationInfo(Model model, @RequestParam("reservation-id") int id, RedirectAttributes redirectAttributes) {

    int checkId = registrationService.checkReservationIdInUse(id);

    switch (checkId) {

      case 1 -> {
        Reservation reservation = registrationService.fetchReservationById(id);
        model.addAttribute("reservation", reservation);
        return "registration/lease-show-specific-reservation";
      }

      case 2 -> {
        redirectAttributes.addAttribute("message", "Reservationen findes ikke");
        return "redirect:/search-reservation-by-id";
      }

      default -> {
        redirectAttributes.addAttribute("message", "Tast 1 eller h??jere");
        return "redirect:/search-reservation-by-id";
      }
    }
  }
}
