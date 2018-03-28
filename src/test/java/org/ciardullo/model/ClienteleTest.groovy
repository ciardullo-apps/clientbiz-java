package org.ciardullo.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.JSONPObject
import spock.lang.Specification

class ClienteleTest extends Specification {
    Clientele clientele
    ObjectMapper objectMapper = new ObjectMapper()

    def setup() {
        clientele = new Clientele();
    }

    def "a Clientele object with a firstContact date is serialized to JSON with a valid format for an HTML5 datetime-local input"() {
        given: "a firstContact Date"
            def aDate = new Date();
        and: "a Clientele with the firstContact date set"
            clientele.setFirstContact(aDate)
        when: "the Clientele object is serialized to JSON"
            def s = objectMapper.writeValueAsString(clientele)
        and: "the serialized JSON is parsed"
            def j = objectMapper.readTree(s)
        and: "the firstcontact date is cast to a String"
            def firstContactDate = (String)j.get("firstcontact")
        then: "the Date is formatted properly for an HTML5 datetime-local"
            firstContactDate =~ "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}"
    }

    def "a Clientele object with a firstResponse date is serialized to JSON with a valid format for an HTML5 datetime-local input"() {
        given: "a firstResponse Date"
        def aDate = new Date();
        and: "a Clientele with the firstResponse date set"
        clientele.setFirstResponse(aDate)
        when: "the Clientele object is serialized to JSON"
        def s = objectMapper.writeValueAsString(clientele)
        and: "the serialized JSON is parsed"
        def j = objectMapper.readTree(s)
        and: "the firstresponse date is cast to a String"
        def firstResponseDate = (String)j.get("firstresponse")
        then: "the Date is formatted properly for an HTML5 datetime-local"
        firstResponseDate =~ "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}"
    }

    def "a Clientele object with a lastApptDate date is serialized to JSON with a valid format for an HTML5 datetime-local input"() {
        given: "a lastApptDate Date"
        def aDate = new Date();
        and: "a Clientele with the lastApptDate date set"
        clientele.setLastApptDate(aDate)
        when: "the Clientele object is serialized to JSON"
        def s = objectMapper.writeValueAsString(clientele)
        and: "the serialized JSON is parsed"
        def j = objectMapper.readTree(s)
        and: "the lastapptdate date is cast to a String"
        def lastApptDate = (String)j.get("lastapptdate")
        then: "the Date is formatted properly for an HTML5 datetime-local"
        lastApptDate =~ "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}"
    }
}
