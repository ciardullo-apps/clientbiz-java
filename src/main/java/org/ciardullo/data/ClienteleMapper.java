package org.ciardullo.data;

import org.apache.ibatis.annotations.Select;
import org.ciardullo.model.Appointment;
import org.ciardullo.model.Clientele;

import java.util.List;
import java.util.Map;

public interface ClienteleMapper {

    Clientele selectClientele(int id);

    List<Clientele> selectClienteles();

    int insertClientele(Clientele clientele);

    int updatelientele(Clientele clientele);

    int deleteClientele(int id);

    @Select("SELECT * from clientele where id = #{id}")
    Clientele getClient(int id);

    @Select("SELECT * from clientview")
    List<Clientele> getClients();

    @Select("SELECT * from appointment where client_id = #{id} order by starttime")
    List<Appointment> getAppointments(int clientId);
}
