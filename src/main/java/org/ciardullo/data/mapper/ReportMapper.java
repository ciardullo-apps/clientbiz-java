package org.ciardullo.data.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ciardullo.model.reports.MonthlyActivity;

import java.util.List;

public interface ReportMapper {

    @Select("select DATE_FORMAT(starttime, '%Y-%m-01') as monthOfYear, " +
            "COUNT(distinct client_id) as totalClients, " +
            "COUNT(*) as totalAppointments, " +
            "SUM(duration / 60) as totalHours, " +
            "SUM(rate * (duration / 60) * billingpct) as totalRevenue, " +
            "SUM(rate * (duration / 60) * billingpct)/ sum(duration / 60) as averageRate " +
            "FROM appointment " +
            "GROUP BY DATE_FORMAT(starttime, '%Y-%m-01') " +
            "ORDER BY ${sortColumn} ${sortOrder}")
    List<MonthlyActivity> findMonthlyActivity(@Param("sortColumn") String sortColumn, @Param("sortOrder") String sortOrder);
}
