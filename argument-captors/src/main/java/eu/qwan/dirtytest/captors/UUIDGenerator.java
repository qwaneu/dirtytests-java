package eu.qwan.dirtytest.captors;

import java.util.UUID;

@FunctionalInterface
public interface UUIDGenerator {

    UUID generate();
}