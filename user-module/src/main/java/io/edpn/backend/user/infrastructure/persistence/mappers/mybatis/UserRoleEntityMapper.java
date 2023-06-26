package io.edpn.backend.user.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.mybatisutil.UuidTypeHandler;
import io.edpn.backend.user.infrastructure.persistence.entity.ApiRoleEntity;
import io.edpn.backend.user.infrastructure.persistence.entity.UserRoleEntity;
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

public interface UserRoleEntityMapper {

    @Results(id = "UserRoleResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name"),
            @Result(property = "grants", column = "id", javaType = Set.class,
                    many = @Many(select = "io.edpn.backend.user.infrastructure.persitence.mappers.mybatis.UserGrantEntityMapper.findByUserRoleId"))

    })
    @Select("SELECT * FROM user_role WHERE id = #{id}")
    Optional<UserRoleEntity> findById(UUID id);

    @ResultMap("UserRoleResultMap")

    @Select({"SELECT ag.* ",
            "FROM user_role_user_grant_map urm ",
            "INNER JOIN user_role ur ON urm.grant = ur.id",
            "WHERE kgm.key = #{edpnUserId}"})
    Collection<UserRoleEntity> findByEdpnUserId(@Param("edpnUserId") UUID edpnUserId);

    @Insert("INSERT INTO user_role(id, name) VALUES(#{id}, #{name})")
    void insertUserRole(UserRoleEntity userRole);

    @Update("UPDATE user_role SET name = #{name} WHERE id = #{id}")
    void updateUserRole(UserRoleEntity userRole);

    @Delete("DELETE FROM user_role WHERE id = #{id}")
    void deleteUserRole(UUID id);
}
