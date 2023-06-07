package io.edpn.backend.mybatisutil;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringListTypeHandler implements TypeHandler<List<String>> {

    @Override
    public void setParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            String listAsString = String.join(",", parameter);
            ps.setString(i, listAsString);
        } else {
            ps.setNull(i, jdbcType.TYPE_CODE);
        }
    }

    @Override
    public List<String> getResult(ResultSet rs, String columnName) throws SQLException {
        String[] stringArray = rs.getString(columnName).split(",");
        return new ArrayList<>(Arrays.asList(stringArray));
    }

    @Override
    public List<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
        String[] stringArray = rs.getString(columnIndex).split(",");
        return new ArrayList<>(Arrays.asList(stringArray));
    }

    @Override
    public List<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String[] stringArray = cs.getString(columnIndex).split(",");
        return new ArrayList<String>(Arrays.asList(stringArray));
    }
}
