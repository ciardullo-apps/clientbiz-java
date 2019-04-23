package org.ciardullo.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class Appointment {
    private int id;

    @JsonProperty("client_id")
    private int clientId;

    @JsonProperty("topic_id")
    private int topicId;

    @JsonProperty("starttime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone="US/Eastern")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startTime;

    private int duration;

    private BigDecimal rate;

    @JsonProperty("billingpct")
    private BigDecimal billingPct;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "US/Eastern")
    private Date paid;

    @JsonIgnore
    private Clientele clientele;

    @JsonIgnore
    private Topic topic;

    private String description;

    public Appointment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getBillingPct() {
        return billingPct;
    }

    public void setBillingPct(BigDecimal billingPct) {
        this.billingPct = billingPct;
    }

    public Date getPaid() {
        return paid;
    }

    public void setPaid(Date paid) {
        this.paid = paid;
    }

    public Clientele getClientele() {
        return clientele;
    }

    public void setClientele(Clientele clientele) {
        this.clientele = clientele;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @JsonView(View.Receivables.class)
    public String getFirstName() {
        if(clientele == null) {
            return null;
        }

        return this.clientele.getFirstName();
    }

    @JsonView(View.Receivables.class)
    public String getLastName() {
        if(clientele == null) {
            return null;
        }

        return this.clientele.getLastName();
    }

    @JsonView(View.Receivables.class)
    public String getTopicName() {
        if(topic == null) {
            return null;
        }

        return this.topic.getName();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRevenue() {
        return new BigDecimal(String.format("%.2f",
                rate.doubleValue() * duration / 60 * billingPct.doubleValue()));
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", topicId=" + topicId +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", rate=" + rate +
                ", billingPct=" + billingPct +
                ", paid=" + paid +
                ", clientele=" + clientele +
                ", topic=" + topic +
                ", description=" + description +
                '}';
    }
}
