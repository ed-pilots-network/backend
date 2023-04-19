package io.eddb.eddb2backend.infrastructure.persistence.util;

import io.eddb.eddb2backend.application.dto.persistence.*;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.lang.reflect.Constructor;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public abstract class AbstractEntityIdTypeHandler<T> extends BaseTypeHandler<T> {

    private final Constructor<T> constructor;

    public AbstractEntityIdTypeHandler(Class<T> valueTypeClass) {
        try {
            this.constructor = valueTypeClass.getConstructor(UUID.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("The given value type class does not have a constructor with a single UUID parameter", e);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, T value, JdbcType jdbcType) throws SQLException {
        UUID uuidValue;
        try {
            uuidValue = (UUID) value.getClass().getMethod("value").invoke(value);
        } catch (Exception e) {
            throw new SQLException("Failed to get UUID value from the given value object", e);
        }
        preparedStatement.setObject(i, uuidValue, jdbcType.TYPE_CODE);
    }

    @Override
    public T getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        UUID uuidValue = resultSet.getObject(columnName, UUID.class);
        return uuidValue != null ? createValueObject(uuidValue) : null;
    }

    @Override
    public T getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        UUID uuidValue = resultSet.getObject(columnIndex, UUID.class);
        return uuidValue != null ? createValueObject(uuidValue) : null;
    }

    @Override
    public T getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        UUID uuidValue = callableStatement.getObject(columnIndex, UUID.class);
        return uuidValue != null ? createValueObject(uuidValue) : null;
    }

    private T createValueObject(UUID uuidValue) throws SQLException {
        try {
            return constructor.newInstance(uuidValue);
        } catch (Exception e) {
            throw new SQLException("Failed to create a value object with the given UUID", e);
        }
    }
}
