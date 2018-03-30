package org.ciardullo.service;

import org.ciardullo.data.mapper.AppointmentMapper;
import org.ciardullo.data.mapper.ClienteleMapper;
import org.ciardullo.data.mapper.TopicMapper;
import org.ciardullo.model.Appointment;
import org.ciardullo.model.Clientele;
import org.ciardullo.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientBizService {
    @Autowired
    ClienteleMapper clienteleMapper;

    @Autowired
    TopicMapper topicMapper;

    @Autowired
    AppointmentMapper appointmentMapper;

    public List<Clientele> getClients() {
        return clienteleMapper.getClients();
    }

    public Clientele getClient(int id) {
        return clienteleMapper.getClient(id);
    }

    public List<Appointment> getAppointmentsByClient(int clientId) {
        return clienteleMapper.getAppointments(clientId);
    }

    public List<Topic> getTopics() {
        return topicMapper.selectTopics();
    }

    public List<Appointment> getAllReceivables() {
        return appointmentMapper.findAllReceivables();
    }
}
