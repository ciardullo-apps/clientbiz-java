package org.ciardullo.data.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.ciardullo.model.Clientele;

import java.util.List;

public interface ClienteleMapper {

    Clientele selectClientele(int id);

    List<Clientele> selectClienteles();

    @Insert("INSERT INTO clientele (firstname, lastname, contactname, city, state, timezone, firstcontact, firstresponse, solicited) " +
            "VALUES (#{firstName}, #{lastName}, #{contactName}, #{city}, #{state}, #{timezone}, #{firstContact}, #{firstResponse}, #{solicited}) ")
    @Options(useGeneratedKeys=true)
    int insertClientele(Clientele clientele);   // mybatis returns number of rows inserted, NOT the id

    int updateClientele(Clientele clientele);

    int deleteClientele(int id);

    @Select("SELECT * from clientele where id = #{id}")
    Clientele getClient(int id);

    @Select("SELECT * from clientview")
    List<Clientele> getClients();

}
