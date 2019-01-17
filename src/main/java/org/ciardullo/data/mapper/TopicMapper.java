package org.ciardullo.data.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;
import org.ciardullo.model.Topic;

import java.util.List;
import java.util.Map;

public interface TopicMapper {
    @Select("SELECT * FROM topic")
    @MapKey("id")
    Map<Integer, Topic> selectTopics();

}
