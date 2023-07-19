package io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.exploration.infrastructure.persistence.entity.RequestDataMessageEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

public interface RequestDataMessageEntityMapper {

    @Insert("INSERT INTO request_data_message (topic, message) VALUES (#{topic}, #{message})")
    void insert(RequestDataMessageEntity requestDataMessageEntity);

    @Delete("DELETE FROM request_data_message WHERE topic = #{topic} AND message = #{message}")
    void delete(RequestDataMessageEntity requestDataMessageEntity);

    @Select("SELECT * FROM request_data_message")
    List<RequestDataMessageEntity> findAll();

    @Select("SELECT * FROM request_data_message WHERE topic = #{topic} AND message = #{message}")
    Optional<RequestDataMessageEntity> find(RequestDataMessageEntity requestDataMessageEntity);
}