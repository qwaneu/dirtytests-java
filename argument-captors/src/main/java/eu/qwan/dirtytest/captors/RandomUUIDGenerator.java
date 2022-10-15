package eu.qwan.dirtytest.captors;

import java.util.UUID;

public class RandomUUIDGenerator implements UUIDGenerator {

    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }
}
