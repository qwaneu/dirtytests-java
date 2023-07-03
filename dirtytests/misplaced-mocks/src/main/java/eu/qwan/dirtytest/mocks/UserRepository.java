package eu.qwan.dirtytest.mocks;

import java.util.Optional;

public interface UserRepository {
    Optional<User> byId(String id);
}
