package org.ciardullo.service;

import org.ciardullo.data.mapper.ReportMapper;
import org.ciardullo.model.Appointment;
import org.ciardullo.model.reports.MonthlyActivity;
import org.ciardullo.model.reports.RevenueByTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportMapper reportMapper;

    public List<MonthlyActivity> getMonthlyActivity(String sortColumn, String sortOrder) {
        return reportMapper.findMonthlyActivity(sortColumn, sortOrder);
    }

    public List<Appointment> getActivityByYearMonth(int year, int month) {
        return reportMapper.findAppointmentsByYearMonth(year, month);
    }

    public List<RevenueByTopic> getRevenueByTopic() {
        return reportMapper.findRevenueByTopic();
    }

    public List<RevenueByTopic> getRevenueByTopic(int year) {
        return reportMapper.findRevenueByTopicAndYear(year);
    }

    public List<Integer> getYearsWithRevenue() {
        return reportMapper.findYearsWithRevenue();
    }
}
