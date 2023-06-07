package io.edpn.backend.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CollectionUtil {

    public static <T> List<T> toList(T[] array) {
        return Optional.ofNullable(array)
                .map(ts -> Arrays.stream(ts).collect(Collectors.toCollection(LinkedList::new)))
                .orElseGet(LinkedList::new);
    }
}
