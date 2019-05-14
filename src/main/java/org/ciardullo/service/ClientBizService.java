package org.ciardullo.service;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.ciardullo.data.mapper.*;
import org.ciardullo.model.Appointment;
import org.ciardullo.model.Clientele;
import org.ciardullo.model.Topic;
import org.ciardullo.model.reports.MonthlyActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
        return clienteleMapper.getClients("id", "asc");
    }

    public List<Clientele> getClients(String sortColumn, String sortOrder) {
        return clienteleMapper.getClients(sortColumn, sortOrder);
    }

    public Clientele getClient(int id) {
        return clienteleMapper.getClient(id);
    }

    public List<Appointment> getAppointmentsByClient(int clientId) {
        return appointmentMapper.findAppointmentsByClient(clientId);
    }

    public Map<Integer, Topic> getTopics() {
        return topicMapper.selectTopics();
    }

    public List<Appointment> getAllReceivables() {
        return appointmentMapper.findAllReceivables();
    }

    @Transactional
    public int insertClient(Clientele clientele) {
        int numRows = clienteleMapper.insertClientele(clientele);
        System.out.println("#### INSERTED CLIENTELE " + clientele.getId());
        clientTopicMapper.insertClientTopic(clientele.getId(), clientele.getTopicId());
        return numRows;
    }

    @Transactional
    public int updateClient(Clientele clientele) {
        int numRows = clienteleMapper.updateClientele(clientele);
        if(numRows != 1) {
            throw new TooManyResultsException(String.format("Updated %d rows!", numRows));
        }
        return numRows;
    }

    @Transactional
    public int updatePaidDate(Appointment appointment) {
        int numRows = appointmentMapper.updatePaidDate(appointment);
        if(numRows != 1) {
            throw new TooManyResultsException(String.format("Updated %d rows!", numRows));
        }
        return numRows;
    }

    @Transactional
    public int insertAppointment(Appointment appointment) {
        int numRows = appointmentMapper.insertAppointment(appointment);
        System.out.println("#### INSERTED APPOINTMENT " + appointment.getId());
        return numRows;
    }
}
