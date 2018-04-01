package org.ciardullo.data.mapper;

import org.apache.ibatis.annotations.Insert;

public interface ClientTopicMapper {

    @Insert("INSERT INTO clienttopic (client_id, topic_id ) " +
            "VALUES (#{param1}, #{param2})")

    int insertClientTopic(int clientId, int topicId);
}
