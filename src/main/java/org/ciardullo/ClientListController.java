package org.ciardullo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ciardullo.model.Clientele;
import org.ciardullo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ClientListController {

    @Autowired
    ClientService clientService;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping(value = "/client", produces = "application/json")
    @ResponseBody
    public String client() {
        List<Clientele> clients = clientService.getClients();
        for (Clientele client : clients) {
            System.out.println(client);
        }

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

}