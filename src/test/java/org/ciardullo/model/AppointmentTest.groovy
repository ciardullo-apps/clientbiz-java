package org.ciardullo.model

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class AppointmentTest extends Specification {
    Appointment appointment
    ObjectMapper objectMapper = new ObjectMapper()

    def setup() {
        appointment = new Appointment()
    }

    def "an Appointment object with a startTime date is serialized to JSON with a valid format for an HTML5 datetime-local input"() {
        given: "a startTime Date"
        def aDate = new Date();
        and: "an Appointment with the startTime date set"
        appointment.setStartTime(aDate)
        when: "the Appointment object is serialized to JSON"
        def s = objectMapper.writeValueAsString(appointment)
        and: "the serialized JSON is parsed"
        def j = objectMapper.readTree(s)
        and: "the starttime date is cast to a String"
        def startTime = (String)j.get("starttime")
        then: "the Date is formatted properly for an HTML5 datetime-local"
        startTime =~ "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}"
    }

    def "an Appointment object with a paid date is serialized to JSON in the form yyyy-MM-dd"() {
        given: "a paid Date"
        def aDate = new Date();
        and: "an Appointment with the paid date set"
        appointment.setPaid(aDate)
        when: "the Appointment object is serialized to JSON"
        def s = objectMapper.writeValueAsString(appointment)
        and: "the serialized JSON is parsed"
        def j = objectMapper.readTree(s)
        and: "the paid date is cast to a String"
        def paid = (String)j.get("paid")
        then: "the Date is formatted properly"
        paid =~ "[0-9]{4}-[0-9]{2}-[0-9]{2}"
    }

}
