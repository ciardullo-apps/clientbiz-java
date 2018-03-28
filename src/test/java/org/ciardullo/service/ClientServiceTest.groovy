package org.ciardullo.service

import org.ciardullo.config.AppConfig
import org.ciardullo.config.DbConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.test.context.SpringIntegrationTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

@WebAppConfiguration
@ContextConfiguration(classes=[AppConfig, DbConfig])
class ClientServiceTest extends Specification {
    @Autowired
    ClientService clientService;

    def "#getClients returns a list containing at least one client from the clientele table"() {
        when:
            def clients = clientService.getClients()
        then:
            clients.size() > 0
    }
}
