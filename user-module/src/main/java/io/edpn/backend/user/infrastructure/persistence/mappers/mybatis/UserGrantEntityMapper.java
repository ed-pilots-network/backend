package io.edpn.backend.user.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.mybatisutil.UuidTypeHandler;
import io.edpn.backend.user.infrastructure.persistence.entity.UserGrantEntity;
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

public interface UserGrantEntityMapper {
    @Results(id = "UserGrantResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name")
    })
    @Select("SELECT * FROM user_grant WHERE id = #{id}")
    Optional<UserGrantEntity> findById(@Param("id") UUID id);

    @ResultMap("UserGrantResultMap")
    @Select({"SELECT rg.* ",
            "FROM user_role_user_grant_map rgm ",
            "INNER JOIN user_grant rg ON rgm.grant = rg.id",
            "WHERE rgm.role = #{userRoleId}"})
    Collection<UserGrantEntity> findByUserRoleId(@Param("userRoleId") UUID userRoleId);

    @Insert("INSERT INTO user_grant(id, name) VALUES(#{id}, #{name})")
    void insert(UserGrantEntity userGrant);

    @Update("UPDATE user_grant SET name = #{name} WHERE id = #{id}")
    void update(UserGrantEntity userGrant);

    @Delete("DELETE FROM user_grant WHERE id = #{id}")
    void delete(UUID id);
}
