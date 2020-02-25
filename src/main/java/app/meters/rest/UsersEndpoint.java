package app.meters.rest;


import app.meters.repo.User;
import app.meters.repo.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
class UsersEndpoint {

    final UserRepository userRepository;

    @GetMapping(path = "/users/{id}", produces = "application/json")
    User getUser(@PathVariable("id") String id) {
        return userRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
    User insertUser(@RequestBody CreateUserRequest request) {
        return userRepository
            .insert(request.getName())
            .orElseThrow(() -> new ResponseStatusException(CONFLICT));
    }

    @DeleteMapping(path = "/users/{id}")
    void deleteUser(@PathVariable("id") String id) {
        if (!userRepository.delete(id)) throw new ResponseStatusException(NOT_FOUND);
    }

}

@Data
class CreateUserRequest {
    String name;
}
