package io.edpn.backend.exploration.application.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

public class SystemTest {

    @Test
    public void testWithEliteId_sameValue_shouldReturnSameSystem() {
        // Given
        Long eliteId = 123L;
        System system = new System(UUID.randomUUID(), eliteId, "name", "starClass", new Coordinate(1.0, 2.0, 3.0));

        // When
        System result = system.withEliteId(eliteId);

        // Then
        assertThat(result, sameInstance(system));
    }

    @Test
    public void testWithEliteId_differentValue_shouldReturnNewSystem() {
        // Given
        Long oldEliteId = 123L;
        System system = new System(UUID.randomUUID(), oldEliteId, "name", "starClass", new Coordinate(1.0, 2.0, 3.0));
        Long newEliteId = 456L;

        // When
        System result = system.withEliteId(newEliteId);

        // Then
        assertThat(result, not(sameInstance(system)));
        assertThat(result.eliteId(), equalTo(newEliteId));
    }

    @Test
    public void testWithStarClass_sameValue_shouldReturnSameSystem() {
        // Given
        String starClass = "starClass";
        System system = new System(UUID.randomUUID(), 123L, "name", starClass, new Coordinate(1.0, 2.0, 3.0));

        // When
        System result = system.withStarClass(starClass);

        // Then
        assertThat(result, sameInstance(system));
    }

    @Test
    public void testWithStarClass_differentValue_shouldReturnNewSystem() {
        // Given
        String oldStarClass = "oldStarClass";
        System system = new System(UUID.randomUUID(), 123L, "name", oldStarClass, new Coordinate(1.0, 2.0, 3.0));
        String newStarClass = "newStarClass";

        // When
        System result = system.withStarClass(newStarClass);

        // Then
        assertThat(result, not(sameInstance(system)));
        assertThat(result.starClass(), equalTo(newStarClass));
    }

    @Test
    public void testWithCoordinate_sameValue_shouldReturnSameSystem() {
        // Given
        Coordinate coordinate = new Coordinate(1.0, 2.0, 3.0);
        System system = new System(UUID.randomUUID(), 123L, "name", "starClass", coordinate);

        // When
        System result = system.withCoordinate(coordinate);

        // Then
        assertThat(result, sameInstance(system));
    }

    @Test
    public void testWithCoordinate_differentValue_shouldReturnNewSystem() {
        // Given
        Coordinate oldCoordinate = new Coordinate(1.0, 2.0, 3.0);
        System system = new System(UUID.randomUUID(), 123L, "name", "starClass", oldCoordinate);
        Coordinate newCoordinate = new Coordinate(4.0, 5.0, 6.0);

        // When
        System result = system.withCoordinate(newCoordinate);

        // Then
        assertThat(result, not(sameInstance(system)));
        assertThat(result.coordinate(), equalTo(newCoordinate));
    }
}