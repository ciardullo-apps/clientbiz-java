package org.ciardullo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ciardullo.model.Appointment;
import org.ciardullo.model.Clientele;
import org.ciardullo.model.Topic;
import org.ciardullo.model.View;
import org.ciardullo.model.reports.MonthlyActivity;
import org.ciardullo.service.ClientBizService;
import org.ciardullo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class ClientBizController {

    @Autowired
    ClientBizService clientService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ReportService reportService;

    @GetMapping(value = "/client", produces = "application/json")
    @ResponseBody
    public String client() {
        List<Clientele> clients = clientService.getClients();

        String s = "";
        try {
            s = objectMapper.writeValueAsString(clients);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return s;
    }

    @GetMapping(value = "/client.html")
    public String client(@RequestParam(value="sortColumn", required = false, defaultValue = "lastapptdate") String sortColumn,
                         @RequestParam(value="sortOrder", required = false, defaultValue = "desc") String sortOrder,
                         @RequestParam(value="target", required = false, defaultValue = "index") String target,
                         Model model) {
        List<Clientele> clients = clientService.getClients(sortColumn, sortOrder);

        model.addAttribute("clients", clients);
        model.addAttribute("viewName", "client-list");
        return target;
    }

    @GetMapping(value = "/client/{id}", produces = "application/json")
    @ResponseBody
    public String client(@PathVariable("id") int id) {
        Clientele client = clientService.getClient(id);

        String s = "";
        try {
            s = objectMapper.writeValueAsString(client);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return s;
    }

    @GetMapping(value = "/client/{id}.html")
    public String client(Model model, @PathVariable("id") int id) {
        Clientele client = clientService.getClient(id);
        Map<Integer, Topic> topics = clientService.getTopics();

        // Get topic for client's last appointment
        List<Appointment> appointmentsByClient = clientService.getAppointmentsByClient(id);
        if(appointmentsByClient.size() > 0) {
            client.setTopicId(
                    appointmentsByClient.get(appointmentsByClient.size() - 1)
                            .getTopic()
                            .getId());
        }

        model.addAttribute("client", client);
        model.addAttribute("topics", topics);
        model.addAttribute("viewName", "edit-client");

        return "index";
    }

    @GetMapping(value = "/addClient")
    public String addClient(Model model) {
        Clientele client = new Clientele();
        client.setFirstResponse(getNextHour());
        client.setFirstContact(getNextHour());
        Map<Integer, Topic> topics = clientService.getTopics();

        model.addAttribute("client", client);
        model.addAttribute("topics", topics);
        model.addAttribute("viewName", "edit-client");

        return "index";
    }

    @GetMapping(value = "/appointments/{id}", produces = "application/json")
    @ResponseBody
    public String appointments(@PathVariable("id") int id) {
        List<Appointment> appointments = clientService.getAppointmentsByClient(id);

        String s = "";
        try {
            s = objectMapper.writeValueAsString(appointments);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return s;
    }

    @GetMapping(value = "/appointments/{id}.html")
    public String appointments(Model model, @PathVariable("id") int id) {
        Clientele client = clientService.getClient(id);
        Map<Integer, Topic> topics = clientService.getTopics();
        List<Appointment> appointments = clientService.getAppointmentsByClient(id);

        model.addAttribute("client", client);
        model.addAttribute("appointments", appointments);
        model.addAttribute("topics", topics);
        model.addAttribute("viewName", "appointment-list");

        return "index";
    }

    @GetMapping(value = "/topics", produces = "application/json")
    @ResponseBody
    public String topics() {
        Map<Integer, Topic> topics = clientService.getTopics();

        String s = "";
        try {
            s = objectMapper.writeValueAsString(topics);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return s;
    }


    @GetMapping(value = "/receivables", produces = "application/json")
    @ResponseBody
    public String receivables() {
        List<Appointment> appointments = clientService.getAllReceivables();

        String s = "";
        try {
            s = objectMapper
                    .writerWithView(View.Receivables.class)
                    .writeValueAsString(appointments);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return s;
    }

    @GetMapping(value = "/receivables.html")
    public String receivables(Model model) {
        List<Appointment> appointments = clientService.getAllReceivables();

        model.addAttribute("receivables", appointments);
        model.addAttribute("viewName", "receivables");
        model.addAttribute("paiddate", new Date());

        return "index";
    }

    @PostMapping(path = "/saveClient", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public String saveClient(@RequestBody Clientele client) {
        System.out.println(client);
        if (client.getId() > 0) {
            clientService.updateClient(client);
        } else {
            clientService.insertClient(client);
        }

        String s = String.format("{ \"%s\": \"%d\" }", "updatedClientId", client.getId());
        return s;
    }

    @PostMapping(path = "/updatePaidDate", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public String updatePaidDate(@RequestBody Appointment appointment) {
        clientService.updatePaidDate(appointment);
        String s = String.format("{ \"%s\": \"%d\" }", "updatedAppointmentId", appointment.getId());
        return s;
    }

    @GetMapping(value = "/addAppointment")
    public String addAppointment(Model model) {
        Appointment appointment = new Appointment();
        appointment.setBillingPct(new BigDecimal("0.8"));
        appointment.setRate(new BigDecimal("60"));
        appointment.setDuration(60);
        appointment.setStartTime(getNextHour());
        List<Clientele> clients = clientService.getClients();
        Map<Integer, Topic> topics = clientService.getTopics();

        model.addAttribute("appointment", appointment);
        model.addAttribute("clients", clients);
        model.addAttribute("topics", topics);
        model.addAttribute("viewName", "create-appointment");

        return "index";
    }

    @PostMapping(path = "/saveAppointment", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public String saveAppointment(@RequestBody Appointment appointment) {
        // After MySQL 8.0.15 update, seeing empty string instead of null in description column
        if(appointment.getDescription() != null && appointment.getDescription().trim().equals("")) {
            appointment.setDescription(null);
        }

        System.out.println(appointment);
        clientService.insertAppointment(appointment);

        String s = String.format("{ \"%s\": \"%d\" }", "updatedClientId", appointment.getId());
        return s;
    }

    @GetMapping(value = "/monthlyActivity", produces = "application/json")
    @ResponseBody
    public String monthlyActivity() {
        List<MonthlyActivity> reportData = reportService.getMonthlyActivity("monthOfYear", "asc");

        String s = "";
        try {
            s = objectMapper.writeValueAsString(reportData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return s;
    }

    @GetMapping(value = "/reports/monthly-activity.html")
    public String monthlyActivity(@RequestParam(value="sortColumn", required = false, defaultValue = "monthOfYear") String sortColumn,
                         @RequestParam(value="sortOrder", required = false, defaultValue = "desc") String sortOrder,
                         @RequestParam(value="target", required = false, defaultValue = "index") String target,
                         Model model) {
        List<MonthlyActivity> reportData = reportService.getMonthlyActivity(sortColumn, sortOrder);

        model.addAttribute("reportData", reportData);
        model.addAttribute("viewName", "reports/monthly-activity");
        model.addAttribute("fragmentName", "monthly-activity");
        return target;
    }

    @GetMapping(value = "/activityByYearMonth/{year}/{month}", produces = "application/json")
    @ResponseBody
    public String activityByYearMonth(@PathVariable("year") int year,
                                      @PathVariable("month") int month) {
        List<Appointment> reportData = reportService.getActivityByYearMonth(year, month);

        String s = "";
        try {
            s = objectMapper.writeValueAsString(reportData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return s;
    }

    @GetMapping(value = "/reports/activity-year-month/{year}/{month}")
    public String monthlyActivity(@PathVariable(value="year") int year,
                                  @PathVariable(value="month") int month,
                                  @RequestParam(value="target", required = false, defaultValue = "index") String target,
                                  Model model) {
        List<Appointment> reportData = reportService.getActivityByYearMonth(year, month);

        model.addAttribute("reportData", reportData);
        model.addAttribute("viewName", "reports/activity-year-month");
        model.addAttribute("fragmentName", "activity-year-month");
        return target;
    }

    private Date getNextHour() {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault()).withMinute(0).withSecond(0).withNano(0).plusHours(1);
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }
}