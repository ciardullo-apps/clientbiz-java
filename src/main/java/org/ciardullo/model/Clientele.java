package org.ciardullo.model;

import java.time.LocalDateTime;

public class Clientele {
    private int id;
    private String firstname;
    private String lastname;
    private String contactname;
    private String city;
    private String state;
    private String timezone;
    private LocalDateTime firstcontact;
    private LocalDateTime firstresponse;
    private boolean solicited;

    public Clientele() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
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

    public LocalDateTime getFirstcontact() {
        return firstcontact;
    }

    public void setFirstcontact(LocalDateTime firstcontact) {
        this.firstcontact = firstcontact;
    }

    public LocalDateTime getFirstresponse() {
        return firstresponse;
    }

    public void setFirstresponse(LocalDateTime firstresponse) {
        this.firstresponse = firstresponse;
    }

    public boolean isSolicited() {
        return solicited;
    }

    public void setSolicited(boolean solicited) {
        this.solicited = solicited;
    }

    @Override
    public String toString() {
        return "Clientele{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", contactname='" + contactname + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", timezone='" + timezone + '\'' +
                ", firstcontact=" + firstcontact +
                ", firstresponse=" + firstresponse +
                ", solicited=" + solicited +
                '}';
    }
}
