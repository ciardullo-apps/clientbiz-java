package org.ciardullo.data.mapper;

import org.apache.ibatis.annotations.*;
import org.ciardullo.model.Clientele;

import java.util.List;

public interface ClienteleMapper {

    Clientele selectClientele(int id);

    List<Clientele> selectClienteles();

    @Insert("INSERT INTO clientele (firstname, lastname, contactname, city, state, timezone, firstcontact, firstresponse, solicited) " +
            "VALUES (#{firstName}, #{lastName}, #{contactName}, #{city}, #{state}, #{timezone}, #{firstContact}, #{firstResponse}, #{solicited}) ")
    @Options(useGeneratedKeys=true, keyColumn = "id", keyProperty = "id")
    int insertClientele(Clientele clientele);   // mybatis returns number of rows inserted, NOT the id

    @Update("UPDATE clientele SET firstname = #{firstName}, lastName = #{lastName}, contactname = #{contactName}, city = #{city}, state = #{state}, " +
        "timezone = #{timezone}, firstcontact = #{firstContact}, firstresponse = #{firstResponse}, solicited = #{solicited} " +
        "WHERE id = #{id}")
    int updateClientele(Clientele clientele);

    int deleteClientele(int id);

    @Select("SELECT * from clientele where id = #{id}")
    Clientele getClient(int id);

    // MyBatis requires the @Param annotation when there are two or more parameters
    // order by parameter names must be preceded by $ instead of #
    @Select("SELECT * from clientview ORDER BY ${sortColumn} ${sortOrder}")
    List<Clientele> getClients(@Param("sortColumn") String sortColumn, @Param("sortOrder") String sortOrder);

}
