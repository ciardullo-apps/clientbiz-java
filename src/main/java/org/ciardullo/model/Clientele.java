package org.ciardullo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Objects;

public class Clientele {
    private int id;

    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("lastname")
    private String lastName;

    @JsonProperty("contactname")
    private String contactName;

    private String city;

    private String state;

    private String timezone;

    @JsonProperty("firstcontact")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private Date firstContact;

    @JsonProperty("firstresponse")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private Date firstResponse;

    private boolean solicited;

    private int numappts;

    @JsonProperty("lastapptdate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private Date lastApptDate;

    @JsonProperty("lastapptyearmonth")
    private String lastApptYearMonth;

    public Clientele() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Date getFirstContact() {
        return firstContact;
    }

    public void setFirstContact(Date firstContact) {
        this.firstContact = firstContact;
    }

    public Date getFirstResponse() {
        return firstResponse;
    }

    public void setFirstResponse(Date firstResponse) {
        this.firstResponse = firstResponse;
    }

    public boolean isSolicited() {
        return solicited;
    }

    public void setSolicited(boolean solicited) {
        this.solicited = solicited;
    }

    public int getNumappts() {
        return numappts;
    }

    public void setNumappts(int numappts) {
        this.numappts = numappts;
    }

    public Date getLastApptDate() {
        return lastApptDate;
    }

    public void setLastApptDate(Date lastApptDate) {
        this.lastApptDate = lastApptDate;
    }

    public String getLastApptYearMonth() {
        return lastApptYearMonth;
    }

    public void setLastApptYearMonth(String lastApptYearMonth) {
        this.lastApptYearMonth = lastApptYearMonth;
    }

    @Override
    public String toString() {
        return "Clientele{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contactName='" + contactName + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", timezone='" + timezone + '\'' +
                ", firstContact=" + firstContact +
                ", firstResponse=" + firstResponse +
                ", solicited=" + solicited +
                ", numappts=" + numappts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clientele clientele = (Clientele) o;
        return id == clientele.id &&
                solicited == clientele.solicited &&
                Objects.equals(firstName, clientele.firstName) &&
                Objects.equals(lastName, clientele.lastName) &&
                Objects.equals(contactName, clientele.contactName) &&
                Objects.equals(city, clientele.city) &&
                Objects.equals(state, clientele.state) &&
                Objects.equals(timezone, clientele.timezone) &&
                Objects.equals(firstContact, clientele.firstContact) &&
                Objects.equals(firstResponse, clientele.firstResponse);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName, contactName, city, state, timezone, firstContact, firstResponse, solicited, numappts, lastApptDate, lastApptYearMonth);
    }
}
