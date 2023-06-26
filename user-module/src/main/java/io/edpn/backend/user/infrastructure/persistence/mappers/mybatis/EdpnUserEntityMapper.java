package io.edpn.backend.user.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.user.infrastructure.persistence.entity.ApiGrantEntity;
import io.edpn.backend.user.infrastructure.persistence.entity.EdpnUserEntity;
import io.edpn.backend.user.infrastructure.persistence.entity.PricingPlanEntity;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface EdpnUserEntityMapper {

    @Results(id = "edpnUserResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class),
            @Result(property = "email", column = "email", javaType = String.class),
            @Result(property = "password", column = "password", javaType = String.class),
            @Result(property = "accountExpiryTimestamp", column = "account_expiry_timestamp", javaType = LocalDateTime.class),
            @Result(property = "passwordExpiryTimestamp", column = "password_expiry_timestamp", javaType = LocalDateTime.class),
            @Result(property = "enabled", column = "enabled", javaType = boolean.class),
            @Result(property = "locked", column = "locked", javaType = boolean.class),
            @Result(property = "roles", column = "id", javaType = Set.class,
                    many = @Many(select = "io.edpn.backend.user.infrastructure.persistence.mapper.UserRoleEntityMapper.findByEdpnUserId")),
            @Result(property = "grants", column = "id", javaType = Set.class,
                    many = @Many(select = "io.edpn.backend.user.infrastructure.persistence.mapper.UserGrantEntityMapper.findByEdpnUserId")),
            @Result(property = "apiKeys", column = "id", javaType = Set.class,
                    many = @Many(select = "io.edpn.backend.user.infrastructure.persistence.mapper.ApiKeyEntityMapper.findByEdpnUserId")),
            @Result(property = "pricingPlan", column = "pricing_plan", javaType = PricingPlanEntity.class)
    })
    @Select("SELECT * FROM edpn_user WHERE id = #{id}")
    EdpnUserEntity findById(UUID id);

    @Insert({
            "INSERT INTO edpn_user(id, email, password, account_expiry_timestamp, password_expiry_timestamp, enabled, locked, pricing_plan)",
            "VALUES(#{id}, #{email}, #{password}, #{accountExpiryTimestamp}, #{passwordExpiryTimestamp}, #{enabled}, #{locked}, #{pricing_plan})"})
    void insert(EdpnUserEntity edpnUser);

    @Update({
            "UPDATE edpn_user",
            "SET email = #{email},",
            "password = #{password},",
            "account_expiry_timestamp = #{accountExpiryTimestamp},",
            "password_expiry_timestamp = #{passwordExpiryTimestamp},",
            "enabled = #{enabled},",
            "locked = #{locked},",
            "pricing_plan = #{pricing_plan}",
            "WHERE id = #{id}"
    })
    void update(EdpnUserEntity edpnUser);

    @Delete("DELETE FROM edpn_user WHERE id = #{id}")
    void delete(UUID id);

}
