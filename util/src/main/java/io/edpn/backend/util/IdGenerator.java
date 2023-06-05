package io.edpn.backend.util;

import java.util.UUID;

public class IdGenerator {

    public UUID generateId() {
        return UUID.randomUUID();
    }

    public UUID fromString(String str) {
        return UUID.fromString(str);
    }
}
