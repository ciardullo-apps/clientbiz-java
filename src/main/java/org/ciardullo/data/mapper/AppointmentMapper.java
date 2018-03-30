package org.ciardullo.data.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.ciardullo.model.Appointment;
import org.ciardullo.model.Clientele;
import org.ciardullo.model.Topic;

import java.util.List;

public interface AppointmentMapper {
    @Select("SELECT * FROM appointment WHERE paid IS NULL")
    @Results(value = {
            @Result(property = "clientele",
                    column = "client_id",
                    javaType = Clientele.class,
                    one = @One(select = "findClienteleByAppointment",
                            fetchType = FetchType.DEFAULT)),
            @Result(property = "topic",
                    column = "topic_id",
                    javaType = Topic.class,
                    one = @One(select = "findTopicByAppointment",
                            fetchType = FetchType.DEFAULT))

                    })
    List<Appointment> findAllReceivables();

    @Select("SELECT * FROM clientele WHERE id = #{clientId}")
    Clientele findClienteleByAppointment(int clientId);

    @Select("SELECT * FROM topic WHERE id = #{topicId}")
    Topic findTopicByAppointment(int topicId);

    @Select("SELECT * FROM appointment WHERE client_id = #{clientId} ORDER BY starttime")
    @Results(value = {
            @Result(property = "clientele",
                    column = "client_id",
                    javaType = Clientele.class,
                    one = @One(select = "findClienteleByAppointment",
                            fetchType = FetchType.DEFAULT)),
            @Result(property = "topic",
                    column = "topic_id",
                    javaType = Topic.class,
                    one = @One(select = "findTopicByAppointment",
                            fetchType = FetchType.DEFAULT))

    })
    List<Appointment> findAppointmentsByClient(int clientId);
}
