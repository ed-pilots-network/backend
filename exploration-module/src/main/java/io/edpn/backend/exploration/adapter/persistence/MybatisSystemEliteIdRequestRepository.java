package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEliteIdRequestEntity;
import io.edpn.backend.util.Module;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

public interface MybatisSystemEliteIdRequestRepository {

    @Insert({
            "INSERT INTO system_eliteId_data_request (requesting_module, system_name)",
            "VALUES (#{requestingModule}, #{systemName})",
            "ON CONFLICT (requesting_module, system_name)",
            "DO NOTHING"
    })
    void insertIfNoExists(MybatisSystemEliteIdRequestEntity requestDataMessageEntity);

    @Delete("DELETE FROM system_eliteId_data_request WHERE requesting_module = #{requestingModule} AND system_name = #{systemName}")
    void delete(MybatisSystemEliteIdRequestEntity requestDataMessageEntity);

    @Select("SELECT * FROM system_eliteId_data_request")
    List<MybatisSystemEliteIdRequestEntity> findAll();

    @Select("SELECT * FROM system_eliteId_data_request WHERE system_name = #{systemName}")
    @Results(id = "commodityMarketInfoResultMap", value = {
            @Result(property = "systemName", column = "system_name"),
            @Result(property = "requestingModule", column = "requesting_module")
    })
    List<MybatisSystemEliteIdRequestEntity> findBySystemName(String requestDataMessageEntity);

    @Select("SELECT * FROM system_eliteId_data_request WHERE requesting_module = #{requestingModule} AND system_name = #{systemName}")
    @ResultMap("commodityMarketInfoResultMap")
    Optional<MybatisSystemEliteIdRequestEntity> find(@Param("requestingModule") Module requestingModule, @Param("systemName") String systemName);
}
