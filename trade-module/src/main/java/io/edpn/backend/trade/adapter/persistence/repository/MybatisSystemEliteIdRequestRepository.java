package io.edpn.backend.trade.adapter.persistence.repository;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemDataRequestEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MybatisSystemEliteIdRequestRepository {

    @Insert("INSERT INTO system_elite_id_data_request (system_name) VALUES (#{systemName})")
    void insert(@Param("systemName") String systemName);

    @Delete("DELETE FROM system_elite_id_data_request WHERE system_name = #{systemName}")
    void delete(@Param("systemName") String systemName);

    @Select("SELECT EXISTS(SELECT 1 FROM system_elite_id_data_request WHERE system_name = #{systemName})")
    boolean exists(@Param("systemName") String systemName);

    @Select("SELECT * FROM system_elite_id_data_request")
    @Results(id = "systemDataRequestResultMap", value = {
            @Result(property = "systemName", column = "system_name", javaType = String.class)
    })
    List<MybatisSystemDataRequestEntity> findAll();
}
