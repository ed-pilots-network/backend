package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisFindCommodityFilter;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisValidatedCommodityEntity;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MybatisValidatedCommodityRepository {
    @Select("SELECT * FROM validated_commodity_view WHERE id = #{id}")
    @Results( id = "validatedCommodityResultMap", value = {
            @Result(property = "commodityName", column = "technical_name", javaType = String.class),
            @Result(property = "displayName", column = "display_name", javaType = String.class),
            @Result(property = "type", column = "type", javaType = String.class),
            @Result(property = "isRare", column = "is_rare", javaType = Boolean.class)
    })
    Optional<MybatisValidatedCommodityEntity> findById(UUID id);
    
    @Select("SELECT * FROM validated_commodity_view WHERE display_name = #{displayName}")
    @ResultMap("validatedCommodityResultMap")
    Optional<MybatisValidatedCommodityEntity> findByName(String displayName);
    
    @Select("SELECT * FROM validated_commodity_view")
    @ResultMap("validatedCommodityResultMap")
    List<MybatisValidatedCommodityEntity> findAll();
    
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
    List<MybatisValidatedCommodityEntity> findByFilter(MybatisFindCommodityFilter findCommodityFilter);
    
}
