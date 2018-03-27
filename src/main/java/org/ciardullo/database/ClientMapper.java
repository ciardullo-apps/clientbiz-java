package org.ciardullo.database;

import org.apache.ibatis.annotations.Select;
import org.ciardullo.model.Clientele;

import java.util.List;

public interface ClientMapper {
    @Select("SELECT * from clientele where id = #{id}")
    Clientele getClient(int id);

    @Select("SELECT * from clientview")
    List<Clientele> getClients();
}
