package io.edpn.backend.user.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.mybatisutil.UuidTypeHandler;
import io.edpn.backend.user.infrastructure.persistence.entity.ApiGrantEntity;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ApiGrantEntityMapper {
    @Results(id = "ApiGrantResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name")
    })
    @Select("SELECT * FROM api_grant WHERE id = #{id}")
    Optional<ApiGrantEntity> findById(@Param("id") UUID id);

    @ResultMap("ApiGrantResultMap")
    @Select({"SELECT ag* ",
            "FROM api_role_api_grant_map rgm ",
            "INNER JOIN api_grant ag ON rgm.grant = ag.id",
            "WHERE rgm.role = #{apiRoleId}"})
    Collection<ApiGrantEntity> findByApiRoleId(@Param("apiRoleId") UUID apiRoleId);

    @ResultMap("ApiGrantResultMap")
    @Select({"SELECT ag.* ",
            "FROM api_key_api_grant_map kgm ",
            "INNER JOIN api_grant ag ON kgm.grant = ag.id",
            "WHERE kgm.key = #{apiKeyId}"})
    Collection<ApiGrantEntity> findByApiKeyId(@Param("apiRoleId") UUID apiKeyId);

    @Insert("INSERT INTO api_grant(id, name) VALUES(#{id}, #{name})")
    void insert(ApiGrantEntity apiGrant);

    @Update("UPDATE api_grant SET name = #{name} WHERE id = #{id}")
    void update(ApiGrantEntity apiGrant);

    @Delete("DELETE FROM api_grant WHERE id = #{id}")
    void delete(UUID id);
}
