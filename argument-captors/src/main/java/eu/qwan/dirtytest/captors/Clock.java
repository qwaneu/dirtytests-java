package eu.qwan.dirtytest.captors;

import java.time.LocalDateTime;

public interface Clock {

    LocalDateTime getNow();
}