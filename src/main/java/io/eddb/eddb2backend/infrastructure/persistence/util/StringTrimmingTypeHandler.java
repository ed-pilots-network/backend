package io.eddb.eddb2backend.infrastructure.persistence.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import static org.springframework.util.StringUtils.trimTrailingCharacter;

public class StringTrimmingTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, trimTrailingCharacter(parameter, ' '));
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return trimTrailingCharacter(rs.getString(columnName), ' ');
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return trimTrailingCharacter(rs.getString(columnIndex), ' ');
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return trimTrailingCharacter(cs.getString(columnIndex), ' ');
    }
}
