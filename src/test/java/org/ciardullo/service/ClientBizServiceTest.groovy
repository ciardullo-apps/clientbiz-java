package org.ciardullo.service

import org.ciardullo.config.AppConfig
import org.ciardullo.config.DbConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

@WebAppConfiguration
@ContextConfiguration(classes=[AppConfig, DbConfig])
class ClientBizServiceTest extends Specification {
    @Autowired
    ClientBizService clientService;

    def "#getClients returns a list containing at least one client from the clientele table"() {
        when:
            def clients = clientService.getClients()
        then:
            clients.size() > 0
    }

    def "#getClient returns a valid Clientele instance or null of id is not found"() {
        expect:
            (clientService.getClient(clientId) != null) == clientFound
        where:
            clientId          | clientFound
            1                 | true
            Integer.MAX_VALUE | false
    }

    def "#getAppointments returns a list containing at least one appointment from the appointment table"() {
        expect:
            appointmentsFound == (clientService.getAppointmentsByClient(clientId).size() == 0)
        where:
            clientId          | appointmentsFound
            1                 | false
            Integer.MAX_VALUE | true
    }

    def "#getTopics returns a list containing at least one topic from the topic table"() {
        when:
            def topics = clientService.getTopics()
        then:
            topics.size() > 0
    }

    def "#getAllReceivables returns a list containing at least one appointment"() {
        when:
            def appointments = clientService.getAllReceivables()
        then:
            appointments.size() > 0
    }
}
