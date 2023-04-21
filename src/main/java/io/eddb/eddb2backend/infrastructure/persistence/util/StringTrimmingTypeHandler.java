package io.eddb.eddb2backend.infrastructure.persistence.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(String.class)
public class StringTrimmingTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.trim());
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Optional.ofNullable(rs.getString(columnName)).map(String::trim).orElse(null);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Optional.ofNullable(rs.getString(columnIndex)).map(String::trim).orElse(null);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Optional.ofNullable(cs.getString(columnIndex)).map(String::trim).orElse(null);
    }
}
