package io.edpn.edpnbackend.commoditymessageprocessor.infrastructure.persistence.mappers;

import io.edpn.edpnbackend.commoditymessageprocessor.application.dto.persistence.SchemaLatestTimestampEntity;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SchemaLatestTimestampEntityMapper {

    @Results(id = "SchemaLatestTimestampEntityResult", value = {
            @Result(property = "schema", column = "schema"),
            @Result(property = "timestamp", column = "timestamp")
    })
    @Select("SELECT schema, timestamp FROM schema_latest_timestamps")
    List<SchemaLatestTimestampEntity> findAll();

    @ResultMap("SchemaLatestTimestampEntityResult")
    @Select("SELECT schema, timestamp FROM schema_latest_timestamps WHERE schema = #{schema}")
    Optional<SchemaLatestTimestampEntity> findBySchema(@Param("schema") String schema);

    @Insert("INSERT INTO schema_latest_timestamps (schema, timestamp) VALUES (#{schema}, #{timestamp})")
    int insert(SchemaLatestTimestampEntity schemaLatestTimestampEntity);

    @Update("UPDATE schema_latest_timestamps SET timestamp = #{timestamp} WHERE schema = #{schema}")
    int update(SchemaLatestTimestampEntity schemaLatestTimestampEntity);
}
