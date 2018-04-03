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
        return "greeting";
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

        model.addAttribute("client", client);

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

    @PostMapping(path = "/saveClient", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public String saveClient(@RequestBody Clientele clientele) {
        System.out.println(clientele.getFirstName());
        return "";
    }
}