package org.ciardullo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ciardullo.model.Appointment;
import org.ciardullo.model.Clientele;
import org.ciardullo.model.Topic;
import org.ciardullo.model.View;
import org.ciardullo.service.ClientBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class ClientBizController {

    @Autowired
    ClientBizService clientService;

    @Autowired
    ObjectMapper objectMapper;

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
    public String client(Model model) {
        List<Clientele> clients = clientService.getClients();
        for (Clientele client : clients) {
            System.out.println(client);
        }

        model.addAttribute("clients", clients);
        model.addAttribute("viewName", "client-list");
        return "index";
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
        List<Topic> topics = clientService.getTopics();

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
        List<Topic> topics = clientService.getTopics();
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
        List<Topic> topics = clientService.getTopics();

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
}