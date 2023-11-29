package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationEntity;
import java.util.Optional;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface MybatisStationRepository {

    @Select({
            //TODO
    })
    @ResultMap("stationResultMap")
    MybatisStationEntity insertOrUpdateOnConflict(MybatisStationEntity station);

    @Select({
            //TODO
    })
    @Results(id = "stationResultMap", value = {
            //TODO
    })
    Optional<MybatisStationEntity> findByIdentifier(@Param("systemName") String systemName, @Param("stationName") String stationName);
}
