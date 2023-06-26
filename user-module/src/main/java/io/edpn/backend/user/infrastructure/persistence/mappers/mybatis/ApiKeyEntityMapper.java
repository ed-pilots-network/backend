package io.edpn.backend.user.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.mybatisutil.UuidTypeHandler;
import io.edpn.backend.user.infrastructure.persistence.entity.ApiKeyEntity;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ApiKeyEntityMapper {
    @Results(id = "ApiKeyResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "prefix", column = "prefix"),
            @Result(property = "keyHash", column = "key_hash"),
            @Result(property = "name", column = "name"),
            @Result(property = "expiryTimestamp", column = "expiry_timestamp"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "roles", column = "id", javaType = Set.class,
                    many = @Many(select = "io.edpn.backend.user.infrastructure.persitence.mappers.mybatis.ApiRoleEntityMapper.findByApiKeyId")),
            @Result(property = "grants", column = "id", javaType = Set.class,
                    many = @Many(select = "io.edpn.backend.user.infrastructure.persitence.mappers.mybatis.ApiGrantEntityMapper.findByApiKeyId"))
    })
    @Select("SELECT * FROM api_key WHERE id = #{id}")
    Optional<ApiKeyEntity> findById(@Param("id") UUID id);

    @ResultMap("ApiKeyResultMap")
    @Select("SELECT * FROM api_key WHERE edpn_user = #{edpnUserId}")
    Collection<ApiKeyEntity> findByEdpnUserId(@Param("edpnUserId") UUID edpnUserId);

    @Insert("INSERT INTO api_key(id, prefix, key_hash, name, expiry_timestamp, enabled, edpn_user) VALUES(#{apiKey.id}, #{apiKey.prefix}, #{apiKey.keyHash}, #{apiKey.name}, #{apiKey.expiryTimestamp}, #{apiKey.enabled}, #{user_id})")
    void insert(@Param("userId") UUID userId, @Param("apiKey") ApiKeyEntity apiKey);

    @Update("UPDATE api_key SET name = #{apiKey.name}, expiry_timestamp = #{apiKey.expiryTimestamp}, enabled = #{apiKey.enabled}, edpn_user = #{edpnUser} WHERE id = #{id}")
    void update(@Param("userId") UUID userId, @Param("apiKey") ApiKeyEntity apiKey);

    @Delete("DELETE FROM api_key WHERE id = #{id}")
    void delete(UUID id);
}
