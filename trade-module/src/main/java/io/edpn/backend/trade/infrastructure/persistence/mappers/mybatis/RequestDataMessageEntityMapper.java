package io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.trade.infrastructure.persistence.entity.RequestDataMessageEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

public interface RequestDataMessageEntityMapper {

    @Insert("INSERT INTO request_data_message (topic, message, send) VALUES (#{topic}, #{message}, #{send})")
    void insert(RequestDataMessageEntity requestDataMessageEntity);

    @Delete("DELETE FROM request_data_message WHERE topic = #{topic} AND message = #{message}")
    void delete(RequestDataMessageEntity requestDataMessageEntity);

    @Delete("UPDATE request_data_message SET topic = #{topic}, message = #{message}, send = #{send} WHERE topic = #{topic} AND message = #{message}")
    void update(RequestDataMessageEntity requestDataMessageEntity);

    @Select("SELECT topic, message FROM request_data_message")
    @Results(id = "requestDataMessageEntityResultMap", value = {
            @Result(property = "topic", column = "topic", javaType = String.class),
            @Result(property = "message", column = "message", javaType = String.class),
            @Result(property = "send", column = "send", javaType = boolean.class)
    })
    List<RequestDataMessageEntity> findAll();

    @Select("SELECT * FROM request_data_message WHERE topic = #{topic} AND message = #{message}")
    @ResultMap("requestDataMessageEntityResultMap")
    Optional<RequestDataMessageEntity> find(RequestDataMessageEntity requestDataMessageEntity);
}
