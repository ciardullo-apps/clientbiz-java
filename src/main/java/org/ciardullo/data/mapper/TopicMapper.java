package org.ciardullo.data.mapper;

import org.apache.ibatis.annotations.Select;
import org.ciardullo.model.Topic;

import java.util.List;

public interface TopicMapper {
    @Select("SELECT * FROM topic")
    List<Topic> selectTopics();

}
