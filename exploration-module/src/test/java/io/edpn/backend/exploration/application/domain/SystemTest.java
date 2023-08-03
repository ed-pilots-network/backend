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
        
        Long eliteId = 123L;
        System system = new System(UUID.randomUUID(), eliteId, "name", "starClass", new Coordinate(1.0, 2.0, 3.0));


        System result = system.withEliteId(eliteId);


        assertThat(result, sameInstance(system));
    }

    @Test
    public void testWithEliteId_differentValue_shouldReturnNewSystem() {
        
        Long oldEliteId = 123L;
        System system = new System(UUID.randomUUID(), oldEliteId, "name", "starClass", new Coordinate(1.0, 2.0, 3.0));
        Long newEliteId = 456L;


        System result = system.withEliteId(newEliteId);


        assertThat(result, not(sameInstance(system)));
        assertThat(result.eliteId(), equalTo(newEliteId));
    }

    @Test
    public void testWithStarClass_sameValue_shouldReturnSameSystem() {
        
        String starClass = "starClass";
        System system = new System(UUID.randomUUID(), 123L, "name", starClass, new Coordinate(1.0, 2.0, 3.0));


        System result = system.withStarClass(starClass);


        assertThat(result, sameInstance(system));
    }

    @Test
    public void testWithStarClass_differentValue_shouldReturnNewSystem() {
        
        String oldStarClass = "oldStarClass";
        System system = new System(UUID.randomUUID(), 123L, "name", oldStarClass, new Coordinate(1.0, 2.0, 3.0));
        String newStarClass = "newStarClass";


        System result = system.withStarClass(newStarClass);


        assertThat(result, not(sameInstance(system)));
        assertThat(result.starClass(), equalTo(newStarClass));
    }

    @Test
    public void testWithCoordinate_sameValue_shouldReturnSameSystem() {
        
        Coordinate coordinate = new Coordinate(1.0, 2.0, 3.0);
        System system = new System(UUID.randomUUID(), 123L, "name", "starClass", coordinate);


        System result = system.withCoordinate(coordinate);


        assertThat(result, sameInstance(system));
    }

    @Test
    public void testWithCoordinate_differentValue_shouldReturnNewSystem() {
        
        Coordinate oldCoordinate = new Coordinate(1.0, 2.0, 3.0);
        System system = new System(UUID.randomUUID(), 123L, "name", "starClass", oldCoordinate);
        Coordinate newCoordinate = new Coordinate(4.0, 5.0, 6.0);


        System result = system.withCoordinate(newCoordinate);


        assertThat(result, not(sameInstance(system)));
        assertThat(result.coordinate(), equalTo(newCoordinate));
    }
}