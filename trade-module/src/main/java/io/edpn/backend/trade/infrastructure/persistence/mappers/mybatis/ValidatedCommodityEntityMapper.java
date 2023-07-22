package io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.mybatisutil.UuidTypeHandler;
import io.edpn.backend.trade.infrastructure.persistence.entity.ValidatedCommodityEntity;
import io.edpn.backend.trade.infrastructure.persistence.filter.FindCommodityFilterPersistence;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ValidatedCommodityEntityMapper {
    
    @Select("SELECT * FROM validated_commodity_view WHERE id = #{id}")
    @Results( id = "validatedCommodityResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "commodityName", column = "technical_name", javaType = String.class),
            @Result(property = "displayName", column = "display_name", javaType = String.class),
            @Result(property = "type", column = "type", javaType = String.class),
            @Result(property = "isRare", column = "is_rare", javaType = Boolean.class)
    })
    Optional<ValidatedCommodityEntity> findById(UUID id);
    
    @Select("SELECT * FROM validated_commodity_view")
    @ResultMap("validatedCommodityResultMap")
    List<ValidatedCommodityEntity> findAll();
    
    @Select("""
            <script>
            SELECT *
            FROM validated_commodity_view
            WHERE
            <if test='type != null'>type = #{type}</if>
            <if test='isRare != null and type != null'> AND </if>
            <if test='isRare != null'>is_rare = #{isRare}</if>
            </script>
            """)
    @ResultMap("validatedCommodityResultMap")
    List<ValidatedCommodityEntity> findByFilter(FindCommodityFilterPersistence findCommodityFilterPersistence);
    
}
