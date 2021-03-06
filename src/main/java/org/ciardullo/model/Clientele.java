package org.ciardullo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Clientele {
    private int id;

    @NotBlank(message="First name is required")
    @JsonProperty("firstname")
    private String firstName;

    @NotBlank(message="Last name is required")
    @JsonProperty("lastname")
    private String lastName;

    @JsonProperty("contactname")
    private String contactName;

    @NotBlank(message="City name is required")
    private String city;

    @NotBlank(message="State is required")
    private String state;

    @NotBlank(message="Timezone is required")
    private String timezone;

    @JsonProperty("firstcontact")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone="US/Eastern")
    private Date firstContact;

    @NotNull(message="First response date time is required")
    @JsonProperty("firstresponse")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone="US/Eastern")
    private Date firstResponse;

    private boolean solicited;

    private int numappts;

    private BigDecimal revenue;

    @JsonProperty("lastapptdate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private Date lastApptDate;

    @JsonProperty("lastapptyearmonth")
    private String lastApptYearMonth;

    private int topicId;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    public Date getFirstContact() {
        return firstContact;
    }

    public void setFirstContact(Date firstContact) {
        this.firstContact = firstContact;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
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

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
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

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    @JsonIgnore
    public String getDisplayName() {
        return String.format("%s %s", getFirstName(), getLastName());
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
