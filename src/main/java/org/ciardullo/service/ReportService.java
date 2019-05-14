package org.ciardullo.service;

import org.ciardullo.data.mapper.ReportMapper;
import org.ciardullo.model.reports.MonthlyActivity;
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

}
