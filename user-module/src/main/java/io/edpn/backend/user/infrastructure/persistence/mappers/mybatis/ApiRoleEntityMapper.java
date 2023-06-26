package io.edpn.backend.user.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.mybatisutil.UuidTypeHandler;
import io.edpn.backend.user.infrastructure.persistence.entity.ApiRoleEntity;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ApiRoleEntityMapper {

    @Insert("INSERT INTO api_role(id, name) VALUES(#{id}, #{name})")
    void insertApiRole(ApiRoleEntity apiRole);

    @Update("UPDATE api_role SET name = #{name} WHERE id = #{id}")
    void updateApiRole(ApiRoleEntity apiRole);

    @Delete("DELETE FROM api_role WHERE id = #{id}")
    void deleteApiRole(UUID id);

    @Results(id = "ApiRoleResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name"),
            @Result(property = "grants", column = "id", javaType = Set.class,
                    many = @Many(select = "io.edpn.backend.user.infrastructure.persitence.mappers.mybatis.ApiGrantEntityMapper.findByApiRoleId"))

    })
    @Select("SELECT * FROM api_role WHERE id = #{id}")
    Optional<ApiRoleEntity> findById(UUID id);

    @ResultMap("ApiRoleResultMap")
    @Select({"SELECT ar.* ",
            "FROM api_key_api_role_map kgm ",
            "INNER JOIN api_role ar ON kgm.role = ar.id",
            "WHERE kgm.key = #{apiKeyId}"})
    Collection<ApiRoleEntity> findByApiKeyId(UUID apiKeyId);
}
