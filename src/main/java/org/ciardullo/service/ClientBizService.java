package org.ciardullo.service;

import org.ciardullo.data.mapper.AppointmentMapper;
import org.ciardullo.data.mapper.ClientTopicMapper;
import org.ciardullo.data.mapper.ClienteleMapper;
import org.ciardullo.data.mapper.TopicMapper;
import org.ciardullo.model.Appointment;
import org.ciardullo.model.Clientele;
import org.ciardullo.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientBizService {
    @Autowired
    ClienteleMapper clienteleMapper;

    @Autowired
    TopicMapper topicMapper;

    @Autowired
    AppointmentMapper appointmentMapper;

    @Autowired
    ClientTopicMapper clientTopicMapper;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    public List<Clientele> getClients() {
        return clienteleMapper.getClients();
    }

    public Clientele getClient(int id) {
        return clienteleMapper.getClient(id);
    }

    public List<Appointment> getAppointmentsByClient(int clientId) {
        return appointmentMapper.findAppointmentsByClient(clientId);
    }

    public List<Topic> getTopics() {
        return topicMapper.selectTopics();
    }

    public List<Appointment> getAllReceivables() {
        return appointmentMapper.findAllReceivables();
    }

    @Transactional
    public int insertClient(Clientele clientele, int topicId) {
        int numRows = clienteleMapper.insertClientele(clientele);
        clientTopicMapper.insertClientTopic(clientele.getId(), topicId);
        return numRows;
    }

}
