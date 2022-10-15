package eu.qwan.dirtytest.captors;

import java.time.LocalDateTime;

public interface Clock {

    default LocalDateTime getNow() {
        return LocalDateTime.now();
    }
}