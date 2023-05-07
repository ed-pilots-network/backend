package io.edpn.backend.eddnmessagelistener.domain.exception;

public class UnsupportedSchemaException extends RuntimeException {

    public static final String MESSAGE_TEMPLATE = "EDDN schema '%s' is not yet supported. Cannot process";

    public UnsupportedSchemaException(String schema) {
        super(String.format(MESSAGE_TEMPLATE, schema));
    }

    public UnsupportedSchemaException(String schema, Throwable cause) {
        super(String.format(MESSAGE_TEMPLATE, schema), cause);
    }

    public UnsupportedSchemaException(String schema, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(String.format(MESSAGE_TEMPLATE, schema), cause, enableSuppression, writableStackTrace);
    }
}
