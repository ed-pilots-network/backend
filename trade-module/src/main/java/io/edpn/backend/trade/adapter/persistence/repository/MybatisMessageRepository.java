package io.edpn.backend.trade.adapter.persistence.repository;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisMessageEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

public interface MybatisMessageRepository {

    @Insert("INSERT INTO request_data_message (topic, message) VALUES (#{topic}, #{message})")
    void insert(MybatisMessageEntity requestDataMessageEntity);

    @Delete("DELETE FROM request_data_message WHERE topic = #{topic} AND message = #{message}")
    void delete(MybatisMessageEntity requestDataMessageEntity);

    @Select("SELECT * FROM request_data_message")
    List<MybatisMessageEntity> findAll();

    @Select("SELECT * FROM request_data_message WHERE topic = #{topic} AND message = #{message}")
    Optional<MybatisMessageEntity> find(MybatisMessageEntity messageEntity);
}
