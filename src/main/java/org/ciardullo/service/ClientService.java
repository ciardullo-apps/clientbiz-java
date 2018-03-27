package org.ciardullo.service;

import org.ciardullo.database.ClientMapper;
import org.ciardullo.model.Clientele;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    ClientMapper clientMapper;

    public List<Clientele> getClients() {
        return clientMapper.getClients();
    }

    public Clientele getClient(int id) {
        return clientMapper.getClient(id);
    }
}
