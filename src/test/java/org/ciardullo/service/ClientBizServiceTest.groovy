package org.ciardullo.service

import org.ciardullo.config.AppConfig
import org.ciardullo.config.DbConfig
import org.ciardullo.model.Clientele
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneOffset

@WebAppConfiguration
@ContextConfiguration(classes=[AppConfig, DbConfig])
@Transactional // Required for auto rollback
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

    def "#insert succeeds when all required column values are set in a Clientele object"() {
        given: "a Clientele object with all required values set"
            def c = new Clientele();
            c.setFirstName("Jane")
            c.setLastName("Doe")
            c.setCity("Nowhere")
            c.setState("NA")
            c.setTimezone("US/Eastern")
            c.setFirstResponse(Date.from(LocalDateTime.of(2018, 1, 1, 15, 0).toInstant(ZoneOffset.UTC)))
            c.setTopicId(1)
        when:
            clientService.insertClient(c)
        and:
            def clientele = clientService.getClient(c.getId())
        then:
            c.equals(clientele)
    }
}
