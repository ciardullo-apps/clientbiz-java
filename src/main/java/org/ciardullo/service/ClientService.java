package org.ciardullo.service;

import org.ciardullo.data.ClienteleMapper;
import org.ciardullo.model.Appointment;
import org.ciardullo.model.Clientele;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    ClienteleMapper clienteleMapper;

    public List<Clientele> getClients() {
        return clienteleMapper.getClients();
    }

    public Clientele getClient(int id) {
        return clienteleMapper.getClient(id);
    }

    public List<Appointment> getAppointmentsByClient(int clientId) {
        return clienteleMapper.getAppointments(clientId);
    }
}
