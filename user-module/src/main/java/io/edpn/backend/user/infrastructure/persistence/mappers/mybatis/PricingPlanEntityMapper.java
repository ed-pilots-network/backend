package io.edpn.backend.user.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.mybatisutil.UuidTypeHandler;
import io.edpn.backend.user.infrastructure.persistence.entity.PricingPlanEntity;
import java.util.Optional;
import java.util.UUID;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PricingPlanEntityMapper {
    @Results({
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name"),
            @Result(property = "capacityPerMinute", column = "capacity_per_minute")
    })
    @Select("SELECT * FROM pricing_plan WHERE id = #{id}")
    Optional<PricingPlanEntity> findById(@Param("id") UUID id);

    @Insert("INSERT INTO pricing_plan(id, name, capacity_per_minute) VALUES(#{id}, #{name}, #{capacityPerMinute})")
    void insert(PricingPlanEntity pricingPlan);

    @Update("UPDATE pricing_plan SET name = #{name}, capacity_per_minute = #{capacityPerMinute}, currency = #{currency} WHERE id = #{id}")
    void update(PricingPlanEntity pricingPlan);

    @Delete("DELETE FROM pricing_plan WHERE id = #{id}")
    void delete(UUID id);
}
