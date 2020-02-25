package app.meters.repo;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private static final AtomicLong generator = new AtomicLong(1);
    private static final Map<String, User> users = new ConcurrentHashMap<>();

    public Optional<User> findById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<User> insert(String name) {
        String id = nextId();
        User user = new User(id, name);
        return users.putIfAbsent(id, user) == null
            ? Optional.of(user)
            : Optional.empty();
    }

    public boolean delete(String id) {
        return users.remove(id) != null;
    }

    private String nextId() {
        return String.valueOf(generator.getAndIncrement());
    }

}
