package eu.qwan.dirtytest.captors;

import java.time.LocalDateTime;

public class LocalClock implements Clock {

    public LocalDateTime getNow() {
        return LocalDateTime.now();
    }

}
