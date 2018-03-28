package org.ciardullo.database;

import org.apache.ibatis.annotations.Select;
import org.ciardullo.model.Appointment;
import org.ciardullo.model.Clientele;

import java.util.List;

public interface ClientMapper {
    @Select("SELECT * from clientele where id = #{id}")
    Clientele getClient(int id);

    @Select("SELECT * from clientview")
    List<Clientele> getClients();

    @Select("SELECT * from appointment where client_id = #{id} order by starttime")
    List<Appointment> getAppointments(int clientId);
}
