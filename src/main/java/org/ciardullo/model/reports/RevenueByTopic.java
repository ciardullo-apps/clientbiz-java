package org.ciardullo.model.reports;

public class RevenueByTopic {
    private String topicName;
    private double totalRevenue;

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    private double pctOfTotal;

    public RevenueByTopic() {
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public double getPctOfTotal() {
        return pctOfTotal;
    }

    public void setPctOfTotal(double pctOfTotal) {
        this.pctOfTotal = pctOfTotal;
    }
}
