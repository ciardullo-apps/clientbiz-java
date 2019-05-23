package org.ciardullo.data.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.ciardullo.model.Appointment;
import org.ciardullo.model.Clientele;
import org.ciardullo.model.Topic;
import org.ciardullo.model.reports.MonthlyActivity;
import org.ciardullo.model.reports.RevenueByTopic;

import java.util.List;

public interface ReportMapper {

    @Select("select DATE_FORMAT(starttime, '%Y-%m') as monthOfYear, " +
            "COUNT(distinct client_id) as totalClients, " +
            "COUNT(*) as totalAppointments, " +
            "SUM(duration / 60) as totalHours, " +
            "SUM(rate * (duration / 60) * billingpct) as totalRevenue, " +
            "SUM(rate * (duration / 60) * billingpct)/ sum(duration / 60) as averageRate " +
            "FROM appointment " +
            "GROUP BY DATE_FORMAT(starttime, '%Y-%m') " +
            "ORDER BY ${sortColumn} ${sortOrder}")
    List<MonthlyActivity> findMonthlyActivity(@Param("sortColumn") String sortColumn, @Param("sortOrder") String sortOrder);

    @Select("SELECT * FROM appointment WHERE YEAR(starttime) = #{year} AND MONTH(starttime) = #{month} ORDER BY id")
    @Results(value = {
            @Result(property = "clientele",
                    column = "client_id",
                    javaType = Clientele.class,
                    one = @One(select = "org.ciardullo.data.mapper.AppointmentMapper.findClienteleByAppointment",
                            fetchType = FetchType.DEFAULT)),
            @Result(property = "topic",
                    column = "topic_id",
                    javaType = Topic.class,
                    one = @One(select = "org.ciardullo.data.mapper.AppointmentMapper.findTopicByAppointment",
                            fetchType = FetchType.DEFAULT))

    })
    List<Appointment> findAppointmentsByYearMonth(@Param("year") int year, @Param("month") int month);

    @Select("select name as topicName, sum(rate * (duration / 60) * billingpct) as totalRevenue, convert(sum(rate * (duration / 60) * billingpct) / sum(sum(rate * (duration / 60) * billingpct)) over () * 100, decimal(9,2)) as pctOfTotal\n" +
            "from appointment a join topic t on a.topic_id = t.id\n" +
            "group by t.name;\n")
    List<RevenueByTopic> findRevenueByTopic();
}
